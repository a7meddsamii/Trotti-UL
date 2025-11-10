package ca.ulaval.glo4003.trotti.commons.infrastructure.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.EmployeeRegistryGateway;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.EmployeeNotAuthorized;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonULavalEmployeeRegistryGateway implements EmployeeRegistryGateway {
	
	private final Path resourcePath;
	
	public JsonULavalEmployeeRegistryGateway(Path resourcePath) {
		this.resourcePath = resourcePath;
	}
	
	@Override
	public void validateIdentity(Idul idul) {
		Set<Idul> employeesIduls = readFromClasspath();
		if (!employeesIduls.contains(idul)) {
			throw new EmployeeNotAuthorized("IDUL " + idul + " is not a valid employee IDUL.");
		}
	}
	
	private Set<Idul> readFromClasspath() {
		try (BufferedReader br = Files.newBufferedReader(resourcePath, StandardCharsets.UTF_8)) {
			
			return br.lines().map(String::trim).filter(s -> !s.isBlank()).map(Idul::from)
					.collect(Collectors.toSet());
		} catch (IOException e) {
			throw new IllegalStateException("Failed to fetch employees IDULs");
		}
	}
}
