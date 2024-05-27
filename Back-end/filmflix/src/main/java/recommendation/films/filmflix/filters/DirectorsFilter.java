package recommendation.films.filmflix.filters;


import org.springframework.beans.factory.annotation.Autowired;
import recommendation.films.filmflix.dao.MovieDao;
import recommendation.films.filmflix.models.Movie;

public class DirectorsFilter implements Filter{
    private String[] directors ;
    public DirectorsFilter(String directors){
        this.directors = directors.split(",");
    }

    @Override
    public boolean satisfies(Object object) {
        Movie movie = (Movie) object;
        for(String director : directors ){
            if(movie.getDirector().indexOf(director) != -1)
                return true;
        }
        return false;
    }
}