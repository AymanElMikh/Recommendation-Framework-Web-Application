package recommendation.films.filmflix.filters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import recommendation.films.filmflix.dao.MovieDao;
import recommendation.films.filmflix.models.Movie;

public class MinutesFilter implements Filter{
    private int minMinute;
    private int maxMinute;
    @JsonCreator
    public MinutesFilter(@JsonProperty("minMinute") int minMinute, @JsonProperty("maxMinute") int maxMinute) {
        this.minMinute = minMinute;
        this.maxMinute = maxMinute;
    }
    @Override
    public boolean satisfies(Object object) {
        Movie movie = (Movie) object;
        int minutes = movie.getMinutes();
        if (minutes >= minMinute && minutes <= maxMinute) {
            return  true;
        }
        return false;
    }
}