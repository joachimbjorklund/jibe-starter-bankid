package nu.jibe.bankid.api;

/**
 *
 */
public interface RelyingPartyClient {
    AuthResponse auth(PersonalNumber personalNumber);
}
