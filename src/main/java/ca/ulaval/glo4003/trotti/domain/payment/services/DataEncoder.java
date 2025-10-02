package ca.ulaval.glo4003.trotti.domain.payment.services;

public interface DataEncoder {

    String encode(String data);

    String decode(String data);
}
