package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.mappers.FleetApiMapper;
import java.util.List;

class StationControllerTest {

    private static final String STATION_ID = "VACHON";
    private static final String TRANSFER_ID = "transfer123";
    private static final Idul TECHNICIAN_IDUL = Idul.from("tech123");
    private static final List<Integer> SLOTS = List.of(1, 2, 3);

    private FleetApiMapper fleetApiMapper;
    private StationController controller;

}
