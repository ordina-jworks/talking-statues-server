package be.ordina.talkingstatues.monuments;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface MonumentRepository extends MongoRepository<Monument,String> {

    List<Monument> findAllByArea(String area);
}
