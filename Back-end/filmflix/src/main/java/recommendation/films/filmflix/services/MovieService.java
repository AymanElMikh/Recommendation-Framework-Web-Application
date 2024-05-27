package recommendation.films.filmflix.services;
import jakarta.servlet.http.HttpSession;
import recommendation.films.filmflix.dto.FilterDTO;
import recommendation.films.filmflix.dto.MovieDTO;

import java.util.List;


public interface MovieService {
    List<MovieDTO> getMovies(FilterDTO filterDTO, HttpSession session);
}
