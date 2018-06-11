package be.ordina.talkingstatues.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigurationController {

    @GetMapping(value = "/google", produces = {"application/vnd.ordina.v1.0+json"})
    public String getGoogleApiKey(){
        return "AIzaSyB8XRZJKwlGjlWu15KRI7j-6VFT_LL9TDE";
    }

}
