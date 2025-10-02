package ca.ulaval.glo4003.trotti.infrastructure.persistence;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.infrastructure.account.repository.AccountRecord;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.record.BuyerRecord;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

public class UserInMemoryDatabase {
    private final ConcurrentMap<Idul, AccountRecord> accountTable;
    private final ConcurrentMap<Idul, BuyerRecord> buyerTable;

    public UserInMemoryDatabase(
            ConcurrentMap<Idul, AccountRecord> accountTable,
            ConcurrentMap<Idul, BuyerRecord> buyerTable) {
        this.accountTable = accountTable;
        this.buyerTable = buyerTable;
    }

    public void insertIntoAccountTable(AccountRecord account) {
        accountTable.put(account.idul(), account);
        BuyerRecord buyerRecord =
                new BuyerRecord(account.idul(), account.name(), account.email(), List.of(), null);
        buyerTable.put(account.idul(), buyerRecord);
    }

    public void insertIntoBuyerTable(BuyerRecord buyer) {
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

    public BuyerRecord selectFromBuyerTable(Idul idul) {
        return buyerTable.get(idul);
    }

    public BuyerRecord selectFromBuyerTable(Email email) {
        return buyerTable.values().stream().filter(buyer -> buyer.email().equals(email)).findFirst()
                .orElse(null);
    }

    private void enforceForeignKeyConstraint(BuyerRecord buyer) {
        if (!accountTable.containsKey(buyer.idul())) {
            throw new IllegalStateException(
                    "Foreign key constraint violation: Account does not exist for the given Idul");
        }
    }
}
