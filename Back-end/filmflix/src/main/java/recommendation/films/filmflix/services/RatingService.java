package recommendation.films.filmflix.services;

import jakarta.servlet.http.HttpSession;
import recommendation.films.filmflix.dto.RateDTO;
import recommendation.films.filmflix.dto.RatedMovieDTO;
import recommendation.films.filmflix.filters.Filter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface RatingService {
    public List<RatedMovieDTO> getSimilarRatingsByFilter(String id, Filter filterCriteria);
    public void processRatings(RateDTO rateDTO, HttpSession session);

}