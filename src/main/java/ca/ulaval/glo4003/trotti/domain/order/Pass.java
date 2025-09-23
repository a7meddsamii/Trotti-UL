package ca.ulaval.glo4003.trotti.domain.order;

public class Pass {
    private int maximumTravelingTimePerDay; // In minutes, and could be DAO ?
    private Session session;

    public Pass(
            int maximumTravelingTimePerDay,
            Session session
    ) {
        this.maximumTravelingTimePerDay = maximumTravelingTimePerDay;
        this.session = session;
    }

    public int getMaximumTravelingTimePerDay() {
        return maximumTravelingTimePerDay;
    }

    public Session getSession() {
        return session;
    }
}
