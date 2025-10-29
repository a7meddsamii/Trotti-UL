package ca.ulaval.glo4003.trotti.payment.domain.security;

public interface DataCodec {

    String encode(String data);

    String decode(String data);
}
