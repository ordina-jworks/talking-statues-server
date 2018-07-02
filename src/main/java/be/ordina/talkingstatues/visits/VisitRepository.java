package be.ordina.talkingstatues.visits;

import org.springframework.data.mongodb.repository.MongoRepository;

interface VisitRepository extends MongoRepository<Visit,String> {
}
