package recommendation.films.filmflix.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recommendation.films.filmflix.dto.FilterDTO;
import recommendation.films.filmflix.dto.MovieDTO;
import recommendation.films.filmflix.services.MovieService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class MovieController {
    private final ObjectMapper objectMapper;
    private MovieService movieService;
    @Autowired
    public MovieController(ObjectMapper objectMapper, MovieService movieService) {
        this.objectMapper = objectMapper;
        this.movieService = movieService;
    }
    @PostMapping("/moviesAfterFilter")
    public ResponseEntity<Object> getFilteredMovies(@RequestBody String filtersJson, HttpSession session) {
        try {
            FilterDTO filterDTO = objectMapper.readValue(filtersJson, FilterDTO.class);
            List<MovieDTO> movies = movieService.getMovies(filterDTO, session);
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Something went wrong with the JSON file.");
        }
    }
}