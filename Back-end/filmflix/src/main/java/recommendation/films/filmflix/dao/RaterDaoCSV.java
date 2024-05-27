package recommendation.films.filmflix.dao;

import recommendation.films.filmflix.models.Rater;
import recommendation.films.filmflix.database.RaterDatabase;
import recommendation.films.filmflix.config.CSVDataConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class RaterDaoCSV implements RaterDao {
    private final RaterDatabase raterDatabase;
    @Autowired
    public RaterDaoCSV(CSVDataConfig csvDataConfig) {
        this.raterDatabase = RaterDatabase.getInstance(csvDataConfig);
    }
    @Override
    public ArrayList<Rater> getRaters() {
        return new ArrayList<>(raterDatabase.getRaters().values());
    }
    @Override
    public Rater getRater(String id) {
        return raterDatabase.getRater(id);
    }
    @Override
    public void addRaterRating(String raterID, String movieID, double rating) {
        raterDatabase.addRaterRating(raterID, movieID, rating);
    }
    @Override
    public boolean containId(String id) {
        return raterDatabase.getRaters().containsKey(id);
    }
}