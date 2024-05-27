package recommendation.films.filmflix.dao;
import recommendation.films.filmflix.models.Rater;
import java.util.ArrayList;

public class RaterDaoRedis implements RaterDao{
    @Override
    public ArrayList<Rater> getRaters() {
        return null;
    }
    @Override
    public Rater getRater(String id) {
        return null;
    }
    @Override
    public void addRaterRating(String raterID, String movieID, double rating) {
    }
    @Override
    public boolean containId(String id) {
        return false;
    }
}