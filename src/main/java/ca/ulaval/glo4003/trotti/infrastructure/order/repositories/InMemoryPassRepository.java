package ca.ulaval.glo4003.trotti.infrastructure.order.repositories;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.domain.order.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.PassPersistenceMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryPassRepository implements PassRepository {

    private Map<Id, PassRecord> inMemoryPassDatabase = new HashMap<>();

    private final PassPersistenceMapper passPersistenceMapper;

    public InMemoryPassRepository(PassPersistenceMapper passPersistenceMapper) {
        this.passPersistenceMapper = passPersistenceMapper;
    }

    @Override
    public void saveAll(List<Pass> passes) {
        passes.stream().map(passPersistenceMapper::toRecord)
                .forEach(record -> inMemoryPassDatabase.put(record.id(), record));
    }

    @Override
    public List<Pass> findAllByIdul(Idul idul) {
        return inMemoryPassDatabase.values().stream().filter(record -> record.idul().equals(idul))
                .map(passPersistenceMapper::toDomain).toList();
    }
}
