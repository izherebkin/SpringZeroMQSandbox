package org.izherebkin.integration.mapper;

import org.izherebkin.integration.dto.MessageDto;
import org.izherebkin.integration.dto.RandomDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class JsonMapperTest {

    private static MessageDto messageDto;

    @BeforeAll
    static void beforeAll() {
        RandomDto randomDto = new RandomDto();
        randomDto.setStringValue(RandomStringUtils.randomAlphanumeric(32));
        randomDto.setBooleanValue(ThreadLocalRandom.current().nextBoolean());
        randomDto.setLongValue(ThreadLocalRandom.current().nextLong());
        randomDto.setDoubleValue(ThreadLocalRandom.current().nextDouble());

        messageDto = new MessageDto();
        messageDto.setSenderName(RandomStringUtils.randomAlphabetic(32));
        messageDto.setSenderTime(LocalDateTime.now());
        messageDto.setRandoms(Collections.singletonList(randomDto));
    }

    @Test
    void toJsonFromJsonTest() {
        String json = JsonMapper.toJson(messageDto);
        MessageDto messageDto = JsonMapper.fromJson(json, MessageDto.class);

        Assert.hasText(json, "JSON must contains text");
        Assertions.assertNotNull(messageDto, "MessageDto must not be null");
        Assertions.assertEquals(JsonMapperTest.messageDto.toString(), messageDto.toString());
    }

}
