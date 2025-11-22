package ca.ulaval.glo4003.trotti.account.application.dto;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;

public record PasswordLoginDto(Email email, String password) {
}
