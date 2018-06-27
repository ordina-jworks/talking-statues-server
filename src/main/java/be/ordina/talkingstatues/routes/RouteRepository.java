package be.ordina.talkingstatues.routes;

import org.springframework.data.mongodb.repository.MongoRepository;

interface RouteRepository extends MongoRepository<Route,String> {
}
