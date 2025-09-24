package ca.ulaval.glo4003.trotti.domain.order;

public class Pass {
    private MaximumTravelingTime maximumTravelingTime; // In minutes per day
    private Session session;

    public Pass(MaximumTravelingTime maximumTravelingTime, Session session) {
        this.maximumTravelingTime = maximumTravelingTime;
        this.session = session;
    }

    public MaximumTravelingTime getMaximumTravelingTime() {
        return maximumTravelingTime;
    }

    public Session getSession() {
        return session;
    }
}
