package be.ordina.talkingstatues.Visits;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisitRepository extends MongoRepository<Visit,String> {
}
