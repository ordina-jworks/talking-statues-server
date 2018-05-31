package be.nick.banker_backend.Security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AuthService implements SecurityConstants {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    AppUser registerUser(AppUser appUser){
        appUser.setId(UUID.randomUUID());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    AppUser getUserByUsername(String username){
        return appUserRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("username not found"));
    }
}
