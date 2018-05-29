package be.ordina.talkingstatues.Images;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image,String> {

    List<Image> findAllByMonumentId(String monumentId);

}
