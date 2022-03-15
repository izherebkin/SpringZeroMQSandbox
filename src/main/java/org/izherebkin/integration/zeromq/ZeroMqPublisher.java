package org.izherebkin.integration.zeromq;

import org.zeromq.*;
import zmq.util.Z85;

public class ZeroMqPublisher {

    private final ZContext zContext;

    private final ZAuth zAuth;

    private final ZMQ.Socket zmqSocket;

    public ZeroMqPublisher(String serverKey, String connectAddress) {
        zContext = new ZContext();

        zAuth = new ZAuth(zContext);
        zAuth.configureCurve(ZAuth.CURVE_ALLOW_ANY);

        zmqSocket = zContext.createSocket(SocketType.PUB);
        ZCert zCert = new ZCert();
        zmqSocket.setCurvePublicKey(zCert.getPublicKey());
        zmqSocket.setCurveSecretKey(zCert.getSecretKey());
        zmqSocket.setCurveServerKey(Z85.decode(serverKey));
        zmqSocket.connect(connectAddress);
    }

    public void publish(String s) {
        zmqSocket.send(s);
    }

    public void destroy() throws Exception {
        zAuth.close();
        zContext.close();
    }

}
