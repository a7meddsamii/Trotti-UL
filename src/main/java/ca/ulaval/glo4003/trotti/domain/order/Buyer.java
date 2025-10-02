package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import java.util.Optional;

public class Buyer {
    private final Idul idul;
    private final String name;
    private final Email email;
    private Cart cart;
    private CreditCard creditCard;

    public Buyer(Idul idul, String name, Email email, Cart cart, CreditCard creditCard) {
        this.idul = idul;
        this.name = name;
        this.email = email;
        this.cart = cart;
        this.creditCard = creditCard;
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

    public Optional<CreditCard> getPaymentMethod() {
        return Optional.ofNullable(creditCard);
    }

    public void updatePaymentMethod(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public void deletePaymentMethod() {
        this.creditCard = null;
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
