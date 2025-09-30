package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.application.order.dto.PassDto;
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
import java.util.ArrayList;
import java.util.List;

public class OrderApplicationService {

    private final BuyerRepository buyerRepository;
    private final AccountRepository accountRepository;
    private final BuyerFactory buyerFactory;
    private final PassFactory passFactory;
    private final OrderFactory orderFactory;
    private final PaymentService paymentService;
    private final EmailService emailService;

    public OrderApplicationService(
            BuyerRepository buyerRepository,
            AccountRepository accountRepository,
            BuyerFactory buyerFactory,
            PassFactory passFactory,
            OrderFactory orderFactory,
            EmailService emailService) {
        this.buyerRepository = buyerRepository;
        this.accountRepository = accountRepository;
        this.buyerFactory = buyerFactory;
        this.passFactory = passFactory;
        this.orderFactory = orderFactory;
        this.paymentService = new PaymentService();
        this.emailService = emailService;
    }

    public List<Pass> getCart(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul).orElseGet(() -> {
            Account account =
                    accountRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
            Buyer newBuyer = buyerFactory.create(idul, account.getName(), account.getEmail());

            buyerRepository.save(newBuyer);
            return newBuyer;
        });

        return buyer.getCart().getList();
    }

    public List<Pass> addToCard(Idul idul, List<PassDto> passListDto) {
        List<Pass> passList = new ArrayList<>();
        for (PassDto passDto : passListDto) {
            passList.add(passFactory.create(passDto.maximumDailyTravelTime(), passDto.session(),
                    passDto.billingFrequency()));
        }

        Buyer buyer = buyerRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
        Cart cart = buyer.getCart();
        for (Pass pass : passList) {
            cart.add(pass);
        }
        buyerRepository.save(buyer);

        return cart.getList();
    }

    public List<Pass> removeFromCart(Idul idul, Id passId) {
        Buyer buyer = buyerRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
        Cart cart = buyer.getCart();
        cart.remove(passId);
        buyerRepository.save(buyer);

        return cart.getList();
    }

    public void clearCart(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
        Cart cart = buyer.getCart();
        cart.clear();
    }

    public void savePaymentMethod(Idul idul, PaymentMethod paymentMethod) {
        Buyer buyer = buyerRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
        buyer.savePaymentMethod(paymentMethod);
        buyerRepository.save(buyer);
    }

    public void deletePaymentMethod(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
        buyer.clearPaymentMethod();
        buyerRepository.save(buyer);
    }

    public void confirmCart(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);
        Cart cart = buyer.getCart();

        PaymentMethod paymentMethod = buyer.getPaymentMethod();
        paymentService.process(paymentMethod, cart.calculateAmount());

        Order order = orderFactory.create(idul, cart.getList());
        EmailMessage invoice = EmailMessage
                .builder().withEmailStrategy(new OrderInvoiceEmailStrategy(buyer.getEmail(),
                        buyer.getName(), order.generateInvoice(paymentMethod.generateInvoice())))
                .build();
        emailService.send(invoice);
    }
}
