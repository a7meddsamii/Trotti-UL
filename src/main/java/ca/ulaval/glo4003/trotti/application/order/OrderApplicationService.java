package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.application.order.dto.OrderDto;
import ca.ulaval.glo4003.trotti.application.order.dto.PassDto;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.commons.CreditCard;
import ca.ulaval.glo4003.trotti.domain.commons.Money;
import ca.ulaval.glo4003.trotti.domain.communication.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.communication.strategies.OrderInvoiceEmailStrategy;
import ca.ulaval.glo4003.trotti.domain.order.Order;
import ca.ulaval.glo4003.trotti.domain.order.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.Pass;
import ca.ulaval.glo4003.trotti.domain.order.PassFactory;
import ca.ulaval.glo4003.trotti.domain.order.repository.OrderRepository;
import ca.ulaval.glo4003.trotti.domain.order.services.PaymentPort;
import java.util.ArrayList;
import java.util.List;

public class OrderApplicationService {

    private final AuthenticationService authenticationService;
    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;
    private final OrderFactory orderFactory;
    private final PassFactory passFactory;
    private final PaymentPort paymentPort;
    private final EmailService emailService;

    public OrderApplicationService(
            AuthenticationService authenticationService,
            AccountRepository accountRepository,
            OrderRepository orderRepository,
            OrderFactory orderFactory,
            PassFactory passFactory,
            PaymentPort paymentPort,
            EmailService emailService) {
        this.authenticationService = authenticationService;
        this.accountRepository = accountRepository;
        this.orderRepository = orderRepository;
        this.orderFactory = orderFactory;
        this.passFactory = passFactory;
        this.paymentPort = paymentPort;
        this.emailService = emailService;
    }

    // For now, the entire process is done in one step, but could be split ?
    // Comments added for clarity, will remove later
    public void makeOrder(AuthenticationToken token, OrderDto orderDto, CreditCard creditCard) {
        // Authenticate
        Idul idul = authenticationService.authenticate(token);

        // Create Order
        List<Pass> passList = new ArrayList<>();
        for (PassDto passDto : orderDto.passList()) {
            passList.add(passFactory.create(passDto.maximumDailyTravelTime(), passDto.session(),
                    passDto.billingFrequency()));
        }
        Order order = orderFactory.create(idul, passList);

        // Pay Order before saving it
        Money amount = order.getTotal();
        paymentPort.pay(creditCard, amount);

        // Finally get to save Order to OrderRepository
        orderRepository.save(order);

        // Get Account for 1. & 2.
        Account account =
                accountRepository.findByIdul(idul).orElseThrow(AccountNotFoundException::new);

        // 1. Send invoice
        EmailMessage invoice = EmailMessage.builder()
                .withEmailStrategy(
                        new OrderInvoiceEmailStrategy(account.getEmail(), account.getName(), order))
                .build();
        emailService.send(invoice);

        // 2 Save CreditCard to Account and Account to AccountRepository
        account.saveCreditCard(creditCard);
        accountRepository.save(account);
    }
}
