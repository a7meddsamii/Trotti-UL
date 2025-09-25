package ca.ulaval.glo4003.trotti.infrastructure.repository;

import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
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

    public void insertIntoBuyerTable(BuyerEntity buyer) {
        buyerTable.put(buyer.idul(), buyer);
    }

    public Optional<AccountEntity> selectFromAccountTable(Idul idul) {
        return Optional.ofNullable(accountTable.get(idul));
    }

    public Optional<AccountEntity> selectFromAccountTable(Email email) {
        return accountTable.values().stream().filter(account -> account.email().equals(email))
                .findFirst();
    }

    public Optional<BuyerEntity> selectFromBuyerTable(Idul idul) {
        return Optional.ofNullable(buyerTable.get(idul));
    }
}
