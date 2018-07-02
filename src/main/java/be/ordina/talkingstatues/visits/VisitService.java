package be.ordina.talkingstatues.visits;

import org.springframework.stereotype.Service;

@Service
public class VisitService {

    private final VisitRepository visitRepository;


    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public void addVisitToDb(Visit newVisit){
        visitRepository.save(newVisit);
    }
}
