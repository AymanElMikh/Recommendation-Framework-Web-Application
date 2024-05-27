package recommendation.films.filmflix.dto;
import recommendation.films.filmflix.models.Movie;

public class MovieDTOConverter {
    public static MovieDTO toDTO(Movie movie) {
        if (movie == null) {
            return null;
        }
        return new MovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getYear(),
                movie.getGenres(),
                movie.getDirector(),
                movie.getCountry(),
                movie.getPoster(),
                movie.getMinutes()
        );
    }

    public static Movie fromDTO(MovieDTO movieDTO) {
        if (movieDTO == null) {
            return null;
        }
        return new Movie(
                movieDTO.getId(),
                movieDTO.getTitle(),
                String.valueOf(movieDTO.getYear()),
                movieDTO.getGenres(),
                movieDTO.getDirector(),
                movieDTO.getCountry(),
                movieDTO.getPoster(),
                movieDTO.getMinutes()
        );
    }

    public static RatedMovieDTO toRatedDTO(Movie movie, double rating) {
        return new RatedMovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getYear(),
                movie.getGenres(),
                movie.getDirector(),
                movie.getCountry(),
                movie.getPoster(),
                movie.getMinutes(),
                rating
        );
    }
}