package be.ordina.talkingstatues.config;

import org.springframework.data.mongodb.repository.MongoRepository;

interface ConfigurationRepository extends MongoRepository<Configuration,String> {

}
