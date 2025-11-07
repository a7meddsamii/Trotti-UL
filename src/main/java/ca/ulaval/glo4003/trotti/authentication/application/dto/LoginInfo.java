package ca.ulaval.glo4003.trotti.authentication.application.dto;

import ca.ulaval.glo4003.trotti.commons.domain.Email;

public record LoginInfo(Email email, String rawPassword) {
}
