package ca.ulaval.glo4003.trotti.domain.account;

import java.time.LocalDate;
import java.time.Period;

public class Account {
  private final String name;
  private final LocalDate birthDate;
  private Gender gender;
  private final String idul;
  private Email email;
  private final String hashedPassword; //"23edbwywbdhysgshshs"

  public Account(
    String name,
    LocalDate birthDate,
    Gender gender,
    String idul,
    Email email,
    String hashedPassword
  ) {
    this.name = name;
    this.gender = gender;
    this.birthDate = birthDate;
    this.idul = idul;
    this.email = email;
    this.hashedPassword = hashedPassword;
  }

  public String getName() {
    return name;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public Gender getGender() {
    return gender;
  }

  public int getAge() {
    LocalDate today = LocalDate.now();
    return Period.between(this.birthDate, today).getYears();
  }

  public String getIdul() {
    return idul;
  }

  public Email getEmail() {
    return email;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }
}
