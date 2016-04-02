package nu.jibe.bankid.core;

import com.bankid.rpservice.v4_0_0.RpFault;
import com.bankid.rpservice.v4_0_0.RpService;
import com.bankid.rpservice.v4_0_0.RpServicePortType;
import com.bankid.rpservice.v4_0_0.types.AuthenticateRequestType;
import com.bankid.rpservice.v4_0_0.types.OrderResponseType;
import nu.jibe.bankid.api.AuthResponse;
import nu.jibe.bankid.api.AutoStartToken;
import nu.jibe.bankid.api.OrderReference;
import nu.jibe.bankid.api.PersonalNumber;
import nu.jibe.bankid.api.RelyingPartyClient;
import nu.jibe.bankid.api.RelyingPartyClientConfiguration;

import java.util.Objects;

/**
 *
 */
public class DefaultRelyingPartyClient implements RelyingPartyClient {

    private final RelyingPartyClientConfiguration configuration;
    private final RpServicePortType rpServicePort;

    DefaultRelyingPartyClient(RelyingPartyClientConfiguration configuration) {
        this.configuration = Objects.requireNonNull(configuration);
        this.rpServicePort = createServicePort(configuration);
    }

    @Override
    public AuthResponse auth(PersonalNumber personalNumber) {
        try {
            return toAuthResponse(rpServicePort.authenticate(createAuthenticateRequest(personalNumber)));
        } catch (RpFault rpFault) {
            throw new RuntimeException(rpFault);
        }
    }

    private AuthResponse toAuthResponse(OrderResponseType orderResponse) {
        return new AuthResponse(new OrderReference(orderResponse.getOrderRef()), new AutoStartToken(orderResponse.getAutoStartToken()));
    }

    private AuthenticateRequestType createAuthenticateRequest(PersonalNumber personalNumber) {
        AuthenticateRequestType answer = new AuthenticateRequestType();
        answer.setPersonalNumber(personalNumber.getValue());
        return answer;
    }

    private RpServicePortType createServicePort(RelyingPartyClientConfiguration configuration) {
        return new RpService().getRpServiceSoapPort();
    }
}
