package ca.ulaval.glo4003.trotti.account.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.*;
import java.time.LocalDate;
import java.time.Period;


public class Account {
    private final String name;
    private final Password password;
    private final LocalDate birthDate;
    private final Idul idul;
    private final Gender gender;
    private final Email email;
    private Role role;
    

    public Account(
            String name,
            LocalDate birthDate,
            Gender gender,
            Idul idul,
            Email email,
            Password password,
            Role role
            ) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.idul = idul;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public Idul getIdul() {
        return idul;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public int getAge() {
        LocalDate today = LocalDate.now();
        return Period.between(this.birthDate, today).getYears();
    }

    public boolean verifyPassword(String rawPassword) {
        return this.password.matches(rawPassword);
    }
}