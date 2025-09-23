package ca.ulaval.glo4003.trotti.infrastructure.repository.account;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.repository.UserInMemoryDatabase;

import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {
	private final UserInMemoryDatabase database;
	
	public InMemoryAccountRepository(UserInMemoryDatabase database) {
		this.database = database;
	}
	
	@Override
	public void save(Account account) {
		AccountEntity accountEntity =
				new AccountEntity(
						account.getIdul(), account.getName(), account.getBirthDate(),
						account.getGender(), account.getEmail(), account.getPassword()
				);
		this.database.insertIntoAccountTable(accountEntity);
	}
	
	@Override
	public Optional<Account> findByEmail(Email email) {
		Optional<AccountEntity> accountQuery = database.selectFromAccountTable(email);
		return accountQuery.flatMap(this::mapResult);
	}
	
	@Override
	public Optional<Account> findByIdul(Idul idul) {
		Optional<AccountEntity> accountQuery = database.selectFromAccountTable(idul);
		return accountQuery.flatMap(this::mapResult);
	}
	
	private Optional<Account> mapResult(AccountEntity accountFound) {
		Account account = new Account(
				accountFound.name(), accountFound.birthDate(),
				accountFound.gender(), accountFound.idul(), accountFound.email(),
				accountFound.password()
		);
		
		return Optional.of(account);
	}
}
