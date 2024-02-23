// SPDX-License-Identifier: Apache-2.0
package io.github.stavshamir.springwolf.schemas.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Yaml;

public class DefaultExampleYamlValueSerializer implements ExampleYamlValueSerializer {

    private static final ObjectMapper yamlMapper = Yaml.mapper();

    @Override
    public String writeDocumentAsYamlString(JsonNode node) throws JsonProcessingException {
        return yamlMapper.writeValueAsString(node);
    }
}
