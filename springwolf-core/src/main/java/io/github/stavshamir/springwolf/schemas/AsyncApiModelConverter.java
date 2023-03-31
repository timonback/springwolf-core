package io.github.stavshamir.springwolf.schemas;

import com.asyncapi.v2.model.schema.Schema;

import java.lang.reflect.Type;
import java.util.Map;

public interface AsyncApiModelConverter {

    Map<String, Schema> readAll(Type type);

    Map<String, Schema> read(Type type);

}
