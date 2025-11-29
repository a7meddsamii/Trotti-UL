package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.*;
import jakarta.ws.rs.core.Response;

public class StationController implements StationResource {


    public StationController() {
    }

    @Override
    public Response initiateTransfer(Idul userId, InitiateTransferRequest request) {

        return Response.serverError().build();
    }

    @Override
    public Response unloadScooters(Idul userId, String transferId, UnloadScootersRequest request) {
		return Response.serverError().build();
    }

    @Override
    public Response startMaintenance(Idul userId, StartMaintenanceRequest request) {
		return Response.serverError().build();
    }

    @Override
    public Response endMaintenance(Idul userId, EndMaintenanceRequest request) {
		return Response.serverError().build();
    }

    @Override
    public Response requestMaintenanceService(Idul userId, MaintenanceRequestRequest request) {
		return Response.serverError().build();
    }

    @Override
    public Response getAvailableSlots(String location) {
		return Response.serverError().build();
    }

    @Override
    public Response getOccupiedSlots(String location) {
		return Response.serverError().build();
    }
}
