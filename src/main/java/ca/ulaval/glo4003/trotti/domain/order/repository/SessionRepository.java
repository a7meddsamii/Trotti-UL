package ca.ulaval.glo4003.trotti.domain.order.repository;

import ca.ulaval.glo4003.trotti.domain.order.Session;
import java.util.List;

public interface SessionRepository {

    List<Session> findAll();
}
