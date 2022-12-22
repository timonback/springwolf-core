package io.github.stavshamir.springwolf.example.consumers;

import io.github.stavshamir.springwolf.asyncapi.scanners.channels.operationdata.annotation.AsyncOperation;
import io.github.stavshamir.springwolf.asyncapi.scanners.channels.operationdata.annotation.AsyncSubscriber;
import io.github.stavshamir.springwolf.asyncapi.scanners.channels.operationdata.annotation.KafkaAsyncOperationBinding;
import io.github.stavshamir.springwolf.example.dtos.AnotherPayloadDto;
import io.github.stavshamir.springwolf.example.dtos.ExamplePayloadDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.money.MonetaryAmount;

import static org.springframework.kafka.support.mapping.AbstractJavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME;


@Service
@Slf4j
@KafkaListener(topics = "multi-payload-topic", containerFactory = "exampleKafkaListenerContainerFactory")
public class ExampleClassLevelKafkaListener {

    @KafkaHandler
    public void receiveExamplePayload(ExamplePayloadDto payload) {
        log.info("Received new message in multi-payload-topic: {}", payload.toString());
    }

    @KafkaHandler
    public void receiveAnotherPayload(AnotherPayloadDto payload) {
        log.info("Received new message in multi-payload-topic: {}", payload.toString());
    }

    @KafkaHandler
    @AsyncSubscriber(operation = @AsyncOperation(
            channelName = "multi-payload-topic",
            description = "Override description in the AsyncSubscriber annotation with servers at ${kafka.bootstrap.servers}",
            headers = @AsyncOperation.Headers(
                    schemaName = "SpringKafkaDefaultHeaders-MonetaryAmount",
                    values = {
                            @AsyncOperation.Headers.Header(
                                    name = DEFAULT_CLASSID_FIELD_NAME,
                                    description = "Spring Type Id Header",
                                    value = "javax.money.MonetaryAmount"
                            ),
                    }
            )
    ))
    @KafkaAsyncOperationBinding(
            bindingVersion = "1",
            clientId = "foo-clientId",
            groupId = "#{'foo-groupId'}"
    )
    public void receiveMonetaryAmount(MonetaryAmount payload) {
        log.info("Received new message in multi-payload-topic: {}", payload.toString());
    }

}
