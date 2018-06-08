package be.ordina.talkingstatues.Images;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image,String> {

    Optional<Image> findByMonumentId(String monumentId);

}
