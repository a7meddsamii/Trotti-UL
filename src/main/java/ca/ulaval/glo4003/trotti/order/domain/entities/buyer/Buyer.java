package ca.ulaval.glo4003.trotti.order.domain.entities.buyer;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.values.PassId;
import ca.ulaval.glo4003.trotti.payment.domain.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import java.util.List;
import java.util.Optional;

public class Buyer {
    private final Idul idul;
    private final String name;
    private final Email email;
    private final Cart cart;
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

    public boolean removeFromCart(PassId passId) {
        return cart.remove(passId);
    }

    public void clearCart() {
        cart.clear();
    }

    public Money getCartBalance() {
        return cart.calculateAmount();
    }

    public List<Pass> confirmCartPasses() {
        List<Pass> confirmedPasses = cart.linkPassesToBuyer(idul);
        cart.clear();
        return confirmedPasses;
    }

    public List<Pass> getCartPasses() {
        return cart.getPasses();
    }

    public boolean hasEmptyCart() {
        return cart.isEmpty();
    }
}
