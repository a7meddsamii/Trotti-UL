package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.application.order.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.application.order.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.application.order.mappers.TransactionMapper;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.communication.services.NotificationService;
import ca.ulaval.glo4003.trotti.domain.order.entities.buyer.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.entities.invoice.Invoice;
import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Order;
import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.domain.order.exceptions.CartException;
import ca.ulaval.glo4003.trotti.domain.order.factories.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.domain.order.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.order.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.InvalidPaymentMethodException;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.PaymentException;
import ca.ulaval.glo4003.trotti.domain.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.payment.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.domain.payment.values.transaction.Transaction;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class OrderApplicationService {

    private final BuyerRepository buyerRepository;
    private final PassRepository passRepository;
    private final PaymentMethodFactory paymentMethodFactory;
    private final OrderFactory orderFactory;
    private final PaymentService paymentService;
    private final TransactionMapper transactionMapper;
    private final NotificationService<Transaction> transactionNotificationService;
    private final NotificationService<Invoice> invoiceNotificationService;

    public OrderApplicationService(
            BuyerRepository buyerRepository,
            PassRepository passRepository,
            PaymentMethodFactory paymentMethodFactory,
            OrderFactory orderFactory,
            PaymentService paymentService,
            TransactionMapper transactionMapper,
            NotificationService<Transaction> transactionNotificationService,
            NotificationService<Invoice> invoiceNotificationService) {
        this.buyerRepository = buyerRepository;
        this.passRepository = passRepository;
        this.paymentMethodFactory = paymentMethodFactory;
        this.orderFactory = orderFactory;
        this.paymentService = paymentService;
        this.transactionMapper = transactionMapper;
        this.transactionNotificationService = transactionNotificationService;
        this.invoiceNotificationService = invoiceNotificationService;
    }

    public TransactionDto placeOrderFor(Idul idul, PaymentInfoDto paymentInfoDto) {
        Buyer buyer = buyerRepository.findByIdul(idul);
        if (buyer.hasEmptyCart())
            throw new CartException("Cannot place an order with an empty cart");

        CreditCard paymentMethod = getPaymentMethod(buyer, paymentInfoDto);

        Transaction transaction =
                paymentService.process(paymentMethod, buyer.getCartBalance(), paymentInfoDto.cvv());

        if (transaction.isSuccessful()) {
            finalizeOrderFor(buyer);
        }

        transactionNotificationService.notify(buyer.getEmail(), transaction);

        if (!transaction.isSuccessful()) {
            throw new PaymentException(transaction.getDescription());
        }

        return transactionMapper.toDto(transaction);
    }

    private CreditCard getPaymentMethod(Buyer buyer, PaymentInfoDto paymentInfoDto) {
        boolean hasNumber = StringUtils.isNotBlank(paymentInfoDto.cardNumber());
        boolean hasName = StringUtils.isNotBlank(paymentInfoDto.cardHolderName());
        boolean hasExpiry = paymentInfoDto.expirationDate() != null;
        boolean hasCvv = StringUtils.isNotBlank(paymentInfoDto.cvv());

        if (!hasNumber && !hasName && !hasExpiry && hasCvv) {
            return buyer.getPaymentMethod().orElseThrow(
                    () -> new InvalidPaymentMethodException("No saved payment method"));
        }

        if (hasNumber && hasName && hasExpiry && hasCvv) {
            CreditCard newCard = paymentMethodFactory.createCreditCard(paymentInfoDto.cardNumber(),
                    paymentInfoDto.cardHolderName(), paymentInfoDto.expirationDate(),
                    paymentInfoDto.cvv());
            buyer.updatePaymentMethod(newCard);
            return newCard;
        }

        throw new InvalidPaymentMethodException("Invalid or incomplete payment method information");
    }

    private void finalizeOrderFor(Buyer buyer) {
        List<Pass> boughtPasses = buyer.confirmCartPasses();
        buyerRepository.update(buyer);
        passRepository.saveAll(boughtPasses);
        Order order = orderFactory.create(buyer.getIdul(), boughtPasses);
        Invoice invoice = order.generateInvoice();
        invoiceNotificationService.notify(buyer.getEmail(), invoice);
    }

}
