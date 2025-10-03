package ca.ulaval.glo4003.trotti.infrastructure.employee;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeIdulCsvReader {

    public Set<Idul> readFromClasspath(String resourcePath) {
        try (var in = Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream(resourcePath),
                "Resource not found: " + resourcePath);
             var br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

            return br.lines()
                    .map(String::trim)
                    .filter(s -> !s.isBlank())
                    .map(Idul::from)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read: " + resourcePath, e);
        }
    }
}