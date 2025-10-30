package ca.ulaval.glo4003.trotti.config.bootstrapper;

import ca.ulaval.glo4003.trotti.config.ServerComponentLocator;

public abstract class Bootstrapper {
    protected final ServerComponentLocator resourceLocator;

    protected Bootstrapper() {
        this.resourceLocator = ServerComponentLocator.getInstance();
    }

    public abstract void load();
}
