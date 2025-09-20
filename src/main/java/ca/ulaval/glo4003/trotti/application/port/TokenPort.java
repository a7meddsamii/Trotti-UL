package ca.ulaval.glo4003.trotti.application.port;

public interface TokenPort {
  String generateToken(String accountId);
}
