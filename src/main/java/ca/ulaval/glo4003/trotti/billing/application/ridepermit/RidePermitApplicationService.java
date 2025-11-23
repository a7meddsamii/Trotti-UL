package ca.ulaval.glo4003.trotti.billing.application.ridepermit;

import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.AddTravelTimeDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.CreateRidePermitDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.factory.RidePermitFactory;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

public class RidePermitApplicationService {
    private final RidePermitFactory ridePermitFactory;
    private final RidePermitRepository ridePermitRepository;
    private final PaymentGateway paymentGateway;
    private final RidePermitAssembler ridePermitAssembler;
	private final Clock clock;

    public RidePermitApplicationService(
			RidePermitFactory ridePermitFactory,
			RidePermitRepository ridePermitRepository,
			PaymentGateway paymentGateway,
			RidePermitAssembler ridePermitAssembler,
			Clock clock
	) {
        this.ridePermitFactory = ridePermitFactory;
        this.ridePermitRepository = ridePermitRepository;
        this.paymentGateway = paymentGateway;
        this.ridePermitAssembler = ridePermitAssembler;
		this.clock = clock;
	}

    public List<RidePermitDto> getRidePermits(Idul riderId) {
        List<RidePermit> ridePermits = ridePermitRepository.findAllByIdul(riderId);
        return ridePermitAssembler.assemble(ridePermits);
    }
	
	public RidePermitDto getRidePermit(Idul riderId, RidePermitId ridePermitId) {
		RidePermit ridePermit = ridePermitRepository.findByRiderIdAndRidePermitId(riderId, ridePermitId)
				.orElseThrow(()-> new NotFoundException("Ride permit " + ridePermitId + " not found"));
		return ridePermitAssembler.assemble(ridePermit);
	}

    public void createRidePermits(Idul riderId, List<CreateRidePermitDto> createRidePermits) {
        List<RidePermit> ridePermits =
                createRidePermits.stream().map(dto -> ridePermitFactory.create(riderId,
                        dto.session(), dto.maxDailyTravelTime(), dto.billingFrequency())).toList();
        ridePermitRepository.saveAll(ridePermits);
    }

    public void addTravelTime(Idul riderId, AddTravelTimeDto addTravelTimeDto) {
        RidePermit ridePermit = ridePermitRepository.findById(addTravelTimeDto.ridePermitId())
				.orElseThrow(()-> new NotFoundException("Ride permit " + addTravelTimeDto.ridePermitId() + " not found"));
        ridePermit.addDailyTravelTime(riderId, addTravelTimeDto.startDateTime(),
                addTravelTimeDto.travelTime());
        ridePermitRepository.save(ridePermit);
    }
	
	public boolean isRidePermitActive(Idul riderId, RidePermitId ridePermitId) {
		RidePermit ridePermit = ridePermitRepository.findById(ridePermitId)
				.orElseThrow(()-> new NotFoundException("Ride permit " + ridePermitId + " not found"));
		return ridePermit.isActiveForRides(riderId, LocalDate.now(clock));
	}
}
