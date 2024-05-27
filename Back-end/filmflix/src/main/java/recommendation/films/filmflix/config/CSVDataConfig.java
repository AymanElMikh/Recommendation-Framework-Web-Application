package recommendation.films.filmflix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class CSVDataConfig {
    private final Environment environment;
    @Autowired
    public CSVDataConfig(Environment environment){
        this.environment = environment;
    }
    public String getStatisticalMoviesCSVURLFile(){
        return environment.getProperty("my.statisticalMoviesCSVFile.url");
    }
    public String getStatisticalRatersCSVURLFile(){
        return environment.getProperty("my.statisticalRatersCSVFile.url");
    }
}
