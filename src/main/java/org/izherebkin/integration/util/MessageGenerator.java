package org.izherebkin.integration.util;

import org.izherebkin.integration.dto.MessageDto;
import org.izherebkin.integration.dto.RandomDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class MessageGenerator {

    private static final Logger logger = LoggerFactory.getLogger(MessageGenerator.class);

    public static MessageDto generate() {
        RandomDto randomDto = new RandomDto();
        randomDto.setStringValue(RandomStringUtils.randomAlphanumeric(32));
        randomDto.setBooleanValue(ThreadLocalRandom.current().nextBoolean());
        randomDto.setLongValue(ThreadLocalRandom.current().nextLong());
        randomDto.setDoubleValue(ThreadLocalRandom.current().nextDouble());

        MessageDto messageDto = new MessageDto();
        String inetAddress = null;
        try {
            inetAddress = InetUtils.getHostAddress();
        } catch (UnknownHostException e) {
            logger.error(ExceptionUtils.getRootCauseMessage(e), e);
        }
        messageDto.setSenderName(inetAddress);
        messageDto.setSenderTime(LocalDateTime.now());
        messageDto.setRandoms(Collections.singletonList(randomDto));
        return messageDto;
    }

}
