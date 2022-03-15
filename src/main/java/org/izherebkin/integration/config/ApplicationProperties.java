package org.izherebkin.integration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application")
public class ApplicationProperties {

    private Mode mode;

    private Long publicationPeriodSeconds;

    private String zeromqSubscriberPublicKey;

    private String zeromqSubscriberSecretKey;

    private String zeromqSubscriberBindingAddress;

    private String zeromqPublisherServerKey;

    private String zeromqPublisherConnectAddress;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Long getPublicationPeriodSeconds() {
        return publicationPeriodSeconds;
    }

    public void setPublicationPeriodSeconds(Long publicationPeriodSeconds) {
        this.publicationPeriodSeconds = publicationPeriodSeconds;
    }

    public String getZeromqSubscriberPublicKey() {
        return zeromqSubscriberPublicKey;
    }

    public void setZeromqSubscriberPublicKey(String zeromqSubscriberPublicKey) {
        this.zeromqSubscriberPublicKey = zeromqSubscriberPublicKey;
    }

    public String getZeromqSubscriberSecretKey() {
        return zeromqSubscriberSecretKey;
    }

    public void setZeromqSubscriberSecretKey(String zeromqSubscriberSecretKey) {
        this.zeromqSubscriberSecretKey = zeromqSubscriberSecretKey;
    }

    public String getZeromqSubscriberBindingAddress() {
        return zeromqSubscriberBindingAddress;
    }

    public void setZeromqSubscriberBindingAddress(String zeromqSubscriberBindingAddress) {
        this.zeromqSubscriberBindingAddress = zeromqSubscriberBindingAddress;
    }

    public String getZeromqPublisherServerKey() {
        return zeromqPublisherServerKey;
    }

    public void setZeromqPublisherServerKey(String zeromqPublisherServerKey) {
        this.zeromqPublisherServerKey = zeromqPublisherServerKey;
    }

    public String getZeromqPublisherConnectAddress() {
        return zeromqPublisherConnectAddress;
    }

    public void setZeromqPublisherConnectAddress(String zeromqPublisherConnectAddress) {
        this.zeromqPublisherConnectAddress = zeromqPublisherConnectAddress;
    }

}
