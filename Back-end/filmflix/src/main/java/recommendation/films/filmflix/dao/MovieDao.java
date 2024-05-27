package recommendation.films.filmflix.dao;

import recommendation.films.filmflix.models.Movie;
import recommendation.films.filmflix.filters.Filter;
import java.util.List;

public interface MovieDao {
    public Movie getMovie(String id);
    public List<String> filterBy(Filter f);
    public int size();
}