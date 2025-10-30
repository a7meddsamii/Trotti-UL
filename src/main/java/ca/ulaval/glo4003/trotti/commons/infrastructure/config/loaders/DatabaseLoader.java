package ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.commons.infrastructure.database.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.order.infrastructure.repositories.records.BuyerRecord;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TravelerRecord;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DatabaseLoader extends Bootstrapper {
    @Override
    public void load() {
        loadUserRepositories();
    }

    private void loadUserRepositories() {
        ConcurrentMap<Idul, AccountRecord> accountTable = new ConcurrentHashMap<>();
        ConcurrentMap<Idul, BuyerRecord> buyerTable = new ConcurrentHashMap<>();
        ConcurrentMap<Idul, TravelerRecord> travelerTable = new ConcurrentHashMap<>();
        UserInMemoryDatabase userInMemoryDatabase =
                new UserInMemoryDatabase(accountTable, buyerTable, travelerTable);
        this.resourceLocator.register(UserInMemoryDatabase.class, userInMemoryDatabase);
    }
}
