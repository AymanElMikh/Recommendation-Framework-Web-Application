package recommendation.films.filmflix.dao;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import recommendation.films.filmflix.config.CSVDataConfig;
import recommendation.films.filmflix.models.Rater;
import recommendation.films.filmflix.utils.duke.FileResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RaterDaoCSV implements RaterDao {
    private Map<String, Rater> raters;
    private final String ratersCSVURL;

    @Autowired
    public RaterDaoCSV(CSVDataConfig csvDataConfig) {
        this.ratersCSVURL = csvDataConfig.getStatisticalRatersCSVURLFile();
        this.raters = new HashMap<>();
        initializeData();
    }

    private void initialize() {
        if (raters == null) {
            raters = new HashMap<>();
        }
    }

    private void initializeData() {
        if (raters == null) {
            addRatings(ratersCSVURL);
        }
    }

    private void addRatings(String filename) {
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

    @Override
    public void addRaterRating(String raterID, String movieID, double rating) {
        initialize();
        Rater rater = raters.computeIfAbsent(raterID, Rater::new);
        rater.addRating(movieID, rating);
    }
    @Override
    public boolean containId(String id) {
        return raters.containsKey(id);
    }

    @Override
    public Rater getRater(String id) {
        initialize();
        return raters.get(id);
    }
    @Override
    public ArrayList<Rater> getRaters() {
        initialize();
        return new ArrayList<>(raters.values());
    }
}