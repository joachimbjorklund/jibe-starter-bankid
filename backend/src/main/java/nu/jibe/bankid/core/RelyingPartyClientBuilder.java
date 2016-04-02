package nu.jibe.bankid.core;

import nu.jibe.bankid.api.RelyingPartyClient;
import nu.jibe.bankid.api.RelyingPartyClientConfiguration;

/**
 *
 */
public class RelyingPartyClientBuilder {
    private Config configuration = new Config();

    public RelyingPartyClient build() {
        return new DefaultRelyingPartyClient(configuration);
    }

    private class Config implements RelyingPartyClientConfiguration {
    }
}
