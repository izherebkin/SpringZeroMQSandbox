package org.izherebkin.integration.service;

import org.izherebkin.integration.task.LoopTask;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnExpression("'${application.mode}' == 'PUB_SUB' or '${application.mode}' == 'PUB'")
public class PublicationService implements InitializingBean {

    @Autowired
    private ThreadPoolTaskScheduler publicationTaskScheduler;

    @Autowired
    private LoopTask publicationTask;

    @Override
    public void afterPropertiesSet() {
        publicationTaskScheduler.schedule(publicationTask, publicationTask.getTrigger());
    }

}
