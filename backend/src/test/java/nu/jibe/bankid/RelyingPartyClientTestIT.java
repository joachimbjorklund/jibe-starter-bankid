package nu.jibe.bankid;

import nu.jibe.bankid.api.AuthResponse;
import nu.jibe.bankid.api.CollectProgressStatus;
import nu.jibe.bankid.api.CollectResponse;
import nu.jibe.bankid.api.RelyingPartyClient;
import nu.jibe.bankid.core.RelyingPartyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 *
 */
@Test(singleThreaded = true)
@SpringApplicationConfiguration(RelyingPartyClientTestIT.Conf.class)
public class RelyingPartyClientTestIT extends AbstractTestNGSpringContextTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelyingPartyClientTestIT.class);

    private RelyingPartyClient client;
    private TestUser testUser;

    @Value("${jibe-starter-bankid.trust-store}")
    private File trustStore;
    @Value("${jibe-starter-bankid.trust-store-type}")
    private String trustStoreType;
    @Value("${jibe-starter-bankid.trust-store-password}")
    private String trustStorePassword;

    @Value("${jibe-starter-bankid.key-store}")
    private File keyStore;
    @Value("${jibe-starter-bankid.key-store-type}")
    private String keyStoreType;
    @Value("${jibe-starter-bankid.key-store-password}")
    private String keyStorePassword;

    @BeforeMethod
    public void before() throws Exception {
        setupCrypto();

        client = new RelyingPartyClientBuilder().build();
        testUser = TestUser.randomTestUser();
    }

    @Test
    public void testAuth() throws Exception {
        AuthResponse authResponse = client.auth(testUser.getPersonalNumber());
        assertNotNull(authResponse);

        LOGGER.debug("orderRef: {}", authResponse.getOrderReference());
        LOGGER.debug("autoStartToken: {}", authResponse.getAutoStartToken());
    }

    @Test
    public void testAuthAndCollect() throws Exception {
        AuthResponse authResponse = client.auth(testUser.getPersonalNumber());
        assertNotNull(authResponse);
        LOGGER.debug("orderRef: {}", authResponse.getOrderReference());
        LOGGER.debug("autoStartToken: {}", authResponse.getAutoStartToken());

        CollectResponse collectResponse = client.collect(authResponse.getOrderReference());
        assertNotNull(authResponse);
        LOGGER.debug("progressStatus: {}", collectResponse.getCollectProgressStatus());
        assertEquals(collectResponse.getCollectProgressStatus(), CollectProgressStatus.OUTSTANDING_TRANSACTION);
    }

    private void setupCrypto() {
        System.setProperty("javax.net.ssl.trustStore", trustStore.getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStoreType", trustStoreType);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
        System.setProperty("javax.net.ssl.keyStore", keyStore.getAbsolutePath());
        System.setProperty("javax.net.ssl.keyStoreType", keyStoreType);
        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
    }

    @EnableAutoConfiguration
    public static class Conf {
        public Conf() {
        }
    }
}
