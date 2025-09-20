package ca.ulaval.glo4003.trotti.application.account.dto;

import java.time.LocalDate;

public record CreateAccount(
  String name,
  LocalDate birthDate,
  String gender,
  String idul,
  String email,
  String password
) {}
