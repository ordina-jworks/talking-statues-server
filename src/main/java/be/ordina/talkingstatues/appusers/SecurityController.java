package be.ordina.talkingstatues.appusers;

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

    @GetMapping("/appusers")
    List<AppUser> getAllUsers(){
        return authService.getAllUsersFromDb();
    }

    @GetMapping("/appusers/find/{id}")
    AppUser findUserById(@PathVariable String id){
        return authService.getUserById(id);
    }

    @PostMapping("/appusers/add")
    AppUser addUser(@RequestBody AppUser newUser){
        if(newUser != null){
            return authService.registerUser(newUser);
        }
        else {
            return null;
        }
    }

    @DeleteMapping("/appusers/delete/{id}")
    void deleteFoundUser(@PathVariable String id){
        authService.deleteUserFromDb(id);
    }

    @PutMapping("/appusers/forget/{id}")
    void forgetFoundUser(@PathVariable String id){
        AppUser foundUser = authService.getUserById(id);

        foundUser.setHandle("");
        foundUser.setName("");
        foundUser.setLastName("");

        authService.registerUser(foundUser);
    }
}