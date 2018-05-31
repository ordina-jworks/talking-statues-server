package be.ordina.talkingstatues.Security;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    @GetMapping("user-info")
    String getUserInfo(OAuth2AuthenticationToken authentication){
        return authentication.getName()+" : "+authentication.getPrincipal().getAttributes().values().toString();
    }
}
