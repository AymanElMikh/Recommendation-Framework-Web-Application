package recommendation.films.filmflix.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recommendation.films.filmflix.dao.MovieDao;
import recommendation.films.filmflix.dao.RaterDao;
import recommendation.films.filmflix.dto.FilterDTO;
import recommendation.films.filmflix.dto.MovieDTO;
import recommendation.films.filmflix.dto.MovieDTOConverter;
import recommendation.films.filmflix.filters.AllFilters;
import recommendation.films.filmflix.filters.Filter;
import recommendation.films.filmflix.filters.TrueFilter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovieServiceOne implements MovieService {
    private final MovieDao movieDao;
    private final RaterDao raterDao;

    @Autowired
    public MovieServiceOne(MovieDao movieDao, RaterDao raterDao) {
        this.movieDao = movieDao;
        this.raterDao = raterDao;
    }

    @Override
    public List<MovieDTO> getMovies(FilterDTO filterDTO, HttpSession session) {
        String raterId = generateUniqueRaterId();
        session.setAttribute("raterId", raterId);
        session.setAttribute("filterDTO", filterDTO);

        AllFilters allFilters = new AllFilters();
        List<Filter> filters = filterDTO.getFilters();

        if (filters == null || filters.isEmpty()) {
            allFilters.addFilter(new TrueFilter());
        } else {
            filters.forEach(allFilters::addFilter);
        }

        List<String> movieIds = movieDao.filterBy(allFilters);
        return movieIds.stream()
                .map(movieDao::getMovie)
                .map(MovieDTOConverter::toDTO)
                .collect(Collectors.toList());
    }

    private String generateUniqueRaterId() {
        String raterId;
        do {
            raterId = UUID.randomUUID().toString();
        } while (raterDao.containId(raterId));
        return raterId;
    }
}
