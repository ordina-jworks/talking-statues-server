package be.ordina.talkingstatues.Monument;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MonumentRepository extends MongoRepository<Monument,String> {

    List<Monument> findAllByArea(String area);
}
