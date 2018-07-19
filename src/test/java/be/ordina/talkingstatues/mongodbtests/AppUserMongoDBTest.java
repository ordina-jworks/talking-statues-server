package be.ordina.talkingstatues.mongodbtests;

import be.ordina.talkingstatues.appusers.AppUser;
import be.ordina.talkingstatues.appusers.AppUserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static be.ordina.talkingstatues.appusers.AppUserTestUtils.*;
import static org.junit.Assert.assertEquals;

@DataMongoTest
@RunWith(SpringRunner.class)
public class AppUserMongoDBTest {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setup() {
        mongoTemplate.dropCollection(AppUser.class);
    }

    @After
    public void tearDown() {
        mongoTemplate.dropCollection(AppUser.class);
    }

    @Test
    public void saveAppUser_andFindById() {
        AppUser savedAppUser = appUserRepository.save(new AppUser(HANDLE, NAME, LAST_NAME));

        assertEquals(savedAppUser.getId(), appUserRepository.findById(savedAppUser.getId()).get().getId());
    }

    @Test
    public void findByHandle() {
        AppUser savedAppUser = appUserRepository.save(new AppUser(HANDLE, NAME, LAST_NAME));

        assertEquals(savedAppUser.getId(), appUserRepository.findByHandle(HANDLE).get().getId());
    }

    @Test
    public void deleteAppUser() {
        AppUser savedAppUser = appUserRepository.save(new AppUser(HANDLE, NAME, LAST_NAME));

        assertEquals(1, appUserRepository.findAll().size());

        appUserRepository.delete(savedAppUser);

        assertEquals(0, appUserRepository.findAll().size());
    }
}
