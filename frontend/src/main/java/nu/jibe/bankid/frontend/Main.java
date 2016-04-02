package nu.jibe.bankid.frontend;

import nu.jibe.bankid.api.RelyingPartyClient;
import nu.jibe.bankid.core.RelyingPartyClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

/**
 *
 */
@SpringBootApplication
public class Main {
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

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public RelyingPartyClient relyingPartyClient() {
        setupCrypto();
        return new RelyingPartyClientBuilder().build();
    }

    private void setupCrypto() {
        System.setProperty("javax.net.ssl.trustStore", trustStore.getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStoreType", trustStoreType);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
        System.setProperty("javax.net.ssl.keyStore", keyStore.getAbsolutePath());
        System.setProperty("javax.net.ssl.keyStoreType", keyStoreType);
        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
    }
}
