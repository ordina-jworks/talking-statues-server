package be.ordina.talkingstatues.mongodbtests;

import be.ordina.talkingstatues.visits.Visit;
import be.ordina.talkingstatues.visits.VisitRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static be.ordina.talkingstatues.appusers.AppUserTestUtils.APP_USER_ID;
import static be.ordina.talkingstatues.monuments.MonumentTestUtils.MON_ID;
import static org.junit.Assert.assertEquals;

@DataMongoTest
@RunWith(SpringRunner.class)
public class VisitMongoDBTest {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setup() {
        mongoTemplate.dropCollection(Visit.class);
    }

    @After
    public void tearDown() {
        mongoTemplate.dropCollection(Visit.class);
    }

    @Test
    public void save_andFindById() {
        Visit savedVisit = visitRepository.save(new Visit(APP_USER_ID, MON_ID));

        assertEquals(savedVisit.getId(), visitRepository.findAll().get(0).getId());
    }

}
