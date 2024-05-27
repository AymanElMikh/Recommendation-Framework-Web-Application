package recommendation.films.filmflix.dao;

import recommendation.films.filmflix.filters.Filter;
import recommendation.films.filmflix.models.Movie;
import java.util.List;

public class MovieDaoRedis implements  MovieDao{
    @Override
    public Movie getMovie(String id) {
        return null;
    }
    @Override
    public List<String> filterBy(Filter f) {
        return null;
    }
    @Override
    public int size() {
        return 0;
    }
}
