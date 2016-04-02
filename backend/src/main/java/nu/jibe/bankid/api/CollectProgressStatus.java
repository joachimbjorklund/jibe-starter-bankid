package nu.jibe.bankid.api;

/**
 *
 */
public enum CollectProgressStatus {
    OUTSTANDING_TRANSACTION,
    NO_CLIENT,
    STARTED,
    USER_SIGN,
    USER_REQ,
    COMPLETE
}
