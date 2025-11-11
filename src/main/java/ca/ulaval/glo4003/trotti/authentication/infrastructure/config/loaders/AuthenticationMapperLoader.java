package ca.ulaval.glo4003.trotti.authentication.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.authentication.infrastructure.gateway.IdentityMapper;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class AuthenticationMapperLoader extends Bootstrapper {

    @Override
    public void load() {
		this.loadGatewayMappers();
    }
	
	private void loadGatewayMappers(){
		this.resourceLocator.register(IdentityMapper.class, new IdentityMapper());
	}
}
