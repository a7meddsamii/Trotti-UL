package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.security.SecureRandom;
import java.util.Random;

public class UnlockCode {

    private static final int MINIMUM_CODE_VALUE = 1000;
    private static final int MAXIMUM_CODE_VALUE = 999999;

    private final Idul travelerId;
    private final String code;

    private UnlockCode(String code, Idul travelerId) {
        this.code = code;
        this.travelerId = travelerId;
    }

    public static UnlockCode generateFromTravelerId(Idul id) {
        Random random = new SecureRandom();

        int code = MINIMUM_CODE_VALUE + random.nextInt(MAXIMUM_CODE_VALUE - MINIMUM_CODE_VALUE + 1);

        return new UnlockCode(String.valueOf(code), id);
    }

    public String getCode() {
        return code;
    }

    public Idul getTravelerId() {
        return travelerId;
    }

    public boolean belongsToTravelerAndIsValid(UnlockCode unlockCode, Idul travelerId) {
        return this.travelerId.equals(travelerId) && this.code.equals(unlockCode.getCode());
    }
}
