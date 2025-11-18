package ca.ulaval.glo4003.trotti.account.infrastructure.security.authentication.jwtsecuritycontext;

import jakarta.ws.rs.core.SecurityContext;

public class JwtSecurityContext implements SecurityContext {
    private final JwtPrincipal jwtPrincipal;
    private final Boolean isSecure;

    public JwtSecurityContext(JwtPrincipal jwtPrincipal, Boolean isSecure) {
        this.jwtPrincipal = jwtPrincipal;
        this.isSecure = isSecure;
    }

    @Override
    public JwtPrincipal getUserPrincipal() {
        return jwtPrincipal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return jwtPrincipal.getRole().toString().equalsIgnoreCase(role);
    }

    @Override
    public boolean isSecure() {
        return isSecure;
    }

    @Override
    public String getAuthenticationScheme() {
        return "";
    }
}
