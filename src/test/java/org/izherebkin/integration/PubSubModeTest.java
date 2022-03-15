package org.izherebkin.integration;

import org.izherebkin.integration.service.PublicationService;
import org.izherebkin.integration.service.SubscriptionService;
import org.izherebkin.integration.task.impl.PublicationTask;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(properties = {"application.mode=PUB_SUB"})
@DirtiesContext
public class PubSubModeTest {

    @Autowired(required = false)
    private ThreadPoolTaskScheduler publicationTaskScheduler;

    @Autowired(required = false)
    private PublicationService publicationService;

    @Autowired(required = false)
    private PublicationTask publicationTask;

    @Autowired(required = false)
    private SubscriptionService subscriptionService;

    @Test
    void pubSubModeTest() {
        MatcherAssert.assertThat(publicationTaskScheduler, CoreMatchers.notNullValue());
        MatcherAssert.assertThat(publicationService, CoreMatchers.notNullValue());
        MatcherAssert.assertThat(publicationTask, CoreMatchers.notNullValue());
        MatcherAssert.assertThat(subscriptionService, CoreMatchers.notNullValue());
    }

}
