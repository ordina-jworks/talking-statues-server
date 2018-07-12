package be.ordina.talkingstatues.security;

import be.ordina.talkingstatues.appusers.AppUser;
import be.ordina.talkingstatues.appusers.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/denied")
    public ResponseEntity accessDenied() {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

}
