package recommendation.films.filmflix.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recommendation.films.filmflix.config.SimilarityConfig;
import recommendation.films.filmflix.dao.MovieDao;
import recommendation.films.filmflix.dao.RaterDao;
import recommendation.films.filmflix.dto.MovieDTOConverter;
import recommendation.films.filmflix.dto.RateDTO;
import recommendation.films.filmflix.dto.RatedMovieDTO;
import recommendation.films.filmflix.filters.Filter;
import recommendation.films.filmflix.filters.TrueFilter;
import recommendation.films.filmflix.models.Movie;
import recommendation.films.filmflix.models.Rater;
import recommendation.films.filmflix.models.Rating;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class RatingServiceOne implements RatingService {

    private final RaterDao raterDao;
    private final MovieDao movieDao;
    private final int minimalRaters;
    private final int numSimilarRaters;

    @Autowired
    public RatingServiceOne(RaterDao raterDao, MovieDao movieDao, SimilarityConfig similarityConfig) {
        this.raterDao = raterDao;
        this.movieDao = movieDao;
        this.minimalRaters = similarityConfig.getMinimalRaters();
        this.numSimilarRaters = similarityConfig.getNumSimilarRaters();
    }

    private double dotProduct(Rater me, Rater r) {
        List<String> movies = movieDao.filterBy(new TrueFilter());
        double product = 0.0;

        for (String item : movies) {
            double ratingMe = me.getRating(item);
            double ratingR = r.getRating(item);

            if (ratingMe != -1 && ratingR != -1) {
                double adjustedMe = ratingMe - 5.0;
                double adjustedR = ratingR - 5.0;
                product += adjustedMe * adjustedR;
            }
        }

        return product;
    }

    private List<Rating> getSimilarities(String id) {
        List<Rating> resultRatings = new ArrayList<>();
        Rater mainRater = raterDao.getRater(id);

        for (Rater currentRater : raterDao.getRaters()) {
            if (!currentRater.getID().equals(id)) {
                double similarity = dotProduct(mainRater, currentRater);

                if (similarity >= 0) {
                    resultRatings.add(new Rating(currentRater.getID(), similarity));
                }
            }
        }

        resultRatings.sort(Collections.reverseOrder());
        return resultRatings;
    }

    @Override
    public List<RatedMovieDTO> getSimilarRatingsByFilter(String id, Filter filterCriteria) {
        List<Rating> resultRatings = new ArrayList<>();
        List<Rating> similarities = getSimilarities(id);

        for (String movieID : movieDao.filterBy(filterCriteria)) {
            int numRaters = 0;
            double totalWeightedRating = 0.0;

            for (int k = 0; k < Math.min(numSimilarRaters, similarities.size()); k++) {
                Rating similarity = similarities.get(k);
                Rater similarRater = raterDao.getRater(similarity.getItem());
                double movieRating = similarRater.getRating(movieID);

                if (movieRating != -1) {
                    numRaters++;
                    totalWeightedRating += movieRating * similarity.getValue();
                }
            }

            if (numRaters >= minimalRaters) {
                resultRatings.add(new Rating(movieID, totalWeightedRating / numRaters));
            }
        }

        resultRatings.sort(Collections.reverseOrder());
        return constructRatedMovies(resultRatings);
    }

    @Override
    public void processRatings(RateDTO rateDTO, HttpSession session) {
        String raterId = (String) session.getAttribute("raterId");

        if (raterId == null) {
            throw new IllegalStateException("Rater ID not found in session.");
        }

        for (Rating rating : rateDTO.getRatings()) {
            raterDao.addRaterRating(raterId, rating.getItem(), rating.getValue());
        }
    }

    private List<RatedMovieDTO> constructRatedMovies(List<Rating> ratedMovies) {
        return ratedMovies.stream()
                .map(rating -> {
                    Movie movie = movieDao.getMovie(rating.getItem());
                    return MovieDTOConverter.toRatedDTO(movie, rating.getValue());
                })
                .collect(Collectors.toList());
    }
}
