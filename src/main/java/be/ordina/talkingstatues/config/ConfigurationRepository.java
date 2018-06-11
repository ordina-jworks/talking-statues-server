package be.ordina.talkingstatues.config;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigurationRepository extends MongoRepository<Configuration,String> {

}
