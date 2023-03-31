package io.github.stavshamir.springwolf.schemas;

import com.asyncapi.v2.model.schema.Schema;
import com.asyncapi.v2.model.schema.Type;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SchemaMapperTest {

    @Test
    void testTypeMapping() {

        StringSchema swaggerSchema =  new StringSchema();

        Schema schema = SchemaMapper.fromSwaggerSchema(swaggerSchema);

        assertThat(schema.getType()).isEqualTo(Type.STRING);
    }

    @Test
    void testMapSchema() {
        // given
        MapSchema swaggerSchema = new MapSchema();

        // when
        Schema schema = SchemaMapper.fromSwaggerSchema(swaggerSchema);

        // then
        assertThat(schema.getType()).isEqualTo(Type.OBJECT);
    }


}
