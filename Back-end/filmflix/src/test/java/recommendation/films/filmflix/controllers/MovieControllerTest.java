package recommendation.films.filmflix.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import recommendation.films.filmflix.dto.FilterDTO;
import recommendation.films.filmflix.dto.MovieDTO;
import recommendation.films.filmflix.filters.Filter;
import recommendation.films.filmflix.filters.MinutesFilter;
import recommendation.films.filmflix.filters.TrueFilter;
import recommendation.films.filmflix.services.MovieService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getFilteredMoviesShouldReturnMovies() throws Exception {
        FilterDTO filterDTO = new FilterDTO();

        MovieDTO movie1 = new MovieDTO();
        movie1.setId("1");
        movie1.setDirector("Director_1_For_Test");
        movie1.setCountry("Country_1_For_Test");
        movie1.setPoster("Poster_1_For_Test");
        movie1.setMinutes(120);
        movie1.setGenres("Genre_1_For_Test");

        MovieDTO movie2 = new MovieDTO();
        movie2.setId("2");
        movie2.setDirector("Director_2_For_Test");
        movie2.setCountry("Country_2_For_Test");
        movie2.setPoster("Poster_2_For_Test");
        movie2.setMinutes(120);
        movie2.setGenres("Genre_2_For_Test");

        List<MovieDTO> movies = Arrays.asList(movie1, movie2);

        when(movieService.getMovies(any(FilterDTO.class), any(HttpSession.class))).thenReturn(movies);

        String filterJson = objectMapper.writeValueAsString(filterDTO);

        mockMvc.perform(post("/moviesAfterFilter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filterJson))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(movies)));
    }


    @Test
    void getFilteredMoviesShouldReturnErrorOnInvalidJson() throws Exception {
        String invalidJson = "{invalidJson}";

        when(movieService.getMovies(any(FilterDTO.class), any(HttpSession.class))).thenThrow(new RuntimeException("Service exception"));

        mockMvc.perform(post("/moviesAfterFilter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isExpectationFailed())
                .andExpect(content().string("Something went wrong with the JSON file."));
    }

}