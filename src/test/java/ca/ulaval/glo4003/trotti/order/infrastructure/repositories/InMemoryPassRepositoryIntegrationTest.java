package ca.ulaval.glo4003.trotti.order.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.order.domain.values.PassId;
import ca.ulaval.glo4003.trotti.order.fixtures.PassFixture;
import ca.ulaval.glo4003.trotti.order.infrastructure.mappers.PassPersistenceMapper;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryPassRepositoryIntegrationTest {

    private static final Idul AN_IDUL = Idul.from("a1234567");

    private PassRepository passRepository;

    @BeforeEach
    void setup() {
        passRepository = new InMemoryPassRepository(new PassPersistenceMapper());
        passRepository.saveAll(
                List.of(new PassFixture().withIdul(AN_IDUL).withId(PassId.randomId()).build(),
                        new PassFixture().withIdul(AN_IDUL).withId(PassId.randomId()).build()));
    }

    @Test
    void givenSavedPassWithIdul_whenFindAllByIdul_thenReturnPassList() {
        List<Pass> retrievedPassList = passRepository.findAllByIdul(AN_IDUL);

        Assertions.assertEquals(2, retrievedPassList.size());
        Assertions.assertTrue(
                retrievedPassList.stream().allMatch(pass -> pass.getBuyerIdul().equals(AN_IDUL)));
    }

    @Test
    void givenAnotherIdul_whenFindAllByIdul_thenReturnEmptyList() {
        List<Pass> retrievedPassList = passRepository.findAllByIdul(Idul.from("b7654321"));

        Assertions.assertTrue(retrievedPassList.isEmpty());
    }

    @Test
    void givenSavedPass_whenSaveAll_thenPassesAreSaved() {
        Pass newPass = new PassFixture().withIdul(AN_IDUL).withId(PassFixture.AN_PASSID).build();
        passRepository.saveAll(List.of(newPass));

        List<Pass> retrievedPassList = passRepository.findAllByIdul(AN_IDUL);

        Assertions.assertEquals(3, retrievedPassList.size());
        Assertions.assertTrue(retrievedPassList.stream()
                .anyMatch(pass -> pass.getId().equals(PassFixture.AN_PASSID)));
    }

}
