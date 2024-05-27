package recommendation.films.filmflix.dto;
import recommendation.films.filmflix.filters.AllFilters;
import recommendation.films.filmflix.filters.TrueFilter;

public class FilterDTOConverter {
    public static AllFilters toAllFilters(FilterDTO filterDTO) {
        AllFilters allFilters = new AllFilters();
        if (filterDTO.getFilters() == null || filterDTO.getFilters().isEmpty()) {
            allFilters.addFilter(new TrueFilter());
        } else {
            filterDTO.getFilters().forEach(allFilters::addFilter);
        }
        return allFilters;
    }
}
