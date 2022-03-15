package org.izherebkin.integration.task.impl;

import org.izherebkin.integration.config.ApplicationProperties;
import org.izherebkin.integration.dto.MessageDto;
import org.izherebkin.integration.mapper.JsonMapper;
import org.izherebkin.integration.service.PublicationService;
import org.izherebkin.integration.task.LoopTask;
import org.izherebkin.integration.util.MessageGenerator;
import org.izherebkin.integration.zeromq.ZeroMqPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnBean(PublicationService.class)
public class PublicationTask implements InitializingBean, LoopTask, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(PublicationTask.class);

    @Autowired
    private ApplicationProperties appProperties;

    private ZeroMqPublisher zeroMqPublisher;

    @Override
    public void afterPropertiesSet() {
        zeroMqPublisher = new ZeroMqPublisher(appProperties.getZeromqPublisherServerKey(), appProperties.getZeromqPublisherConnectAddress());
    }

    @Override
    public Trigger getTrigger() {
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(appProperties.getPublicationPeriodSeconds(), TimeUnit.SECONDS);
        periodicTrigger.setInitialDelay(1);
        return periodicTrigger;
    }

    @Override
    public void run() {
        if (zeroMqPublisher != null) {
            MessageDto messageDto = MessageGenerator.generate();
            String s = JsonMapper.toJson(messageDto);
            zeroMqPublisher.publish(s);
            logger.info(messageDto.toString());
            logger.info(s);
        }
    }

    @Override
    public void destroy() throws Exception {
        if (zeroMqPublisher != null) {
            zeroMqPublisher.destroy();
        }
    }

}
