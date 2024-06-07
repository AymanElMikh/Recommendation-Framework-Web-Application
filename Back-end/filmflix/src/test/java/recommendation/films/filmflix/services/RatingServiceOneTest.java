package recommendation.films.filmflix.services;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import recommendation.films.filmflix.config.SimilarityConfig;
import recommendation.films.filmflix.dao.MovieDao;
import recommendation.films.filmflix.dao.RaterDao;
import recommendation.films.filmflix.filters.TrueFilter;
import recommendation.films.filmflix.models.Rater;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RatingServiceOneTest {
    private static final Logger logger = LogManager.getLogger(RatingServiceOneTest.class);
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
    public void dotProduct_ValidRatings_CalculatesCorrectly() {
        Rater me = mock(Rater.class);
        Rater r = mock(Rater.class);

        when(me.getRating("movie1")).thenReturn(3.0);
        when(me.getRating("movie2")).thenReturn(4.0);
        when(r.getRating("movie1")).thenReturn(3.0);
        when(r.getRating("movie2")).thenReturn(4.0);

        List<String> movies = new ArrayList<>();
        movies.add("movie1");
        movies.add("movie2");

        when(movieDao.filterBy(any(TrueFilter.class))).thenReturn(movies);

        double result = ratingService.dotProduct(me, r);

        logger.info("Movies returned by filterBy: {}", movies);
        logger.info("Result of the dotProduct test: {}", result);

        verify(movieDao).filterBy(any(TrueFilter.class));

        assertThat(5.0).isEqualTo(result);

        logger.info("Test dotProduct_ValidRatings_CalculatesCorrectly succeeded!");
    }

}
