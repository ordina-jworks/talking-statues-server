package be.ordina.talkingstatues.visits;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static be.ordina.talkingstatues.appusers.AppUserTestUtils.APP_USER_ID;
import static be.ordina.talkingstatues.monuments.MonumentTestUtils.MON_ID;
import static org.mockito.Mockito.verify;

public class VisitServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private VisitRepository visitRepository;
    @InjectMocks
    private VisitService visitService;

    @Test
    public void addVisitToDb() {
        Visit visit = new Visit(APP_USER_ID, MON_ID);

        visitService.addVisitToDb(visit);
        verify(visitRepository).save(visit);
    }
}