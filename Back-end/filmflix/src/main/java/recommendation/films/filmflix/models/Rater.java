package recommendation.films.filmflix.models;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Rater {
    String id;
    Map<String, Rating> ratingMap;
    private String myID;
    private Map<String, Rating> myRatings;
    public Rater(String id) {
        myID = id;
        myRatings = new HashMap<>();
    }
    public void addRating(String item, double rating) {
        myRatings.put(item ,new Rating(item, rating));
    }
    public boolean hasRating(String item) {
        if( myRatings.keySet().contains(item))
            return true;
        return false;
    }
    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        if (hasRating(item))
            return myRatings.get(item).getValue();
        return -1;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rater rater = (Rater) o;
        return Objects.equals(myID, rater.myID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myID);
    }
}