package recommendation.films.filmflix.dao;

import recommendation.films.filmflix.models.Movie;
import recommendation.films.filmflix.filters.Filter;
import recommendation.films.filmflix.database.MovieDatabase;
import recommendation.films.filmflix.config.CSVDataConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class MovieDaoCSV implements MovieDao {
    private MovieDatabase movieDatabase;

    @Autowired
    public MovieDaoCSV(CSVDataConfig csvDataConfig) {
        this.movieDatabase = MovieDatabase.getInstance(csvDataConfig);
    }
    @Override
    public Movie getMovie(String id) {
        Map<String, Movie> movies = movieDatabase.getMapMovies();
        return movies.get(id);
    }

    @Override
    public List<String> filterBy(Filter filter) {
        List<String> filteredMovies = new ArrayList<>();
        Map<String, Movie> movies = movieDatabase.getMapMovies();

        for (Movie movie : movies.values()) {
            if (filter.satisfies(movie)) {
                filteredMovies.add(movie.getId());
            }
        }
        return filteredMovies;
    }
    @Override
    public int size() {
        Map<String, Movie> movies = movieDatabase.getMapMovies();
        return movies.size();
    }
}
