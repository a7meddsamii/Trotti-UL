package ca.ulaval.glo4003.trotti.trip.domain.factories;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.List;

public class TransferFactory {
    
    public Transfer createTransfer(Idul technicianId, Station station, List<SlotNumber> sourceSlots) {
        Transfer transfer = new Transfer(station.getLocation(), technicianId);
        
        for (SlotNumber slot : sourceSlots) {
            ScooterId scooterId = station.getScooter(slot);
            transfer.getScooters().put(scooterId, false);
        }
        
        return transfer;
    }
}