// SPDX-License-Identifier: Apache-2.0
package io.github.springwolf.examples.kafka.standalone;

import io.github.springwolf.asyncapi.v3.jackson.AsyncApiSerializerService;
import io.github.springwolf.asyncapi.v3.jackson.DefaultAsyncApiSerializerService;
import io.github.springwolf.asyncapi.v3.model.AsyncAPI;
import io.github.springwolf.core.asyncapi.AsyncApiService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class StandaloneTest {

    @Test
    public void scanApplication() throws IOException {
        // given
        AsyncApiService asyncApiService = new StandaloneLoader().create("io.github.springwolf.examples.kafka");

        // when
        AsyncAPI asyncApi = asyncApiService.getAsyncAPI();

        // then
        assertThat(asyncApi).isNotNull();

        AsyncApiSerializerService serializerService = new DefaultAsyncApiSerializerService();
        String jsonString = serializerService.toJsonString(asyncApi);
        System.out.println(jsonString);
        Files.writeString(Path.of("src", "test", "resources", "asyncapi.standalone.json"), jsonString);
    }
}
