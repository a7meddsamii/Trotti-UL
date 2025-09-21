package ca.ulaval.glo4003.trotti.infrastructure.repository.account;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.repository.UserInMemoryDatabase;

public class InMemoryAccountRepository implements AccountRepository {
	private final UserInMemoryDatabase database;
	
	public InMemoryAccountRepository(UserInMemoryDatabase database) {
		this.database = database;
	}
	
	
	@Override
	public void save(Account account) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public Account findByIdul(Idul idul) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
} 
