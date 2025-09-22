package ca.ulaval.glo4003.trotti.infrastructure.repository;

import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.trotti.infrastructure.repository.account.AccountEntity;
import ca.ulaval.glo4003.trotti.infrastructure.repository.order.BuyerEntity;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

public class UserInMemoryDatabase {
    ConcurrentMap<Idul, AccountEntity> accountTable;
    ConcurrentMap<Idul, BuyerEntity> buyerTable;

    public UserInMemoryDatabase(
            ConcurrentMap<Idul, AccountEntity> accountTable,
            ConcurrentMap<Idul, BuyerEntity> buyerTable) {
        this.accountTable = accountTable;
        this.buyerTable = buyerTable;
    }

    public void insertIntoAccountTable(AccountEntity account) {
        accountTable.put(account.idul(), account);
    }

    public Optional<AccountEntity> selectFromAccountTable(Idul idul) {
        return Optional.ofNullable(accountTable.get(idul));
    }

    public void insertIntoBuyerTable(BuyerEntity buyer) {
        enforceAccountForeignKeyConstraint(buyer.idul());
        buyerTable.put(buyer.idul(), buyer);
    }

    private void enforceAccountForeignKeyConstraint(Idul idul) {
        if (!accountTable.containsKey(idul)) {
            throw new AccountNotFoundException();
        }
    }
}
