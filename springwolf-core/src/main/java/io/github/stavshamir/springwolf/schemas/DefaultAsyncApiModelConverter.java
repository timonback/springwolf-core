package io.github.stavshamir.springwolf.schemas;

import com.asyncapi.v2.model.schema.Schema;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAsyncApiModelConverter implements AsyncApiModelConverter {

    private final ModelConverters converter = ModelConverters.getInstance();

    public DefaultAsyncApiModelConverter(Optional<List<ModelConverter>> externalModelConverters) {
        externalModelConverters.ifPresent(converters -> converters.forEach(converter::addConverter));
    }

    @Override
    public Map<String, Schema> readAll(Type type) {
        return converter.readAll(type)
                .entrySet()
                .stream()
                .collect(toMap(Entry::getKey, e -> SchemaMapper.fromSwaggerSchema(e.getValue())));
    }

    @Override
    public Map<String, Schema> read(Type type) {
        return converter.read(type)
                .entrySet()
                .stream()
                .collect(toMap(Entry::getKey, e -> SchemaMapper.fromSwaggerSchema(e.getValue())));
    }
}
