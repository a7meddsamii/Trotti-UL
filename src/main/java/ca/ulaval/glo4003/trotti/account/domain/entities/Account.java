package ca.ulaval.glo4003.trotti.account.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Gender;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.account.domain.values.Password;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Account {
    private final String name;
    private final Password password;
    private final LocalDate birthDate;
    private final Idul idul;
    private final Gender gender;
    private final Email email;
    private final Set<Permission> permissions;

    public Account(
            String name,
            LocalDate birthDate,
            Gender gender,
            Idul idul,
            Email email,
            Password password) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.idul = idul;
        this.email = email;
        this.password = password;
        this.permissions = new HashSet<>();
    }

    public void  addPermission(Permission... permission) {
        permissions.addAll(Arrays.stream(permission).toList());
    }

    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    public void removePermission(Permission permission) {
        permissions.remove(permission);
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

    public int getAge() {
        LocalDate today = LocalDate.now();
        return Period.between(this.birthDate, today).getYears();
    }

    public boolean verifyPassword(String rawPassword) {
        return this.password.matches(rawPassword);
    }
}
