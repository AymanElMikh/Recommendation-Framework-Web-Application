package recommendation.films.filmflix.services;

import recommendation.films.filmflix.dto.RateDTO;
import recommendation.films.filmflix.dto.RatedMovieDTO;
import recommendation.films.filmflix.filters.Filter;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface RatingService {
    public List<RatedMovieDTO> getSimilarRatingsByFilter(String id, Filter filterCriteria);
    public void processRatings(RateDTO rateDTO, HttpSession session);

}