package recommendation.films.filmflix.filters;

import java.util.ArrayList;

public class AllFilters implements Filter{
    ArrayList<Filter> filters;
    public AllFilters() {
        filters = new ArrayList<Filter>();
    }
    public void addFilter(Filter f) {
        filters.add(f);
    }
    @Override
    public boolean satisfies(Object object) {
        for(Filter f : filters) {
            if (! f.satisfies(object)) {
                return false;
            }
        }
        return true;
    }
}