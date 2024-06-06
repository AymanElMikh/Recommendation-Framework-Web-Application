package recommendation.films.filmflix.database;

import org.apache.commons.csv.*;
import org.springframework.beans.factory.annotation.Autowired;
import recommendation.films.filmflix.config.CSVDataConfig;
import recommendation.films.filmflix.models.Rater;
import recommendation.films.filmflix.utils.duke.FileResource;

import java.util.HashMap;
import java.util.Map;
public class RaterDatabase {
    private static RaterDatabase instance;
    private static HashMap<String, Rater> ourRaters;
    private static CSVDataConfig csvDataConfig;

    @Autowired
    private RaterDatabase(CSVDataConfig csvDataConfigParam) {
        csvDataConfig = csvDataConfigParam;
    }

    public static RaterDatabase getInstance(CSVDataConfig csvDataConfigParam) {
        if (instance == null) {
            synchronized (RaterDatabase.class) {
                if (instance == null) {
                    instance = new RaterDatabase(csvDataConfigParam);
                    initialize();
                }
            }
        }
        return instance;
    }

    private static void initialize() {
        if (ourRaters == null) {
            ourRaters = new HashMap<>();
            addRatings(csvDataConfig.getStatisticalRatersCSVURLFile());
        }
    }

    public static void addRatings(String filename) {
        initialize();
        FileResource fr = new FileResource(filename);
        CSVParser csvp = fr.getCSVParser();
        for (CSVRecord rec : csvp) {
            String id = rec.get("rater_id");
            String item = rec.get("movie_id");
            String rating = rec.get("rating");
            addRaterRating(id, item, Double.parseDouble(rating));
        }
    }

    public static void addRaterRating(String raterID, String movieID, double rating) {
        initialize();
        Rater rater = ourRaters.get(raterID);
        if (rater == null) {
            rater = new Rater(raterID);
            ourRaters.put(raterID, rater);
        }
        rater.addRating(movieID, rating);
    }

    public static Rater getRater(String id) {
        initialize();
        return ourRaters.get(id);
    }

    public Map<String, Rater> getRaters() {
        initialize();
        return ourRaters;
    }
}
