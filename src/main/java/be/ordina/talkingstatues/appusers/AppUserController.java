package be.ordina.talkingstatues.appusers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/appusers")
public class AppUserController {
    private final AuthService authService;

    public AppUserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("")
    List<AppUser> getAllUsers() {
        return authService.getAllUsersFromDb();
    }

    @GetMapping("/find/{id}")
    AppUser findUserById(@PathVariable String id) {
        return authService.getUserById(id);
    }

    @PutMapping("/add")
    AppUser addUser(@RequestBody AppUser newUser) {
        if (newUser != null) {
            return authService.registerUser(newUser);
        } else {
            return null;
        }
    }

    @PutMapping("/create")
    public void userCreation(OAuth2AuthenticationToken auth) {
        String username[];

        if (auth.isAuthenticated()) {
            username = auth.getPrincipal().getAttributes().get("name").toString().split(" ", 2);
            authService.registerUser(new AppUser(auth.getPrincipal().getName(), username[0], username[1]));
        }
    }

    @DeleteMapping("/delete/{id}")
    void deleteFoundUser(@PathVariable String id) {
        authService.deleteUserFromDb(id);
    }

    @PutMapping("/forget/{id}")
    void forgetFoundUser(@PathVariable String id) {
        AppUser foundUser = authService.getUserById(id);

        foundUser.setHandle("");
        foundUser.setName("");
        foundUser.setLastName("");

        authService.registerUser(foundUser);
    }
}