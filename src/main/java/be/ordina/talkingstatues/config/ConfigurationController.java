package be.ordina.talkingstatues.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigurationController {

    private final ConfigurationService configService;

    public ConfigurationController(ConfigurationService configService) {
        this.configService = configService;
    }

    @GetMapping(value = "/google", produces = {"application/vnd.ordina.v1.0+json"})
    public Configuration getGoogleApiKey(){
        return configService.getConfigurationById(Configuration.GOOGLE_API_KEY);
    }

}
