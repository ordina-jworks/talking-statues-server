package be.ordina.talkingstatues.security;

import be.ordina.talkingstatues.monuments.Monument;
import be.ordina.talkingstatues.monuments.MonumentRepository;
import be.ordina.talkingstatues.visits.Visit;
import be.ordina.talkingstatues.visits.VisitRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDbSeeder {

    private final AppUserRepository appUserRepository;
    private final MonumentRepository monumentRepository;
    private final VisitRepository visitRepository;


    List<Monument> monuments;


    AppUser newUser1 = new AppUser("newUser1 ", "Jimmy", "Fallon");
    AppUser newUser2 = new AppUser("newUser2", "Jos", "Vermeiren");
    AppUser newUser3 = new AppUser("newUser3", "Eric", "slkfje");
    AppUser newUser4 = new AppUser("newUser4", "Peter", "lizdlij");
    AppUser newUser5 = new AppUser("newUser5", "Jay", "cslemlk");
    AppUser newUser6 = new AppUser("newUser6", "Patrick", "selkdjdeizj");
    AppUser newUser7 = new AppUser("newUser7", "Willem", "ozdiejdiej");
    AppUser newUser8 = new AppUser("newUser8", "Anton", "cdpoziepd");
    AppUser newUser9 = new AppUser("newUser9", "Adriaan", "lzqdjlijqd");
    AppUser newUser10 = new AppUser("newUser10", "Joachim", "poekfpejoiehd");

    public UserDbSeeder(AppUserRepository appUserRepository, MonumentRepository monumentRepository, VisitRepository visitRepository) {
        this.appUserRepository = appUserRepository;
        this.monumentRepository = monumentRepository;
        this.visitRepository = visitRepository;


        monuments = this.monumentRepository.findAll();

        seedUserToDb(newUser1);
        seedUserToDb(newUser2);
        seedUserToDb(newUser3);
        seedUserToDb(newUser4);
        seedUserToDb(newUser5);
        seedUserToDb(newUser6);
        seedUserToDb(newUser7);
        seedUserToDb(newUser8);
        seedUserToDb(newUser9);
        seedUserToDb(newUser10);

        /*addVisitsToUsers();*/

    }

    public AppUser seedUserToDb(AppUser newUser) {
        Optional<AppUser> foundUser = appUserRepository.findByHandle(newUser.getHandle());

        if (foundUser.isPresent()) {
            System.out.println("User exists already");
            return null;
        } else {
            return appUserRepository.save(newUser);
        }
    }

    public void addVisitsToUsers() {
        List<AppUser> foundUsers = appUserRepository.findAll();
        int count = 0;
        for (AppUser usr : foundUsers) {
            if(count == 9){
                count = 0;
            }
            Visit newVisit = new Visit(usr.getId(), monuments.get(count).getId());
            newVisit = this.visitRepository.save(newVisit);

            usr.addVisit(newVisit);

            appUserRepository.save(usr);
            count++;
        }
    }
}
