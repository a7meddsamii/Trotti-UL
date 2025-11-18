package ca.ulaval.glo4003.trotti.account.domain.provider;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;

public interface EmployeeRegistryProvider {
    boolean exists(Idul idul);
}
