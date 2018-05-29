package be.ordina.talkingstatues.Monument;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MonumentRepository extends MongoRepository<Monument,String> {
}
