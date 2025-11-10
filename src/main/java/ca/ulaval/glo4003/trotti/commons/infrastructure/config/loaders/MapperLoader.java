package ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.commons.infrastructure.gateways.sessions.SessionMapper;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class MapperLoader extends Bootstrapper {

    @Override
    public void load() {
        loadSessionMappers();
    }

    private void loadSessionMappers() {
        this.resourceLocator.register(SessionMapper.class, new SessionMapper());
    }
}
