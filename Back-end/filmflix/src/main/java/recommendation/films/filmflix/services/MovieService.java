package recommendation.films.filmflix.services;
import recommendation.films.filmflix.dto.FilterDTO;
import recommendation.films.filmflix.dto.MovieDTO;

import javax.servlet.http.HttpSession;
import java.util.List;


public interface MovieService {
    List<MovieDTO> getMovies(FilterDTO filterDTO, HttpSession session);
}
