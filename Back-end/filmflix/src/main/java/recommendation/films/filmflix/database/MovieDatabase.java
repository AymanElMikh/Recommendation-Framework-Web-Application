package recommendation.films.filmflix.database;

import java.util.*;
import org.apache.commons.csv.*;
import org.springframework.beans.factory.annotation.Autowired;
import recommendation.films.filmflix.config.CSVDataConfig;
import recommendation.films.filmflix.models.Movie;
import recommendation.films.filmflix.utils.duke.FileResource;

public class MovieDatabase {
    private static MovieDatabase instance;
    private static CSVDataConfig csvDataConfig;
    private static HashMap<String, Movie> ourMovies;

    @Autowired
    private MovieDatabase(CSVDataConfig csvDataConfigParam){
        csvDataConfig = csvDataConfigParam;
    }

    public static MovieDatabase getInstance(CSVDataConfig csvDataConfigParam) {
        if (instance == null) {
            synchronized (MovieDatabase.class) {
                if (instance == null) {
                    instance = new MovieDatabase(csvDataConfigParam);
                    initialize();
                }
            }
        }
        return instance;
    }

    private static void initialize() {
        if (ourMovies == null) {
            ourMovies = new HashMap<>();
            loadMoviesWithRetry(csvDataConfig.getStatisticalMoviesCSVURLFile(), 3);
        }
    }

    private static void loadMoviesWithRetry(String filename, int retries) {
        int attempts = 0;
        boolean success = false;

        while (attempts < retries && !success) {
            try {
                loadMovies(filename);
                success = true;
            } catch (Exception e) {
                attempts++;
                if (attempts >= retries) {
                    throw new RuntimeException("Failed to load movies after " + retries + " attempts", e);
                }
            }
        }
    }

    private static void loadMovies(String filename) throws Exception {
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
            ourMovies.put(movie.getId(), movie);
        }
    }

    public Map<String, Movie> getMapMovies() {
        return ourMovies;
    }
}
