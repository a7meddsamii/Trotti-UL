package ca.ulaval.glo4003.trotti.domain.payment.security;

public interface DataCodec {

    String encode(String data);

    String decode(String data);
}
