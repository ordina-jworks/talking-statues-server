package be.ordina.talkingstatues;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan
@EnableMongoRepositories
public class TestConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "FongoDB";
    }

    @Override
    public MongoClient mongoClient() {
        return new Fongo(getDatabaseName()).getMongo();
    }
}
