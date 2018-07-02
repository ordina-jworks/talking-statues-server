package be.ordina.talkingstatues.security;

import be.ordina.talkingstatues.appusers.AppUser;
import be.ordina.talkingstatues.appusers.AuthService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    private final AuthService authService;

    public SecurityController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/user-info")
    String getUserInfo(OAuth2AuthenticationToken authentication){

        // Handle
        if(authentication!=null){
            return authentication.getName();
        }
        return "";
    }

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @PostMapping("/user/create")
    public void userCreation(OAuth2AuthenticationToken auth){
        if(auth.isAuthenticated()){
            authService.registerUser(new AppUser("", "", ""));
        }
    }



}
