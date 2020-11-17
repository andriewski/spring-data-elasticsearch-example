package by.mark.elasticsearchexample.dao.model;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Author {

    @Field(type = Text)
    private String name;
}