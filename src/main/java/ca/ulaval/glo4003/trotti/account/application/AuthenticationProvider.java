package ca.ulaval.glo4003.trotti.account.application;

public interface AuthenticationProvider<T,K> {
    T verify(K loginInfo);
}
