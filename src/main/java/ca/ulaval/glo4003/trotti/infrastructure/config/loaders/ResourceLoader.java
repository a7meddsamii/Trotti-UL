package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;

public abstract class ResourceLoader {
    protected final ServerResourceLocator resourceLocator;

    protected ResourceLoader() {
        this.resourceLocator = ServerResourceLocator.getInstance();
    }

    public abstract void load();
}
