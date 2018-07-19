package be.ordina.talkingstatues.visits;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.verify;

public class VisitServiceTest {

    private static final String USER_ID = "userID";
    private static final String MON_ID = "monId";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private VisitRepository visitRepository;
    @InjectMocks
    private VisitService visitService;

    @Test
    public void addVisitToDb() {
        Visit visit = new Visit(USER_ID, MON_ID);

        visitService.addVisitToDb(visit);
        verify(visitRepository).save(visit);
    }
}