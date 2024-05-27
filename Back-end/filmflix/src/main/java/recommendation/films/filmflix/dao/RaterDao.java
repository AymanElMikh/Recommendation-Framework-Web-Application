package recommendation.films.filmflix.dao;
import recommendation.films.filmflix.models.Rater;
import java.util.ArrayList;

public interface RaterDao {
    public ArrayList<Rater> getRaters();
    public Rater getRater(String id);
    public   void addRaterRating(String raterID, String movieID, double rating);
    public boolean containId(String id);
}