package ca.ulaval.glo4003.trotti.infrastructure.config.providers;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeIdulCsvProvider {

    public Set<Idul> readFromClasspath(Path resourcePath) {
        try (BufferedReader br = Files.newBufferedReader(resourcePath, StandardCharsets.UTF_8)) {

            return br.lines().map(String::trim).filter(s -> !s.isBlank()).map(Idul::from)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read: " + resourcePath, e);
        }
    }
}
