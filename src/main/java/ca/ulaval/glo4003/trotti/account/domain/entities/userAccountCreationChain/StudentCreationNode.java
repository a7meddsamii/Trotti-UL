package ca.ulaval.glo4003.trotti.account.domain.entities.userAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.*;

import java.time.LocalDate;
import java.util.Set;

public class StudentCreationNode extends UserAccountCreationNode {

    private final Set<Permission> permissions = Set.of(CartPermissions.CART_MODIFICATION,
            OrderPermissions.ORDER_CONFIRM,
            TripPermissions.MAKE_TRIP,
            MaintenancePermissions.REQUEST_MAINTENANCE);


    StudentCreationNode(UserAccountCreationNode next) {
        this.next = next;
    }

    @Override
    public Account CreateUserAccount(String name, LocalDate birthDate, Gender gender, Idul idul, Email email, Password password, Role role) {
        if (role == Role.STUDENT) {
            return new Account(name,birthDate,gender,idul,email,password,role,permissions);
        }
        return next.CreateUserAccount(name, birthDate, gender, idul, email, password, role);
    }



}
