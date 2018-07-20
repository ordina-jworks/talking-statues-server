package be.ordina.talkingstatues.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfigurationUtils {

    public static final String INITIAL_DATA_MONUMENTS_KEY = "be.ordina.talkingstatues.initialdata.monuments";
    public static final String INITIAL_DATA_VISITS_KEY = "be.ordina.talkingstatues.initialdata.visits";
    public static final String MOBILE_APP_URL_KEY = "be.ordina.talkingstatues.mobile.url";

    @Autowired
    private Environment env;

    public String getProperty(String key) {
        return this.env.getProperty(key);
    }

}
