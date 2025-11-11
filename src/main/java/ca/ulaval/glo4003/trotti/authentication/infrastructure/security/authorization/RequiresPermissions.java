package ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authorization;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import jakarta.ws.rs.NameBinding;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface RequiresPermissions {
	Permission[] value();
	
	Logical logical() default Logical.ALL;
}
