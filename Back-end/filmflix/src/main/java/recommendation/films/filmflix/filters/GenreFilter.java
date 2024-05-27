package recommendation.films.filmflix.filters;
import org.springframework.beans.factory.annotation.Autowired;
import recommendation.films.filmflix.dao.MovieDao;
import recommendation.films.filmflix.models.Movie;

public class GenreFilter implements Filter{
    private String genre;
    public GenreFilter(String genre){
        this.genre = genre;
    }
    @Override
    public boolean satisfies(Object object) {
        Movie movie = (Movie) object;
        String genre = movie.getGenres();
        if(genre.indexOf(this.genre) != -1)
            return true;
        return false;
    }
}