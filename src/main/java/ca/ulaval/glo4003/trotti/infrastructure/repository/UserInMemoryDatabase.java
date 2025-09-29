package ca.ulaval.glo4003.trotti.infrastructure.repository;

import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.infrastructure.repository.account.AccountRecord;
import ca.ulaval.glo4003.trotti.infrastructure.repository.order.BuyerEntity;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

public class UserInMemoryDatabase {
    private final ConcurrentMap<Idul, AccountRecord> accountTable;
    private final ConcurrentMap<Idul, BuyerEntity> buyerTable;

    public UserInMemoryDatabase(
            ConcurrentMap<Idul, AccountRecord> accountTable,
            ConcurrentMap<Idul, BuyerEntity> buyerTable) {
        this.accountTable = accountTable;
        this.buyerTable = buyerTable;
    }

    public void insertIntoAccountTable(AccountRecord account) {
        accountTable.put(account.idul(), account);
    }

    public void insertIntoBuyerTable(BuyerEntity buyer) {
        enforceForeignKeyConstraint(buyer);
        buyerTable.put(buyer.idul(), buyer);
    }

    public Optional<AccountRecord> selectFromAccountTable(Idul idul) {
        return Optional.ofNullable(accountTable.get(idul));
    }

    public Optional<AccountRecord> selectFromAccountTable(Email email) {
        return accountTable.values().stream().filter(account -> account.email().equals(email))
                .findFirst();
    }

    public Optional<BuyerEntity> selectFromBuyerTable(Idul idul) {
        return Optional.ofNullable(buyerTable.get(idul));
    }

    private void enforceForeignKeyConstraint(BuyerEntity buyer) {
        if (!accountTable.containsKey(buyer.idul())) {
            throw new IllegalStateException(
                    "Foreign key constraint violation: Account does not exist for the given Idul");
        }
    }
}