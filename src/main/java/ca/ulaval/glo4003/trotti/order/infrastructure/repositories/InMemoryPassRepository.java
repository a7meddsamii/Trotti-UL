package ca.ulaval.glo4003.trotti.order.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.commons.domain.values.ids.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.order.domain.values.PassId;
import ca.ulaval.glo4003.trotti.order.infrastructure.mappers.PassPersistenceMapper;
import ca.ulaval.glo4003.trotti.order.infrastructure.repositories.records.PassRecord;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryPassRepository implements PassRepository {

    private final Map<PassId, PassRecord> inMemoryPassDatabase = new HashMap<>();

    private final PassPersistenceMapper passPersistenceMapper;

    public InMemoryPassRepository(PassPersistenceMapper passPersistenceMapper) {
        this.passPersistenceMapper = passPersistenceMapper;
    }

    @Override
    public void saveAll(List<Pass> passes) {
        passes.stream().map(passPersistenceMapper::toRecord)
                .forEach(record -> inMemoryPassDatabase.put(record.passId(), record));
    }

    @Override
    public List<Pass> findAllByIdul(Idul idul) {
        return inMemoryPassDatabase.values().stream().filter(record -> record.owner().equals(idul))
                .map(passPersistenceMapper::toDomain).toList();
    }
}
