package ca.ulaval.glo4003.trotti.account.infrastructure.provider;

import ca.ulaval.glo4003.trotti.account.domain.exceptions.EmployeeException;
import ca.ulaval.glo4003.trotti.account.domain.provider.EmployeeRegistryProvider;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonEmployeeRegistryProvider implements EmployeeRegistryProvider {

    private final Path resourcePath;

    public JsonEmployeeRegistryProvider(Path resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public boolean exists(Idul idul) {
        return readFromClasspath().contains(idul);
    }

    private Set<Idul> readFromClasspath() {
        try (BufferedReader bufferedReader =
                Files.newBufferedReader(resourcePath, StandardCharsets.UTF_8)) {

            return bufferedReader.lines().map(String::trim).filter(rawIdul -> !rawIdul.isBlank())
                    .map(Idul::from).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new EmployeeException("Failed to fetch employees IDULs");
        }
    }
}
