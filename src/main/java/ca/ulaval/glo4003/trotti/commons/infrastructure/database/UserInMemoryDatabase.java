package ca.ulaval.glo4003.trotti.commons.infrastructure.database;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TravelerRecord;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

public class UserInMemoryDatabase {
    private final ConcurrentMap<Idul, AccountRecord> accountTable;
    private final ConcurrentMap<Idul, TravelerRecord> travelerTable;

    public UserInMemoryDatabase(
            ConcurrentMap<Idul, AccountRecord> accountTable,
            ConcurrentMap<Idul, TravelerRecord> travelerTable) {
        this.accountTable = accountTable;
        this.travelerTable = travelerTable;
    }

    public void insertIntoAccountTable(AccountRecord account) {
        accountTable.put(account.idul(), account);
        TravelerRecord travelerRecord =
                new TravelerRecord(account.idul(), account.email(), Collections.emptyList(), null);
        travelerTable.put(account.idul(), travelerRecord);
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
	
    public List<TravelerRecord> getAllTravelers() {
        return List.copyOf(travelerTable.values());
    }

    public TravelerRecord selectFromTravelerTable(Idul idul) {
        return travelerTable.get(idul);
    }

    private void enforceForeignKeyConstraint(Idul idul) {
        if (!accountTable.containsKey(idul)) {
            throw new IllegalStateException(
                    "Foreign key constraint violation: Account does not exist for the given Idul");
        }
    }
}
