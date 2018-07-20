package be.ordina.talkingstatues.mongodbtests;

import be.ordina.talkingstatues.routes.Route;
import be.ordina.talkingstatues.routes.RouteRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static be.ordina.talkingstatues.routes.RouteTestConstants.ROUTE_NAME;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

@DataMongoTest
@RunWith(SpringRunner.class)
public class RouteMongoDBTest {

    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setup() {
        mongoTemplate.dropCollection(Route.class);
    }

    @After
    public void tearDown() {
        mongoTemplate.dropCollection(Route.class);
    }

    @Test
    public void save_andFindById() {
        Route savedRoute = routeRepository.save(new Route(ROUTE_NAME, emptyList()));

        assertEquals(savedRoute.getId(), routeRepository.findById(savedRoute.getId()).get().getId());
    }

    @Test
    public void delete() {
        Route savedRoute = routeRepository.save(new Route(ROUTE_NAME, emptyList()));

        assertEquals(1, routeRepository.findAll().size());

        routeRepository.deleteById(savedRoute.getId());

        assertEquals(0, routeRepository.findAll().size());
    }
}
