package org.izherebkin.integration.config;

import org.izherebkin.integration.service.PublicationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class ApplicationConfig {

    @Bean
    @ConditionalOnBean(PublicationService.class)
    public ThreadPoolTaskScheduler publicationTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("PublicationTaskScheduler-");
        return taskScheduler;
    }

}
