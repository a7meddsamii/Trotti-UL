package ca.ulaval.glo4003.trotti.billing.application.ridepermit;

import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.AddTravelTimeDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.CreateRidePermitDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.factory.RidePermitFactory;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;

import java.util.List;

public class RidePermitApplicationService {
	private final RidePermitFactory ridePermitFactory;
	private final RidePermitRepository ridePermitRepository;
	private final PaymentGateway paymentGateway;
	private final RidePermitAssembler ridePermitAssembler;

	public RidePermitApplicationService(RidePermitFactory ridePermitFactory, RidePermitRepository ridePermitRepository, PaymentGateway paymentGateway, RidePermitAssembler ridePermitAssembler) {
		this.ridePermitFactory = ridePermitFactory;
		this.ridePermitRepository = ridePermitRepository;
		this.paymentGateway = paymentGateway;
		this.ridePermitAssembler = ridePermitAssembler;
	}

	public List<RidePermitDto> getRidePermits(Idul riderId) {
		List<RidePermit> ridePermits = ridePermitRepository.findAllByIdul(riderId);
		return ridePermitAssembler.assemble(ridePermits);
	}

	public void createRidePermits(Idul riderId, List<CreateRidePermitDto>  createRidePermits) {
		List<RidePermit> ridePermits = createRidePermits.stream().map(dto -> ridePermitFactory.create(
						riderId,
						dto.session(),
						dto.maxDailyTravelTime(),
						dto.billingFrequency()))
				.toList();
		ridePermitRepository.saveAll(ridePermits);
	}

	public void addTravelTime(Idul riderId, AddTravelTimeDto addTravelTimeDto) {
		RidePermit ridePermit = ridePermitRepository.findById(addTravelTimeDto.ridePermitId());
		ridePermit.addDailyTravelTime(riderId, addTravelTimeDto.startDateTime(), addTravelTimeDto.travelTime());
		ridePermitRepository.save(ridePermit);
	}
}
