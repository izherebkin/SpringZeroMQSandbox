package org.izherebkin.integration.service;

import org.izherebkin.integration.task.LoopTask;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
public class PublicationServiceTest {

    @Autowired
    private PublicationService publicationService;

    @SpyBean
    private ThreadPoolTaskScheduler publicationTaskScheduler;

    @SpyBean
    private LoopTask publicationTask;

    @Test
    void publicationServiceTest() {
        Mockito.verify(publicationTaskScheduler, Mockito.timeout(1000)).schedule(Mockito.eq(publicationTask), Mockito.isA(Trigger.class));
        Mockito.verify(publicationTask, Mockito.timeout(1000)).run();
    }

}
