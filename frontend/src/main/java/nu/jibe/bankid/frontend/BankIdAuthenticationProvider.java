package nu.jibe.bankid.frontend;

import nu.jibe.bankid.api.AlreadyInProgressException;
import nu.jibe.bankid.api.AuthResponse;
import nu.jibe.bankid.api.CollectProgressStatus;
import nu.jibe.bankid.api.CollectResponse;
import nu.jibe.bankid.api.OrderReference;
import nu.jibe.bankid.api.RelyingPartyClient;
import nu.jibe.bankid.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
public class BankIdAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankIdAuthenticationProvider.class);

    private final Map<User.PersonalNumber, OrderReference> authMap = new HashMap<>();

    @Autowired
    private RelyingPartyClient relyingPartyClient;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LOGGER.debug("authenticate: {}", authentication);
        LOGGER.debug("principal: {}", authentication.getPrincipal());

        User.PersonalNumber personalNumber = new User.PersonalNumber(authentication.getPrincipal().toString());

        OrderReference orderReference = authMap.get(personalNumber);
        if (orderReference != null) {
            Authentication response = collect(orderReference);
            authMap.remove(personalNumber);
            return response;
        }

        AuthResponse authResponse;
        try {
            authResponse = relyingPartyClient.auth(personalNumber);
            LOGGER.debug("authResponse: {}", authResponse.getOrderReference());
            orderReference = authResponse.getOrderReference();
            authMap.put(personalNumber, orderReference);
        } catch (AlreadyInProgressException e) {
        }

        Authentication response = collect(orderReference);
        authMap.remove(personalNumber);
        return response;
    }

    private Authentication collect(OrderReference orderReference) {
        while (true) {
            LOGGER.debug("collecting...");
            CollectResponse response = relyingPartyClient.collect(orderReference);
            LOGGER.debug("response: " + response);
            if (response.getCollectProgressStatus() == CollectProgressStatus.COMPLETE) {
                return new BankIdAuthenticationToken(response.getUser().get());
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        LOGGER.debug("supports: {}", authentication);
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private class BankIdAuthenticationToken implements Authentication {
        private final User user;

        public BankIdAuthenticationToken(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return user;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return null;
        }
    }
}
