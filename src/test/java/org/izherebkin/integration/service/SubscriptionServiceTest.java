package org.izherebkin.integration.service;

import org.izherebkin.integration.config.ApplicationProperties;
import org.izherebkin.integration.zeromq.ZeroMqSubscriber;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.zeromq.Utils;
import org.zeromq.ZCert;

import java.io.IOException;

public class SubscriptionServiceTest {

    private static final String APP_PROPERTIES_FIELD_NAME = "appProperties";

    private static final String ZERO_MQ_SUBSCRIBER_FIELD_NAME = "zeroMqSubscriber";

    private static SubscriptionService subscriptionService;

    @BeforeAll
    static void beforeAll() throws IOException {
        subscriptionService = new SubscriptionService();
        ZCert zCert = new ZCert();
        int port = Utils.findOpenPort();
        ApplicationProperties appProperties = Mockito.mock(ApplicationProperties.class);
        Mockito.when(appProperties.getZeromqSubscriberPublicKey()).thenReturn(zCert.getPublicKeyAsZ85());
        Mockito.when(appProperties.getZeromqSubscriberSecretKey()).thenReturn(zCert.getSecretKeyAsZ85());
        Mockito.when(appProperties.getZeromqSubscriberBindingAddress()).thenReturn("tcp://*:" + port);
        ReflectionTestUtils.setField(subscriptionService, APP_PROPERTIES_FIELD_NAME, appProperties);
    }

    @Test
    void subscriptionServiceTest() throws Exception {
        subscriptionService.afterPropertiesSet();
        ZeroMqSubscriber zeroMqSubscriber = (ZeroMqSubscriber) ReflectionTestUtils.getField(subscriptionService, ZERO_MQ_SUBSCRIBER_FIELD_NAME);
        MatcherAssert.assertThat(zeroMqSubscriber, CoreMatchers.notNullValue());

        ZeroMqSubscriber zeroMqSubscriberSpy = Mockito.spy(zeroMqSubscriber);
        ReflectionTestUtils.setField(subscriptionService, ZERO_MQ_SUBSCRIBER_FIELD_NAME, zeroMqSubscriberSpy);
        subscriptionService.destroy();
        Mockito.verify(zeroMqSubscriberSpy, Mockito.times(1)).destroy();
    }

}
