package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.infrastructure.config.ServerComponentLocator;

public abstract class Bootstrapper {
    protected final ServerComponentLocator resourceLocator;

    protected Bootstrapper() {
        this.resourceLocator = ServerComponentLocator.getInstance();
    }

    public abstract void load();
}
