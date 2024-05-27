package recommendation.films.filmflix.dao;

import java.util.*;
import org.apache.commons.csv.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import recommendation.films.filmflix.config.CSVDataConfig;
import recommendation.films.filmflix.filters.Filter;
import recommendation.films.filmflix.models.Movie;
import recommendation.films.filmflix.utils.duke.FileResource;

@Repository
public class MovieDaoCSV implements MovieDao {
    private final Map<String, Movie> movies;
    private final String moviesCsvPath;

    @Autowired
    public MovieDaoCSV(CSVDataConfig csvDataConfig) {
        this.moviesCsvPath = csvDataConfig.getStatisticalMoviesCSVURLFile();
        this.movies = new HashMap<>();
        loadMovies(moviesCsvPath);
    }

    private void loadMovies(String filename) {
        FileResource fr = new FileResource(filename);
        CSVParser csvp = fr.getCSVParser();

        for (CSVRecord rec : csvp) {
            String id = rec.get("id");
            String title = rec.get("title");
            String year = rec.get("year");
            String country = rec.get("country");
            String genres = rec.get("genre");
            String director = rec.get("director");
            int minutes = Integer.parseInt(rec.get("minutes"));
            String poster = rec.get("poster");
            Movie movie = new Movie(id, title, year, genres, director, country, poster, minutes);
            movies.put(movie.getId(), movie);
        }
    }

    @Override
    public Movie getMovie(String id) {
        return movies.get(id);
    }

    @Override
    public int size() {
        return movies.size();
    }

    @Override
    public List<String> filterBy(Filter filter) {
        List<String> filteredMovies = new ArrayList<>();
        for (Movie movie : movies.values()) {
            if (filter.satisfies(movie)) {
                filteredMovies.add(movie.getId());
            }
        }
        return filteredMovies;
    }
}
