package recommendation.films.filmflix.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import recommendation.films.filmflix.dto.RateDTO;
import recommendation.films.filmflix.services.RatingService;
import javax.servlet.http.HttpSession;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RateController.class)
class RateControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RatingService ratingService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void processRatingsShouldReturnCreatedOnSuccess() throws Exception {
        RateDTO rateDTO = new RateDTO();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("raterId", "JUST_TO_GET_RIDE_OF_SESSION_NULL_SCENARIO");

        doNothing().when(ratingService).processRatings(any(RateDTO.class), any(HttpSession.class));

        mockMvc.perform(post("/raterResults")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateDTO))
                        .session(session))
                .andExpect(status().isCreated())
                .andExpect(content().string("Your ratings operations were done as expected!"));
    }

    @Test
    void processRatingsShouldReturnBadRequestOnIllegalStateException() throws Exception {
        RateDTO rateDTO = new RateDTO();
        MockHttpSession session = new MockHttpSession();
        doThrow(new IllegalStateException("Invalid state")).when(ratingService).processRatings(any(RateDTO.class), any(HttpSession.class));
        mockMvc.perform(post("/raterResults")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateDTO))
                        .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid state"));
    }

    @Test
    void getRatedMoviesShouldReturnNotFoundWhenNoRaterId() throws Exception {
        MockHttpSession session = new MockHttpSession();
        mockMvc.perform(get("/moviesAfterRates")
                        .session(session))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No id information is stored about any user"));
    }

}