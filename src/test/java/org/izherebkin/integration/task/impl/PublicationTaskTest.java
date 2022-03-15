package org.izherebkin.integration.task.impl;

import org.izherebkin.integration.config.ApplicationProperties;
import org.izherebkin.integration.dto.MessageDto;
import org.izherebkin.integration.mapper.JsonMapper;
import org.izherebkin.integration.util.MessageGenerator;
import org.izherebkin.integration.zeromq.ZeroMqPublisher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.zeromq.Utils;
import org.zeromq.ZCert;

import java.io.IOException;

public class PublicationTaskTest {

    private static final String APP_PROPERTIES_FIELD_NAME = "appProperties";

    private static final String ZERO_MQ_PUBLISHER_FIELD_NAME = "zeroMqPublisher";

    private static PublicationTask publicationTask;

    @BeforeAll
    static void beforeAll() throws IOException {
        publicationTask = new PublicationTask();
        ZCert zCert = new ZCert();
        int port = Utils.findOpenPort();
        ApplicationProperties appProperties = Mockito.mock(ApplicationProperties.class);
        Mockito.when(appProperties.getZeromqPublisherServerKey()).thenReturn(zCert.getPublicKeyAsZ85());
        Mockito.when(appProperties.getZeromqPublisherConnectAddress()).thenReturn("tcp://localhost:" + port);
        Mockito.when(appProperties.getPublicationPeriodSeconds()).thenReturn(1L);
        ReflectionTestUtils.setField(publicationTask, APP_PROPERTIES_FIELD_NAME, appProperties);
    }

    @Test
    void publicationTaskTest() throws Exception {
        publicationTask.afterPropertiesSet();
        ZeroMqPublisher zeroMqPublisher = (ZeroMqPublisher) ReflectionTestUtils.getField(publicationTask, ZERO_MQ_PUBLISHER_FIELD_NAME);
        MatcherAssert.assertThat(zeroMqPublisher, CoreMatchers.notNullValue());
        MatcherAssert.assertThat(publicationTask.getTrigger(), CoreMatchers.notNullValue());

        ZeroMqPublisher zeroMqPublisherSpy = Mockito.spy(zeroMqPublisher);
        ReflectionTestUtils.setField(publicationTask, ZERO_MQ_PUBLISHER_FIELD_NAME, zeroMqPublisherSpy);
        MockedStatic<MessageGenerator> messageGenerator = null;
        MockedStatic<JsonMapper> jsonMapper = null;
        try {
            messageGenerator = Mockito.mockStatic(MessageGenerator.class);
            MessageDto messageDto = new MessageDto();
            messageGenerator.when(MessageGenerator::generate).thenReturn(messageDto);
            jsonMapper = Mockito.mockStatic(JsonMapper.class);
            jsonMapper.when(() -> JsonMapper.toJson(Mockito.same(messageDto))).thenReturn(messageDto.toString());
            publicationTask.run();
        } finally {
            if (messageGenerator != null) {
                messageGenerator.close();
            }
            if (jsonMapper != null) {
                jsonMapper.close();
            }
        }
        Mockito.verify(zeroMqPublisherSpy, Mockito.times(1)).publish(Mockito.anyString());

        publicationTask.destroy();
        Mockito.verify(zeroMqPublisherSpy, Mockito.times(1)).destroy();
    }

}
