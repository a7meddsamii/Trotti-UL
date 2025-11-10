package ca.ulaval.glo4003.trotti.commons.domain.gateways;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;

public interface EmployeeRegistryGateway {
    boolean exist(Idul idul);
}
