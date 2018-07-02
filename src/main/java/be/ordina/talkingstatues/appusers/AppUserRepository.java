package be.ordina.talkingstatues.appusers;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

interface AppUserRepository extends MongoRepository<AppUser,String>{
    Optional<AppUser> findByHandle(String handle);
}
