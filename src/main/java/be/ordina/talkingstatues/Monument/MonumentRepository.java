package be.ordina.talkingstatues.Monument;

import be.ordina.talkingstatues.Monument.Model.Monument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MonumentRepository extends MongoRepository<Monument,String> {
}
