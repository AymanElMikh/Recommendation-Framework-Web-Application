package recommendation.films.filmflix.filters;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import recommendation.films.filmflix.models.Movie;

public class YearAfterFilter implements  Filter{
    private int myYear;
    @JsonCreator
    public YearAfterFilter(@JsonProperty("year") int year) {
        this.myYear = year;
    }

    @Override
    public boolean satisfies(Object object) {
        boolean response = false;
        Movie movie = (Movie)object;
        int year = movie.getYear();
        if (year >= this.myYear )
            return true;
        return false;
    }
}