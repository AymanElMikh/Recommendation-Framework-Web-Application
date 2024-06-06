package recommendation.films.filmflix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import recommendation.films.filmflix.config.SimilarityConfig;
import recommendation.films.filmflix.dao.MovieDao;
import recommendation.films.filmflix.dao.RaterDao;
import recommendation.films.filmflix.filters.TrueFilter;
import recommendation.films.filmflix.models.Rater;
import recommendation.films.filmflix.services.RatingServiceOne;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RatingServiceOneTest {
    @Mock
    RaterDao raterDao;

    @Mock
    MovieDao movieDao;

    @Mock
    HttpSession session;

    @Mock
    SimilarityConfig similarityConfig;

    RatingServiceOne ratingService;

    @BeforeEach
    void setUp() {
        ratingService = new RatingServiceOne(raterDao, movieDao, similarityConfig);
    }

    @Test
    void dotProduct_ValidRatings_CalculatesCorrectly() {
        // Setup
        Rater me = new Rater("me");
        me.addRating("movie1", 4.0);
        me.addRating("movie2", 5.0);

        Rater r = new Rater("r");
        r.addRating("movie1", 3.0);
        r.addRating("movie2", 4.0);

        List<String> movies = new ArrayList<>();
        movies.add("movie1");
        movies.add("movie2");

        when(movieDao.filterBy(new TrueFilter())).thenReturn(movies);

        double result = ratingService.dotProduct(me, r);

        assertEquals(5.0, result);
    }

}
