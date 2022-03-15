package org.izherebkin.integration.service;

import org.izherebkin.integration.config.ApplicationProperties;
import org.izherebkin.integration.dto.MessageDto;
import org.izherebkin.integration.mapper.JsonMapper;
import org.izherebkin.integration.zeromq.ZeroMqSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnExpression("'${application.mode}' == 'PUB_SUB' or '${application.mode}' == 'SUB'")
public class SubscriptionService implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    @Autowired
    private ApplicationProperties appProperties;

    private ZeroMqSubscriber zeroMqSubscriber;

    @Override
    public void afterPropertiesSet() {
        zeroMqSubscriber = new ZeroMqSubscriber(appProperties.getZeromqSubscriberPublicKey(),
                appProperties.getZeromqSubscriberSecretKey(), appProperties.getZeromqSubscriberBindingAddress());
        zeroMqSubscriber.subscribe(s -> {
            logger.info(s);
            MessageDto messageDto = JsonMapper.fromJson(s, MessageDto.class);
            logger.info(messageDto.toString());
        });
    }

    @Override
    public void destroy() throws Exception {
        if (zeroMqSubscriber != null) {
            zeroMqSubscriber.destroy();
        }
    }

}
