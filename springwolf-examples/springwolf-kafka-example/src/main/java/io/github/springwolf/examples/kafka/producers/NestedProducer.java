// SPDX-License-Identifier: Apache-2.0
package io.github.springwolf.examples.kafka.producers;

import io.github.springwolf.bindings.kafka.annotations.KafkaAsyncOperationBinding;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import io.github.springwolf.core.asyncapi.annotations.AsyncPublisher;
import io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants;
import io.github.springwolf.examples.kafka.configuration.KafkaConfiguration;
import io.github.springwolf.examples.kafka.dtos.NestedPayloadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import static org.springframework.kafka.support.mapping.AbstractJavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME;

public class NestedProducer {

    @Autowired
    private KafkaTemplate<String, NestedPayloadDto> kafkaTemplate;

    @AsyncPublisher(
            operation =
                    @AsyncOperation(
                            channelName = "topic-defined-via-asyncPublisher-annotation",
                            description = "Custom, optional description defined in the AsyncPublisher annotation",
                            servers = {"kafka-server"},
                            headers =
                                    @AsyncOperation.Headers(
                                            schemaName = "SpringDefaultHeaderAndCloudEvent",
                                            description = "Spring __TypeId__ and CloudEvent Headers",
                                            values = {
                                                @AsyncOperation.Headers.Header(
                                                        name = DEFAULT_CLASSID_FIELD_NAME,
                                                        description = "Spring Type Id Header",
                                                        value =
                                                                "io.github.springwolf.examples.kafka.dtos.NestedPayloadDto"),
                                                @AsyncOperation.Headers.Header(
                                                        name = AsyncHeadersCloudEventConstants.TYPE,
                                                        description =
                                                                "CloudEvent type. Other fields are set through SpringwolfCloudEventCustomizer",
                                                        value = "NestedPayloadDto.v1"),
                                            })))
    @KafkaAsyncOperationBinding(
            clientId = "foo-clientId",
            messageBinding =
                    @KafkaAsyncOperationBinding.KafkaAsyncMessageBinding(
                            key =
                                    @KafkaAsyncOperationBinding.KafkaAsyncKey(
                                            description = "Kafka Producer Message Key",
                                            example = "example-key")))
    public void sendMessage(NestedPayloadDto msg) {
        kafkaTemplate.send(KafkaConfiguration.PRODUCER_TOPIC, msg);
    }
}
