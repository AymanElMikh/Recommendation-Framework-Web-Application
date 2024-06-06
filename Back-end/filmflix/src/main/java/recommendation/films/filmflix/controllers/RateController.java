package recommendation.films.filmflix.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recommendation.films.filmflix.dto.FilterDTO;
import recommendation.films.filmflix.dto.FilterDTOConverter;
import recommendation.films.filmflix.dto.RateDTO;
import recommendation.films.filmflix.filters.AllFilters;
import recommendation.films.filmflix.services.RatingService;

import javax.servlet.http.HttpSession;

@RestController
public class RateController {
    @Autowired
    RatingService ratingService;
    @PostMapping("/raterResults")
    public ResponseEntity<String> processRatings(@RequestBody RateDTO rateDTO, HttpSession session) {
        try {
            ratingService.processRatings(rateDTO, session);
            return ResponseEntity.status(HttpStatus.CREATED).body("Your ratings operations were done as expected!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process ratings");
        }
    }
    @GetMapping("/moviesAfterRates")
    public ResponseEntity<Object> getRatedMovies(HttpSession session) {
        String id = (String) session.getAttribute("raterId");
        if (id != null && !id.isEmpty()) {
            AllFilters filterCriteria = FilterDTOConverter.toAllFilters((FilterDTO) session.getAttribute("filterDTO"));
            return ResponseEntity.ok(ratingService.getSimilarRatingsByFilter(id, filterCriteria));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No id information is stored about any user");
    }

}