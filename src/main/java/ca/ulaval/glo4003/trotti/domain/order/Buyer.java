package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;
import java.util.Optional;

public class Buyer {
    private final Idul idul;
    private final String name;
    private final Email email;
    private Cart cart;
    private Optional<PaymentMethod> paymentMethod;

    public Buyer(Idul idul, String name, Email email, Cart cart) {
        this.idul = idul;
        this.name = name;
        this.email = email;
        this.cart = cart;
        this.paymentMethod = Optional.empty();
    }

    public Buyer(Idul idul, String name, Email email, Cart cart, PaymentMethod paymentMethod) {
        this.idul = idul;
        this.name = name;
        this.email = email;
        this.cart = cart;
        this.paymentMethod = Optional.of(paymentMethod);
    }

    public Idul getIdul() {
        return idul;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public Cart getCart() {
        return cart;
    }

    public Optional<PaymentMethod> getPaymentMethod() {
        return paymentMethod;
    }

    public void updatePaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = Optional.of(paymentMethod);
    }

    public void deletePaymentMethod() {
        this.paymentMethod = Optional.empty();
    }

    public boolean addToCart(Pass pass) {
        return cart.add(pass);
    }

    public boolean removeFromCart(Id passId) {
        return cart.remove(passId);
    }

    public void clearCart() {
        cart.clear();
    }
}
