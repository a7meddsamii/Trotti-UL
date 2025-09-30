package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.application.order.dto.PassRequestDto;
import ca.ulaval.glo4003.trotti.application.order.dto.PassResponseDto;
import ca.ulaval.glo4003.trotti.application.order.mappers.PassMapper;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
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
    private final AccountRepository accountRepository;
    private final BuyerFactory buyerFactory;
    private final PassMapper passMapper;
    private final OrderFactory orderFactory;
    private final PaymentService paymentService;
    private final EmailService emailService;

    public OrderApplicationService(
            BuyerRepository buyerRepository,
            AccountRepository accountRepository,
            BuyerFactory buyerFactory,
            PassMapper passMapper,
            OrderFactory orderFactory,
            PaymentService paymentService,
            EmailService emailService) {
        this.buyerRepository = buyerRepository;
        this.accountRepository = accountRepository;
        this.buyerFactory = buyerFactory;
        this.passMapper = passMapper;
        this.orderFactory = orderFactory;
        this.paymentService = paymentService;
        this.emailService = emailService;
    }

    public List<PassResponseDto> getCart(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul).orElseGet(() -> {
            Account account =
                    accountRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
            Buyer newBuyer = buyerFactory.create(idul, account.getName(), account.getEmail());

            buyerRepository.save(newBuyer);
            return newBuyer;
        });

        return passMapper.toDto(buyer.getCart().getList());
    }

    public List<PassResponseDto> addToCart(Idul idul, List<PassRequestDto> passListDto) {
        List<Pass> passList = passMapper.toDomain(passListDto);

        Buyer buyer = buyerRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
        Cart cart = buyer.getCart();
        for (Pass pass : passList) {
            cart.add(pass);
        }
        buyerRepository.save(buyer);

        return passMapper.toDto(cart.getList());
    }

    public List<PassResponseDto> removeFromCart(Idul idul, Id passId) {
        Buyer buyer = buyerRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
        Cart cart = buyer.getCart();
        cart.remove(passId);
        buyerRepository.save(buyer);

        return passMapper.toDto(cart.getList());
    }

    public void clearCart(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
        Cart cart = buyer.getCart();
        cart.clear();
        buyerRepository.save(buyer);
    }

    public void updatePaymentMethod(Idul idul, PaymentMethod paymentMethod) {
        Buyer buyer = buyerRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
        buyer.updatePaymentMethod(paymentMethod);
        buyerRepository.save(buyer);
    }

    public void deletePaymentMethod(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
        buyer.deletePaymentMethod();
        buyerRepository.save(buyer);
    }

    public void confirmCart(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
        Cart cart = buyer.getCart();

        PaymentMethod paymentMethod = buyer.getPaymentMethod().orElseThrow(
                () -> new InvalidModuleDescriptorException("No payment method found."));
        paymentService.process(paymentMethod, cart.calculateAmount());

        Order order = orderFactory.create(idul, cart.getList());
        EmailMessage invoice = EmailMessage
                .builder().withEmailStrategy(new OrderInvoiceEmailStrategy(buyer.getEmail(),
                        buyer.getName(), order.generateInvoice(paymentMethod.generateInvoice())))
                .build();
        emailService.send(invoice);
    }
}
