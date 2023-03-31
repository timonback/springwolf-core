package io.github.stavshamir.springwolf.schemas;

import com.asyncapi.v2.model.schema.Schema;
import com.asyncapi.v2.model.schema.Type;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;


public class SchemaMapper {

    // TODO Idea: Model converter is in some way stateful so that recreating instances of swagger schema becomes an issue for
    // nested structures. We can try to mitigate those issues by caching mapped swagger schemas and return them when needed.
    private final Map<String, io.swagger.v3.oas.models.media.Schema> swaggerSchemas = emptyMap();

    public static <T> Schema fromSwaggerSchema(io.swagger.v3.oas.models.media.Schema<T> swaggerSchema) {
        Schema schema = new Schema();

        schema.setTitle(swaggerSchema.getName());
        schema.setType(mapSchemaType(swaggerSchema.getType()));

        if (swaggerSchema.getExample() != null) {
            schema.setExamples(List.of(swaggerSchema.getExample()));
        }

        if (swaggerSchema.getProperties() != null) {
            schema.setProperties(fromSwaggerSchema(swaggerSchema.getProperties()));
        }

        // TODO: more attributes

        return schema;
    }

    public static Map<String, Schema> fromSwaggerSchema(Map<String, io.swagger.v3.oas.models.media.Schema> swaggerSchemas) {
        return swaggerSchemas
                .entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, e -> SchemaMapper.fromSwaggerSchema(e.getValue())));
    }

    public static <T> io.swagger.v3.oas.models.media.Schema<T> toSwaggerSchema(Schema asyncApiSchema) {
        io.swagger.v3.oas.models.media.Schema<T> schema = new io.swagger.v3.oas.models.media.Schema<T>();

        schema.setName(asyncApiSchema.getTitle());
        schema.setType(mapSchemaType(asyncApiSchema.type));

        schema.setExample(Objects.requireNonNullElse(asyncApiSchema.getExamples(), emptyList()).stream().findFirst().orElse(null));

        if (asyncApiSchema.getProperties() != null) {
            schema.setProperties(toSwaggerSchema(asyncApiSchema.getProperties()));
        }
        

        return schema;
    }

    public static Map<String, io.swagger.v3.oas.models.media.Schema> toSwaggerSchema(Map<String, Schema> asyncApiSchemas) {
        return asyncApiSchemas
            .entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, e -> SchemaMapper.toSwaggerSchema(e.getValue())));
    }


    private static Type mapSchemaType(String swaggerSchemaType) {
        return Type.valueOf(StringUtils.upperCase(swaggerSchemaType));
    }

    private static String mapSchemaType(Type asyncApiSchemaType) {
        return StringUtils.lowerCase(asyncApiSchemaType.toString());
    }

}
