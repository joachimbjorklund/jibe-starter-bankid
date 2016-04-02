package nu.jibe.bankid.api;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 *
 */
public class CollectResponse {
    private final CollectProgressStatus collectProgressStatus;
    private final User user;
    private final Signature signature;
    private final OcspResponse ocspResponse;

    public CollectResponse(CollectProgressStatus collectProgressStatus, User user, Signature signature, OcspResponse ocspResponse) {
        this.collectProgressStatus = requireNonNull(collectProgressStatus);
        this.user = user;
        this.signature = signature;
        this.ocspResponse = ocspResponse;
    }

    public CollectProgressStatus getCollectProgressStatus() {
        return collectProgressStatus;
    }

    public Optional<OcspResponse> getOcspResponse() {
        return Optional.ofNullable(ocspResponse);
    }

    public Optional<Signature> getSignature() {
        return Optional.ofNullable(signature);
    }

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("collectProgressStatus", collectProgressStatus)
                .append("user", user)
                .append("signature", signature)
                .append("ocspResponse", ocspResponse)
                .toString();
    }
}
