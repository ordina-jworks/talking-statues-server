package be.ordina.talkingstatues.mongodbtests;

import be.ordina.talkingstatues.nlp.Language;
import be.ordina.talkingstatues.monuments.Monument;
import be.ordina.talkingstatues.monuments.MonumentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static be.ordina.talkingstatues.monuments.MonumentTestUtils.buildRandomMonuments;
import static be.ordina.talkingstatues.monuments.MonumentTestUtils.getMonumentsInDifferentAreas;
import static org.junit.Assert.assertEquals;

@DataMongoTest
@RunWith(SpringRunner.class)
public class MonumentMongoDBTest {

    @Autowired
    private MonumentRepository monumentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setup() {
        mongoTemplate.dropCollection(Monument.class);
    }

    @After
    public void tearDown() {
        mongoTemplate.dropCollection(Monument.class);
    }

    @Test
    public void saveMonument_andFindById() {
        Monument monument = buildRandomMonuments("area", Language.NL, 1).get(0);
        Monument savedMonument = monumentRepository.save(monument);

        assertEquals(monumentRepository.findById(savedMonument.getId()).get().getId(), savedMonument.getId());
    }

    @Test
    public void findAllByArea() {
        getMonumentsInDifferentAreas()
                .forEach(monument -> monumentRepository.save(monument));

        assertEquals(2, monumentRepository.findAllByArea("hier").size());
        assertEquals(1, monumentRepository.findAllByArea("daar").size());
    }

    @Test
    public void deleteMonument() {
        Monument monument = buildRandomMonuments("area", Language.NL, 1).get(0);
        Monument savedMonument = monumentRepository.save(monument);

        assertEquals(1, monumentRepository.findAll().size());

        monumentRepository.delete(savedMonument);

        assertEquals(0, monumentRepository.findAll().size());
    }
}
