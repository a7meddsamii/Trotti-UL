package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.application.order.dto.PassDto;
import ca.ulaval.glo4003.trotti.application.order.mappers.PassMapper;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.communication.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.communication.strategies.OrderInvoiceEmailStrategy;
import ca.ulaval.glo4003.trotti.domain.order.*;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;
import ca.ulaval.glo4003.trotti.domain.payment.services.PaymentService;
import java.lang.module.InvalidModuleDescriptorException;
import java.util.List;

public class OrderApplicationService {

    private final BuyerRepository buyerRepository;
    private final PassMapper passMapper;
    private final OrderFactory orderFactory;
    private final PassFactory passFactory;
    private final PaymentService paymentService;
    private final EmailService emailService;

    public OrderApplicationService(
            BuyerRepository buyerRepository,
            PassMapper passMapper,
            OrderFactory orderFactory,
            PassFactory passFactory,
            PaymentService paymentService,
            EmailService emailService) {
        this.buyerRepository = buyerRepository;
        this.passMapper = passMapper;
        this.orderFactory = orderFactory;
        this.passFactory = passFactory;
        this.paymentService = paymentService;
        this.emailService = emailService;
    }

    public List<PassDto> getCart(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul);

        return buyer.getCart().getPasses().stream().map(passMapper::toDto).toList();
    }

    public List<PassDto> addToCart(Idul idul, List<PassDto> passListDto) {
        List<Pass> passList = createPasses(passListDto);

        Buyer buyer = buyerRepository.findByIdul(idul);
        for (Pass pass : passList) {
            buyer.addToCart(pass);
        }
        buyerRepository.save(buyer);

        return buyer.getCart().getPasses().stream().map(passMapper::toDto).toList();
    }

    public List<PassDto> removeFromCart(Idul idul, Id passId) {
        Buyer buyer = buyerRepository.findByIdul(idul);

        buyer.removeFromCart(passId);
        buyerRepository.save(buyer);

        return buyer.getCart().getPasses().stream().map(passMapper::toDto).toList();
    }

    public void clearCart(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul);

        buyer.clearCart();
        buyerRepository.save(buyer);
    }

    public void updatePaymentMethod(Idul idul, PaymentMethod paymentMethod) {
        Buyer buyer = buyerRepository.findByIdul(idul);

        buyer.updatePaymentMethod(paymentMethod);
        buyerRepository.save(buyer);
    }

    public void deletePaymentMethod(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul);

        buyer.deletePaymentMethod();
        buyerRepository.save(buyer);
    }

    public void confirmCart(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul);
        Cart cart = buyer.getCart();

        PaymentMethod paymentMethod = buyer.getPaymentMethod().orElseThrow(
                () -> new InvalidModuleDescriptorException("No payment method found."));
        paymentService.process(paymentMethod, cart.calculateAmount());

        Order order = orderFactory.create(idul, cart.getPasses());
        EmailMessage invoice = EmailMessage.builder()
                .withEmailStrategy(new OrderInvoiceEmailStrategy(buyer.getEmail(), buyer.getName(),
                        order.generateInvoice()))
                .build();
        emailService.send(invoice);
    }

    private List<Pass> createPasses(List<PassDto> passListDto) {
        return passListDto.stream()
                .map(passDto -> passFactory.create(passDto.maximumDailyTravelTime(),
                        passDto.session(), passDto.billingFrequency()))
                .toList();
    }
}
