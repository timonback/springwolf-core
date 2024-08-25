// SPDX-License-Identifier: Apache-2.0
package io.github.springwolf.examples.kafka.configuration;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import io.github.springwolf.asyncapi.v3.model.AsyncAPI;
import io.github.springwolf.asyncapi.v3.model.schema.SchemaObject;
import io.github.springwolf.core.asyncapi.AsyncApiCustomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.CONTENT_TYPE;
import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.CONTENT_TYPE_DESC;
import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.ID;
import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.ID_DESC;
import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.SOURCE;
import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.SOURCE_DESC;
import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.SPECVERSION;
import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.SPECVERSION_DESC;
import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.SUBJECT;
import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.SUBJECT_DESC;
import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.TIME;
import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.TIME_DESC;
import static io.github.springwolf.core.asyncapi.scanners.common.headers.AsyncHeadersCloudEventConstants.TYPE;

/**
 * Usage: When using AsyncPublisher/AsyncSubscriber, the developer only adds the "ce_type" header
 * and the SpringwolfCloudEventOperationCustomizer will add the missing CloudEvent headers.
 */
@Component
@RequiredArgsConstructor
public class SpringwolfCloudEventCustomizer implements AsyncApiCustomizer {
    @Value("${spring.application.name}")
    private String source;

    @Override
    public void customize(AsyncAPI asyncAPI) {
        asyncAPI.getComponents().getSchemas().forEach((key, schema) -> {
            if (schema.getProperties() != null && schema.getProperties().containsKey(TYPE)) {
                addMissingCloudEventHeaders(schema);
            }
        });
    }

    private void addMissingCloudEventHeaders(SchemaObject schema) {
        addMissingHeader(schema, ID, ID_DESC, "2c60089e-6f39-459d-8ced-2d6df7e4c03a");
        addMissingHeader(schema, SOURCE, SOURCE_DESC, "http://localhost");
        addMissingHeader(schema, SPECVERSION, SPECVERSION_DESC, "1.0");
        addMissingHeader(schema, SUBJECT, SUBJECT_DESC, source);
        addMissingHeader(schema, TIME, TIME_DESC, "2023-10-28 20:01:23+00:00");
        addMissingHeader(schema, CONTENT_TYPE, CONTENT_TYPE_DESC, MediaType.APPLICATION_JSON_VALUE);
    }

    private void addMissingHeader(SchemaObject schema, String headerName, String description, String example) {
        schema.getProperties().putIfAbsent(headerName, createHeaderProperty(description, example));
        ObjectNode exampleNode = (ObjectNode) schema.getExamples().get(0);

        exampleNode.putIfAbsent(headerName, new TextNode(example));
    }

    private SchemaObject createHeaderProperty(String description, String example) {
        return SchemaObject.builder()
                .type("string")
                .description(description)
                .examples(List.of(example))
                .enumValues(List.of(example))
                .build();
    }
}
