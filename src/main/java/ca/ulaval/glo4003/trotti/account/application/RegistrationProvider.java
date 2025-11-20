package ca.ulaval.glo4003.trotti.account.application;

public interface RegistrationProvider<T,K>{
    T register(K RegistrationInfo);
}
