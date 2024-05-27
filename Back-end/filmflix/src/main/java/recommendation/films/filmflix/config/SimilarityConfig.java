package recommendation.films.filmflix.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SimilarityConfig {
    private final Environment environment;
    @Autowired
    public SimilarityConfig(Environment environment){
        this.environment = environment;
    }
    public int getMinimalRaters(){
        return Integer.parseInt(environment.getProperty("my.similarityProps.minimalRater"));
    }
    public  int getNumSimilarRaters(){
        return Integer.parseInt(environment.getProperty("my.similarityProps.numSimilarRaters"));
    }
}