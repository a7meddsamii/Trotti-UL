package ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.repository;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.dto.RidePermitPersistenceDto;
import ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.mapper.RidePermitPersistenceMapper;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryRidePermitRepository implements RidePermitRepository {
    private final Map<RidePermitId, RidePermitPersistenceDto> database = new HashMap<>();
    private final RidePermitPersistenceMapper mapper = new RidePermitPersistenceMapper();

    @Override
    public void save(RidePermit ridePermit) {
        RidePermitPersistenceDto dto = mapper.toDto(ridePermit);
        database.put(dto.id(), dto);
    }

    @Override
    public void saveAll(List<RidePermit> ridePermits) {
        ridePermits.forEach(this::save);
    }

    @Override
    public Optional<RidePermit> findById(RidePermitId ridePermitId) {
        return Optional.ofNullable(database.get(ridePermitId)).map(mapper::toDomain);
    }

    @Override
    public Optional<RidePermit> findByRiderIdAndRidePermitId(Idul riderId,
            RidePermitId ridePermitId) {
        return database.values().stream()
                .filter(dto -> dto.id().equals(ridePermitId)
                        && dto.riderId().equals(riderId))
                .findFirst().map(mapper::toDomain);
    }

    @Override
    public List<RidePermit> findAllByIdul(Idul idul) {
        return database.values().stream()
                .filter(dto -> dto.riderId().equals(idul))
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<RidePermit> findAllByDate(LocalDate date) {
        return database.values().stream().map(mapper::toDomain)
                .filter(permit -> permit.getSession().contains(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<RidePermit> findAllBySession(Session session) {
        return database.values().stream().filter(
                dto -> dto.session().equals(session))
                .map(mapper::toDomain).collect(Collectors.toList());
    }
}
