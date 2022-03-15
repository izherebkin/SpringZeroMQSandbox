package org.izherebkin.integration.config;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
public class ApplicationPropertiesTest {

    @Autowired
    private ApplicationProperties appProperties;

    @Test
    void gettersAndSettersTest() {
        MatcherAssert.assertThat(appProperties.getMode(), CoreMatchers.instanceOf(Mode.class));
        MatcherAssert.assertThat(appProperties.getPublicationPeriodSeconds(), CoreMatchers.instanceOf(Long.class));
        MatcherAssert.assertThat(appProperties.getZeromqSubscriberPublicKey(), CoreMatchers.instanceOf(String.class));
        MatcherAssert.assertThat(appProperties.getZeromqSubscriberSecretKey(), CoreMatchers.instanceOf(String.class));
        MatcherAssert.assertThat(appProperties.getZeromqSubscriberBindingAddress(), CoreMatchers.instanceOf(String.class));
        MatcherAssert.assertThat(appProperties.getZeromqPublisherServerKey(), CoreMatchers.instanceOf(String.class));
        MatcherAssert.assertThat(appProperties.getZeromqPublisherConnectAddress(), CoreMatchers.instanceOf(String.class));
    }

}
