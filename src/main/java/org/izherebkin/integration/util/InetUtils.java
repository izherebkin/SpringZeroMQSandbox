package org.izherebkin.integration.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetUtils {

    public static String getHostAddress() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        return inetAddress.getHostAddress();
    }

}
