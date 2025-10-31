package ca.ulaval.glo4003.trotti.config.bootstrapper;

import ca.ulaval.glo4003.trotti.config.locator.ComponentLocator;

public abstract class Bootstrapper {
    protected final ComponentLocator resourceLocator;

    protected Bootstrapper() {
        this.resourceLocator = ComponentLocator.getInstance();
    }

    public abstract void load();
}
