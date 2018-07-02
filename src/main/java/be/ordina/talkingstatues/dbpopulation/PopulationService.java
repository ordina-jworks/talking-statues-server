package be.ordina.talkingstatues.dbpopulation;

import be.ordina.talkingstatues.appusers.AppUser;
import be.ordina.talkingstatues.appusers.AuthService;
import be.ordina.talkingstatues.monuments.Monument;
import be.ordina.talkingstatues.monuments.MonumentService;
import be.ordina.talkingstatues.visits.Visit;
import be.ordina.talkingstatues.visits.VisitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PopulationService {

    private final MonumentService monumentService;
    private final AuthService authService;
    private final VisitService visitService;

    @Value("be.ordina.talkingstatues.initialdata.monuments")
    private boolean initialDataMonuments;

    @Value("be.ordina.talkingstatues.initialdata.appusers")
    private boolean initialDataAppUsers;

    @Value("be.ordina.talkingstatues.initialdata.addvisits")
    private boolean addVisits;

    private final List<Monument> allMonuments;

    public PopulationService(MonumentService monumentService, AuthService authService, VisitService visitService) {
        this.monumentService = monumentService;
        this.authService = authService;
        this.allMonuments = monumentService.getAllMonuments();
        this.visitService = visitService;

        if (initialDataMonuments) {
            monumentService.initializeData();
        }

        if(initialDataAppUsers){
            authService.initializeUserData();
        }

        if(addVisits){
            addVisitsToUsers();
        }


    }


    public void addVisitsToUsers() {
        List<AppUser> foundUsers = authService.getAllUsersFromDb();
        int count = 0;
        for (AppUser usr : foundUsers) {
            if(count == 19){
                count = 0;
            }
            Visit newVisit = new Visit(usr.getId(), allMonuments.get(count).getId());
            visitService.addVisitToDb(newVisit);

            usr.addVisit(newVisit);

            authService.registerUser(usr);
            count++;
        }
    }
}
