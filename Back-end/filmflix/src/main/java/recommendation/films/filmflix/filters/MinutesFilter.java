package recommendation.films.filmflix.filters;

import org.springframework.beans.factory.annotation.Autowired;
import recommendation.films.filmflix.dao.MovieDao;
import recommendation.films.filmflix.models.Movie;

public class MinutesFilter implements Filter{
    private int minMinute;
    private int maxMinute;
    public MinutesFilter(int minMinute, int maxMinute){
        this.minMinute = minMinute;
        this.maxMinute = maxMinute;
    }
    @Override
    public boolean satisfies(Object object) {
        Movie movie = (Movie) object;
        int minutes = movie.getMinutes();
        if (minutes >= minMinute && minutes <= maxMinute) {
            return  true;
        }
        return false;
    }
}