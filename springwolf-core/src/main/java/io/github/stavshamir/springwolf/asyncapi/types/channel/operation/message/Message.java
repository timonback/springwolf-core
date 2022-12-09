package io.github.stavshamir.springwolf.asyncapi.types.channel.operation.message;

import io.github.stavshamir.springwolf.asyncapi.types.channel.operation.message.header.HeaderReference;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

/**
 * Describes a message received on a given channel and operation.
 *
 * @see <a href="https://www.asyncapi.com/docs/specifications/2.0.0/#messageObject">Message specification</a>
 */
@Data
@Builder
@EqualsAndHashCode(of = {"name"})
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Comparable<Message> {

    /**
     * A machine-friendly name for the message.
     */
    private String name;

    /**
     * A human-friendly title for the message.
     */
    private String title;

    /**
     * A human-friendly description for the message.
     */
    private String description;

    private PayloadReference payload;

    private HeaderReference headers;


    @Override
    public int compareTo(Message o) {
        if (o == null) {
            return 1;
        }
        return StringUtils.compare(this.name, o.name);
    }
}
