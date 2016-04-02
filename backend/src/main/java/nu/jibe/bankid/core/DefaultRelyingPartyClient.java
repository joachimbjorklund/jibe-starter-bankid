package nu.jibe.bankid.core;

import com.bankid.rpservice.v4_0_0.RpFault;
import com.bankid.rpservice.v4_0_0.RpService;
import com.bankid.rpservice.v4_0_0.RpServicePortType;
import com.bankid.rpservice.v4_0_0.types.AuthenticateRequestType;
import com.bankid.rpservice.v4_0_0.types.CollectResponseType;
import com.bankid.rpservice.v4_0_0.types.FaultStatusType;
import com.bankid.rpservice.v4_0_0.types.OrderResponseType;
import com.bankid.rpservice.v4_0_0.types.UserInfoType;
import nu.jibe.bankid.api.AlreadyInProgressException;
import nu.jibe.bankid.api.AuthResponse;
import nu.jibe.bankid.api.AutoStartToken;
import nu.jibe.bankid.api.CollectProgressStatus;
import nu.jibe.bankid.api.CollectResponse;
import nu.jibe.bankid.api.OcspResponse;
import nu.jibe.bankid.api.OrderReference;
import nu.jibe.bankid.api.RelyingPartyClient;
import nu.jibe.bankid.api.RelyingPartyClientConfiguration;
import nu.jibe.bankid.api.Signature;
import nu.jibe.bankid.api.User;

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
    public AuthResponse auth(User.PersonalNumber personalNumber) throws AlreadyInProgressException {
        try {
            return toAuthResponse(rpServicePort.authenticate(createAuthenticateRequest(personalNumber)));
        } catch (RpFault rpFault) {
            if (rpFault.getFaultInfo().getFaultStatus() == FaultStatusType.ALREADY_IN_PROGRESS) {
                throw new AlreadyInProgressException();
            }
            throw new RuntimeException(rpFault);
        }
    }

    @Override
    public CollectResponse collect(OrderReference orderReference) {
        try {
            return toCollectResponse(rpServicePort.collect(orderReference.getValue()));
        } catch (RpFault rpFault) {
            throw new RuntimeException(rpFault);
        }
    }

    private CollectResponse toCollectResponse(CollectResponseType collectResponseType) {
        return new CollectResponse(
                CollectProgressStatus.valueOf(collectResponseType.getProgressStatus().name()),
                (collectResponseType.getUserInfo() != null) ? toUser(collectResponseType.getUserInfo()) : null,
                (collectResponseType.getSignature() != null) ? new Signature(collectResponseType.getSignature()) : null,
                (collectResponseType.getOcspResponse() != null) ? new OcspResponse(collectResponseType.getOcspResponse()) : null);
    }

    private User toUser(UserInfoType userInfo) {
        return new User(
                new User.FirstName(userInfo.getGivenName()),
                new User.LastName(userInfo.getSurname()),
                new User.Name(userInfo.getName()),
                new User.PersonalNumber(userInfo.getPersonalNumber()),
                userInfo.getNotBefore().toGregorianCalendar().toZonedDateTime().toLocalDate(),
                userInfo.getNotAfter().toGregorianCalendar().toZonedDateTime().toLocalDate(),
                new User.IPAddress(userInfo.getIpAddress()));
    }

    private AuthResponse toAuthResponse(OrderResponseType orderResponse) {
        return new AuthResponse(new OrderReference(orderResponse.getOrderRef()), new AutoStartToken(orderResponse.getAutoStartToken()));
    }

    private AuthenticateRequestType createAuthenticateRequest(User.PersonalNumber personalNumber) {
        AuthenticateRequestType answer = new AuthenticateRequestType();
        answer.setPersonalNumber(personalNumber.getValue());
        return answer;
    }

    private RpServicePortType createServicePort(RelyingPartyClientConfiguration configuration) {
        return new RpService().getRpServiceSoapPort();
    }
}
