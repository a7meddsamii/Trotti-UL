package ca.ulaval.glo4003.trotti.application.trip.mappers;

import ca.ulaval.glo4003.trotti.application.trip.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RidePermitMapper {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public List<RidePermitDto> toDto(List<RidePermit> ridePermits) {
		List<RidePermitDto> ridePermitDtos = new ArrayList<>();
		for (RidePermit ridePermit : ridePermits) {
			ridePermitDtos.add(new RidePermitDto(ridePermit.getId().toString(), ridePermit.getIdul().toString(),
												 ridePermit.getSession().getStartDate().format(FORMATTER),
												 ridePermit.getSession().getEndDate().format(FORMATTER),
												 ridePermit.getSession().getSemester().getFrenchTranslation()));
		}
		return ridePermitDtos;
	}
}
