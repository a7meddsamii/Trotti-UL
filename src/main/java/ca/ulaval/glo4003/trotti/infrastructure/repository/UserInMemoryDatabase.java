package ca.ulaval.glo4003.trotti.infrastructure.repository;

import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.infrastructure.repository.account.InMemoryAccountRepository;

import java.util.Map;

public class UserInMemoryDatabase {
	Map<Idul, InMemoryAccountRepository> users;
}
