package recommendation.films.filmflix.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MovieDTO {
    private String id;
    private String title;
    private int year;
    private String genres;
    private String director;
    private String country;
    private String poster;
    private int minutes;

    public MovieDTO() {}

    public MovieDTO(String id, String title, int year, String genres, String director, String country, String poster, int minutes) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genres = genres;
        this.director = director;
        this.country = country;
        this.poster = poster;
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "MovieDTO [id=" + id + ", title=" + title + ", year=" + year + ", genres=" + genres +
                ", director=" + director + ", country=" + country + ", poster=" + poster + ", minutes=" + minutes + "]";
    }
}
