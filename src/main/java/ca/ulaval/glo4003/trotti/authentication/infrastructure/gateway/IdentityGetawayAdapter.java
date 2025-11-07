package ca.ulaval.glo4003.trotti.authentication.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.port.AccountQuery;
import ca.ulaval.glo4003.trotti.authentication.domain.entities.Identity;
import ca.ulaval.glo4003.trotti.authentication.domain.gateway.IdentityGateway;
import ca.ulaval.glo4003.trotti.commons.domain.Email;

public class IdentityGetawayAdapter implements IdentityGateway {
	
	private final IdentityMapper identityMapper;
	private final AccountQuery accountQuery;
	
	public IdentityGetawayAdapter(IdentityMapper identityMapper, AccountQuery accountQuery) {
		this.identityMapper = identityMapper;
		this.accountQuery = accountQuery;
	}
	
	@Override
	public Identity findByEmail(Email email) {
		AccountDto accountDto = accountQuery.findByEmail(email);
		return identityMapper.toDomain(accountDto);
	}
}
