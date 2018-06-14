package be.ordina.talkingstatues.config;

import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private final ConfigurationRepository repository;

    public ConfigurationService(ConfigurationRepository repository) {
        this.repository = repository;
    }

    Configuration getConfigurationById(String id){
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Configuration with id: "+id+" does not exist"));
    }

}
