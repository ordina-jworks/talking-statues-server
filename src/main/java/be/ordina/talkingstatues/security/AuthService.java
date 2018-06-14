package be.ordina.talkingstatues.security;

import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final AppUserRepository appUserRepository;

    public AuthService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    AppUser registerUser(AppUser appUser){
        return appUserRepository.save(appUser);
    }

    AppUser getUserByHandle(String handle){
        return appUserRepository.findByHandle(handle)
                .orElseThrow(()->new RuntimeException("user not found"));
    }
}
