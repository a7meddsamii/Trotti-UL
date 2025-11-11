package ca.ulaval.glo4003.trotti.authentication.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.application.port.AccountQuery;
import ca.ulaval.glo4003.trotti.account.application.port.AccountService;
import ca.ulaval.glo4003.trotti.authentication.domain.gateway.IdentityGateway;
import ca.ulaval.glo4003.trotti.authentication.infrastructure.gateway.IdentityGetawayAdapter;
import ca.ulaval.glo4003.trotti.authentication.infrastructure.gateway.IdentityMapper;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class AuthenticationGatewayLoader extends Bootstrapper {
	
	@Override
	public void load() {
		this.loadIdentityGateway();
	}
	
	private void loadIdentityGateway() {
		IdentityMapper identityMapper = this.resourceLocator.resolve(IdentityMapper.class);
		AccountQuery accountQuery = this.resourceLocator.resolve(AccountService.class);
		this.resourceLocator.register(IdentityGateway.class, new IdentityGetawayAdapter(identityMapper, accountQuery));
	}
}
