package nu.jibe.bankid.api;

/**
 *
 */
public class CollectResponse {
    private final CollectProgressStatus collectProgressStatus;

    public CollectResponse(CollectProgressStatus collectProgressStatus) {
        this.collectProgressStatus = collectProgressStatus;
    }

    public CollectProgressStatus getCollectProgressStatus() {
        return collectProgressStatus;
    }
}
