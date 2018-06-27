package be.ordina.talkingstatues.appusers;

import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class AuthService {

    private final AppUserRepository appUserRepository;

    public AuthService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser registerUser(AppUser appUser){
        return appUserRepository.save(appUser);
    }

    AppUser getUserByHandle(String handle){
        return appUserRepository.findByHandle(handle)
                .orElseThrow(()->new RuntimeException("user not found"));
    }

    List<AppUser> getAllUsersFromDb(){
        return appUserRepository.findAll();
    }

    void deleteUserFromDb(String id){
        appUserRepository.deleteById(id);
    }

    AppUser getUserById(String id){
        return appUserRepository.findById(id).orElseThrow(()->new RuntimeException("user not present"));
    }
}