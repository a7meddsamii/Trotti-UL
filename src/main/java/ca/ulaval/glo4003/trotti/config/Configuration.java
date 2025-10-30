package ca.ulaval.glo4003.trotti.config;

public abstract class Configuration {
    private boolean componentsCreated;

    protected Configuration() {
        this.componentsCreated = false;
    }

    public void initiate() {
        if (componentsCreated) {
            return;
        }

        load();
        componentsCreated = true;
    }

    protected abstract void load();
}
