package org.izherebkin.integration.util;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.net.UnknownHostException;

public class InetUtilsTest {

    @Test
    void getHostAddressTest() throws UnknownHostException {
        String hostAddress = InetUtils.getHostAddress();

        Assert.hasText(hostAddress, "Host address must contains text");
    }

}
