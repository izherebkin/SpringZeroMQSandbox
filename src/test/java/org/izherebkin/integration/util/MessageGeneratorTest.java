package org.izherebkin.integration.util;

import org.izherebkin.integration.dto.MessageDto;
import org.izherebkin.integration.dto.RandomDto;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

public class MessageGeneratorTest {

    @Test
    void generateTest() {
        MessageDto messageDto;
        try (MockedStatic<InetUtils> inetUtils = Mockito.mockStatic(InetUtils.class)) {
            inetUtils.when(InetUtils::getHostAddress).thenReturn(StringUtils.EMPTY);
            messageDto = MessageGenerator.generate();
        }

        MatcherAssert.assertThat(messageDto, CoreMatchers.notNullValue());
        MatcherAssert.assertThat(messageDto.getSenderName(), CoreMatchers.instanceOf(String.class));
        MatcherAssert.assertThat(messageDto.getSenderTime(), CoreMatchers.instanceOf(LocalDateTime.class));
        MatcherAssert.assertThat(messageDto.getRandoms(), CoreMatchers.notNullValue());
        MatcherAssert.assertThat(messageDto.getRandoms(), Matchers.hasSize(1));

        RandomDto randomDto = messageDto.getRandoms().iterator().next();
        MatcherAssert.assertThat(randomDto, CoreMatchers.notNullValue());
        MatcherAssert.assertThat(randomDto.getStringValue(), CoreMatchers.instanceOf(String.class));
        MatcherAssert.assertThat(randomDto.getBooleanValue(), CoreMatchers.instanceOf(Boolean.class));
        MatcherAssert.assertThat(randomDto.getLongValue(), CoreMatchers.instanceOf(Long.class));
        MatcherAssert.assertThat(randomDto.getDoubleValue(), CoreMatchers.instanceOf(Double.class));
    }

}
