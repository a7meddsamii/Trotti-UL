package ca.ulaval.glo4003.trotti.infrastructure.persistence.inmemory;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.infrastructure.account.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.infrastructure.order.repositories.records.BuyerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TravelerRecord;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

public class UserInMemoryDatabase {
    private final ConcurrentMap<Idul, AccountRecord> accountTable;
    private final ConcurrentMap<Idul, BuyerRecord> buyerTable;
    private final ConcurrentMap<Idul, TravelerRecord> travelerTable;

    public UserInMemoryDatabase(
            ConcurrentMap<Idul, AccountRecord> accountTable,
            ConcurrentMap<Idul, BuyerRecord> buyerTable,
            ConcurrentMap<Idul, TravelerRecord> travelerTable) {
        this.accountTable = accountTable;
        this.buyerTable = buyerTable;
        this.travelerTable = travelerTable;
    }

    public void insertIntoAccountTable(AccountRecord account) {
        accountTable.put(account.idul(), account);
        BuyerRecord buyerRecord =
                new BuyerRecord(account.idul(), account.name(), account.email(), List.of(), null);
        buyerTable.put(account.idul(), buyerRecord);
        TravelerRecord travelerRecord =
                new TravelerRecord(account.idul(), account.email(), Collections.emptyList());
        travelerTable.put(account.idul(), travelerRecord);
    }

    public void insertIntoBuyerTable(BuyerRecord buyer) {
        enforceForeignKeyConstraint(buyer.idul());
        buyerTable.put(buyer.idul(), buyer);
    }

    public void insertIntoTravelerTable(TravelerRecord traveler) {
        enforceForeignKeyConstraint(traveler.idul());
        travelerTable.put(traveler.idul(), traveler);
    }

    public Optional<AccountRecord> selectFromAccountTable(Idul idul) {
        return Optional.ofNullable(accountTable.get(idul));
    }

    public Optional<AccountRecord> selectFromAccountTable(Email email) {
        return accountTable.values().stream().filter(account -> account.email().equals(email))
                .findFirst();
    }

    public BuyerRecord selectFromBuyerTable(Idul idul) {
        return buyerTable.get(idul);
    }

    public BuyerRecord selectFromBuyerTable(Email email) {
        return buyerTable.values().stream().filter(buyer -> buyer.email().equals(email)).findFirst()
                .orElse(null);
    }

    public List<TravelerRecord> getAllTravelers() {
        return List.copyOf(travelerTable.values());
    }

    private void enforceForeignKeyConstraint(Idul idul) {
        if (!accountTable.containsKey(idul)) {
            throw new IllegalStateException(
                    "Foreign key constraint violation: Account does not exist for the given Idul");
        }
    }
}
