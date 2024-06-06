package recommendation.films.filmflix.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import recommendation.films.filmflix.filters.*;
import java.util.List;

@Getter
@Setter
public class FilterDTO {
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            property = "type"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = GenreFilter.class, name = "genre"),
            @JsonSubTypes.Type(value = DirectorsFilter.class, name = "directors"),
            @JsonSubTypes.Type(value = MinutesFilter.class, name = "minutes"),
            @JsonSubTypes.Type(value = YearAfterFilter.class, name = "yearAfter")
    })
    private List<Filter> filters;
}