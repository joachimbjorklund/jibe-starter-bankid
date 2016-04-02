package nu.jibe.bankid.api;

/**
 *
 */
public interface RelyingPartyClient {
    AuthResponse auth(User.PersonalNumber personalNumber) throws AlreadyInProgressException;

    CollectResponse collect(OrderReference orderReference);
}
