package be.ordina.talkingstatues.security;

import be.ordina.talkingstatues.appusers.AppUser;
import be.ordina.talkingstatues.appusers.AuthService;
import com.mongodb.util.JSON;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    private final AuthService authService;

    public SecurityController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/denied")
    public ResponseEntity accessDenied() {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/user")
    public Principal user(Principal user, OAuth2AuthenticationToken auth) {

        return user;
    }

    @PostMapping("/user/create")
    public void userCreation(OAuth2AuthenticationToken auth){
        String username[];

        if(auth.isAuthenticated()){
            username = auth.getPrincipal().getAttributes().get("name").toString().split(" ");
            authService.registerUser(new AppUser(auth.getPrincipal().getName(), username[0], username[1]));
        }
    }



}
