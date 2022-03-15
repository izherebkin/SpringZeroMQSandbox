package org.izherebkin.integration.zeromq;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.zeromq.Utils;
import org.zeromq.ZCert;
import zmq.ZMQ;

import java.io.IOException;

public class ZeroMqPubSubTest {

    private static SubscriptionListener subscriptionListener;

    private static ZeroMqSubscriber zeroMqSubscriber;

    private static ZeroMqPublisher zeroMqPublisher;

    @BeforeAll
    static void beforeAll() throws IOException {
        ZCert zCert = new ZCert();
        int port = Utils.findOpenPort();
        subscriptionListener = Mockito.spy(new ZeroMqSubscriptionListener());
        zeroMqSubscriber = new ZeroMqSubscriber(zCert.getPublicKeyAsZ85(), zCert.getSecretKeyAsZ85(), "tcp://*:" + port);
        zeroMqSubscriber.subscribe(subscriptionListener);
        zeroMqPublisher = new ZeroMqPublisher(zCert.getPublicKeyAsZ85(), "tcp://localhost:" + port);
        ZMQ.sleep(1);
    }

    @Test
    void pubSubTest() {
        String message = RandomStringUtils.randomAlphanumeric(512);
        zeroMqPublisher.publish(message);

        Mockito.verify(subscriptionListener, Mockito.timeout(1000)).onReceive(Mockito.eq(message));
    }

    @AfterAll
    static void afterAll() throws Exception {
        zeroMqSubscriber.unsubscribe();
        zeroMqSubscriber.destroy();
        zeroMqPublisher.destroy();
    }

    private static class ZeroMqSubscriptionListener implements SubscriptionListener {

        @Override
        public void onReceive(String s) {
        }

    }

}
