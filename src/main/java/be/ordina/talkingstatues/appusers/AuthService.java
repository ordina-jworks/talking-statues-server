package be.ordina.talkingstatues.appusers;

import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class AuthService {

    private final AppUserRepository appUserRepository;

    public AuthService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public void initializeUserData(AppUser[] initialData ){
        appUserRepository.deleteAll();

        for(AppUser usr: initialData){
            appUserRepository.save(usr);
            // System.out.println(usr.toString() + " has been saved.\n");
        }
    }

    public AppUser registerUser(AppUser appUser){
        return appUserRepository.save(appUser);
    }

    public AppUser getUserByHandle(String handle){
        return appUserRepository.findByHandle(handle)
                .orElseThrow(()->new RuntimeException("user not found"));
    }

    public List<AppUser> getAllUsersFromDb(){
        return appUserRepository.findAll();
    }

    public void deleteUserFromDb(String id){
        appUserRepository.deleteById(id);
    }

    public AppUser getUserById(String id){
        return appUserRepository.findById(id).orElseThrow(()->new RuntimeException("user not present"));
    }

}