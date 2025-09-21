package ca.ulaval.glo4003.trotti.infrastructure.auth.argon;

public record ArgonHasherConfig(int memoryCost, int iterations, int threads) {}
