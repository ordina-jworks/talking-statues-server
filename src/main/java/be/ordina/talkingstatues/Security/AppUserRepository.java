package be.ordina.talkingstatues.Security;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUser,String>{
    Optional<AppUser> findByHandle(String handle);
}
