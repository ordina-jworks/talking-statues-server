package be.ordina.talkingstatues.security;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/auth")
public class SecurityController {
    private final AuthService authService;


    public SecurityController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("user-info")
    String getUserInfo(OAuth2AuthenticationToken authentication){

        // Handle
        if(authentication!=null){
            return authentication.getName();
        }
        return "";
    }

    @GetMapping("/users")
    List<AppUser> getAllUsers(){
        return authService.getAllUsersFromDb();
    }

    @GetMapping("/users/find/{id}")
    AppUser findUserById(@PathVariable String id){
        return authService.getUserById(id);
    }

    @PostMapping("/users/add")
    AppUser addUser(@RequestBody AppUser newUser){
        if(newUser != null){
            return authService.registerUser(newUser);
        }
        else {
            return null;
        }
    }

    @DeleteMapping("/users/delete/{id}")
    void deleteFoundUser(@PathVariable String id){
        authService.deleteUserFromDb(id);
    }

    @PutMapping("/users/forget/{id}")
    void forgetFoundUser(@PathVariable String id){
        AppUser foundUser = authService.getUserById(id);

        foundUser.setHandle("");
        foundUser.setName("");
        foundUser.setLastName("");

        authService.registerUser(foundUser);
    }
}