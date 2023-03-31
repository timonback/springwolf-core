package io.github.stavshamir.springwolf.schemas;

import com.asyncapi.v2.model.schema.Schema;
import io.github.stavshamir.springwolf.asyncapi.types.channel.operation.message.header.AsyncHeaders;

import java.util.Map;

public interface SchemasService {

    Map<String, Schema> getDefinitions();

    String register(AsyncHeaders headers);
    String register(Class<?> type);

}
