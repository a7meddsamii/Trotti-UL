package ca.ulaval.glo4003.trotti.domain.trip.entities.traveler;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;

import java.util.List;

public class Traveler {

    private final Idul idul;
    private final Email email;
    private final Wallet wallet;



    public Traveler(Idul idul, Email email, Wallet wallet) {
        this.idul = idul;
        this.email = email;
        this.wallet = wallet;

    }

    public List<RidePermit> updateWallet(List<RidePermit> ridePermitsHistory) {
        return wallet.updateActiveRidePermits(ridePermitsHistory);
    }

    public List<RidePermit> getWallet() {
        return wallet.getRidePermits();
    }

    public boolean isWalletEmpty() {
        return wallet.hasActiveRidePermits();
    }

    public Idul getIdul() {
        return idul;
    }

    public Email getEmail() {
        return email;
    }
}
