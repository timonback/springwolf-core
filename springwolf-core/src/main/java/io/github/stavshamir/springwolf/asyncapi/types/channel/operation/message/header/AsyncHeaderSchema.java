package io.github.stavshamir.springwolf.asyncapi.types.channel.operation.message.header;

import com.asyncapi.v2.model.schema.Schema;
import com.asyncapi.v2.model.schema.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import java.util.List;

public class AsyncHeaderSchema extends Schema {
    @JsonIgnore
    private final String headerName;
    
    public AsyncHeaderSchema(String headerName){
        super();
        this.setType(Type.STRING);

        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }

    @Builder(builderMethodName = "headerBuilder")
    private static AsyncHeaderSchema createHeader(String headerName, String description, String example, List<String> enumValue) {
        AsyncHeaderSchema header = new AsyncHeaderSchema(headerName);
        header.setDescription(description);
        header.setExamples(List.of(example));
        header.setEnumValues(List.of(enumValue));
        return header;
    }
}
