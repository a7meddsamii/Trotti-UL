package ca.ulaval.glo4003.trotti.application.account.dto;

public record AccountRegistration(
  String name,
  String birthDate,
  String gender,
  String idul,
  String email,
  String password
) {}
