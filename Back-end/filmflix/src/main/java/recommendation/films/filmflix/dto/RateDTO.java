package recommendation.films.filmflix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import recommendation.films.filmflix.models.Rating;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RateDTO {
    private String raterId;
    private ArrayList<Rating> ratings;
}
