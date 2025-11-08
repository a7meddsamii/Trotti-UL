package ca.ulaval.glo4003.trotti.authentication.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.account.application.dto.IdentityAccountDto;
import ca.ulaval.glo4003.trotti.authentication.domain.entities.Identity;
import ca.ulaval.glo4003.trotti.authentication.domain.values.HashedPassword;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;
import java.util.HashSet;
import java.util.Set;

public class IdentityMapper {

    public Identity toDomain(IdentityAccountDto identityAccountDto) {
        return new Identity(identityAccountDto.idul(), Role.fromString(identityAccountDto.role()),
                toDomainPermission(identityAccountDto.permissions()), HashedPassword.fromHashed(
                        identityAccountDto.hashedPassword(), identityAccountDto.hasher()));
    }

    private Set<Permission> toDomainPermission(Set<String> permissionsDto) {
        return permissionsDto.stream().map(Permission::fromString).collect(HashSet::new, Set::add,
                Set::addAll);
    }
}
