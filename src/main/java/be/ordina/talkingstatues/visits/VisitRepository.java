package be.ordina.talkingstatues.visits;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisitRepository extends MongoRepository<Visit, String> {
}
