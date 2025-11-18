package ca.ulaval.glo4003.trotti.account.infrastructure.security.authorization;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import jakarta.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface RequiresPermissions {
    Permission[] value();

    Logical logical() default Logical.ALL;
}
