package org.izherebkin.integration.zeromq;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.SocketType;
import org.zeromq.ZAuth;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import zmq.util.Z85;

public class ZeroMqSubscriber {

    private final ZContext zContext;

    private final ZAuth zAuth;

    private final ZMQ.Socket zmqSocket;

    private SubscriptionListener subscriptionListener;

    public ZeroMqSubscriber(String publicKey, String secretKey, String bindingAddress) {
        zContext = new ZContext();

        zAuth = new ZAuth(zContext);
        zAuth.configureCurve(ZAuth.CURVE_ALLOW_ANY);

        zmqSocket = zContext.createSocket(SocketType.SUB);
        zmqSocket.setCurveServer(true);
        zmqSocket.setCurvePublicKey(Z85.decode(publicKey));
        zmqSocket.setCurveSecretKey(Z85.decode(secretKey));
        zmqSocket.bind(bindingAddress);

        Thread zeroMqSubscriberThread = new ZeroMqSubscriberThread();
        zeroMqSubscriberThread.setName(zeroMqSubscriberThread.getClass().getSimpleName());
        zeroMqSubscriberThread.start();
    }

    public void subscribe(SubscriptionListener subscriptionListener) {
        this.subscriptionListener = subscriptionListener;
        zmqSocket.subscribe(ZMQ.SUBSCRIPTION_ALL);
    }

    public void unsubscribe() {
        zmqSocket.unsubscribe(ZMQ.SUBSCRIPTION_ALL);
    }

    public void destroy() throws Exception {
        unsubscribe();
        zAuth.close();
        zContext.close();
    }

    private final class ZeroMqSubscriberThread extends Thread {

        private final Logger logger = LoggerFactory.getLogger(ZeroMqSubscriberThread.class);

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String s = zmqSocket.recvStr();
                    try {
                        if (subscriptionListener != null) {
                            subscriptionListener.onReceive(s);
                        }
                    } catch (Exception e) {
                        logger.error(ExceptionUtils.getRootCauseMessage(e), e);
                    }
                }
            } catch (Exception e) {
                logger.error(ExceptionUtils.getRootCauseMessage(e), e);
            }
        }

    }

}
