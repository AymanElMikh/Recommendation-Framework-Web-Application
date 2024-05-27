package recommendation.films.filmflix.filters;
import recommendation.films.filmflix.models.Movie;

public class YearAfterFilter implements  Filter{
    private int myYear;
    public YearAfterFilter(int year) {
        myYear = year;
    }
    @Override
    public boolean satisfies(Object object) {
        boolean response = false;
        Movie movie = (Movie)object;
        int year = movie.getYear();
        if (year >= this.myYear )
            return true;
        return false;
    }
}