package be.ordina.talkingstatues.routes;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RouteRepository extends MongoRepository<Route, String> {
}
