package be.ordina.talkingstatues.dbpopulation;

import be.ordina.talkingstatues.appusers.AppUser;
import be.ordina.talkingstatues.appusers.AuthService;
import be.ordina.talkingstatues.monuments.Monument;
import be.ordina.talkingstatues.monuments.MonumentService;
import be.ordina.talkingstatues.security.ApplicationConfigurationUtils;
import be.ordina.talkingstatues.visits.Visit;
import be.ordina.talkingstatues.visits.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PopulationService {

    private final MonumentService monumentService;
    private final AuthService authService;
    private final VisitService visitService;

    private ApplicationConfigurationUtils utils;


    private final List<Monument> allMonuments;

    public PopulationService(MonumentService monumentService, AuthService authService, VisitService visitService, ApplicationConfigurationUtils utils) {
        this.monumentService = monumentService;
        this.authService = authService;
        this.allMonuments = monumentService.getAllMonuments();
        this.visitService = visitService;
        this.utils = utils;

        if (Boolean.parseBoolean(utils.getProperty(ApplicationConfigurationUtils.INITIAL_DATA_MONUMENTS_KEY))) {
            System.out.println("Adding initial data for Monuments to DB...");
            monumentService.initializeData(InitialMonumentData.DATA);
        }

        if (Boolean.parseBoolean(utils.getProperty(ApplicationConfigurationUtils.INITIAL_DATA_VISITS_KEY))) {
            System.out.println("Adding initial data for Visits and Users to DB...");
            authService.initializeUserData(InitialUserData.DATA);
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