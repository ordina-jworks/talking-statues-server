package be.ordina.talkingstatues.nlp;

import be.ordina.talkingstatues.monuments.MonumentRepository;
import be.ordina.talkingstatues.monuments.MonumentService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import static org.junit.Assert.assertTrue;

public class NaturalLanguageServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private NaturalLanguageService nlpService;

    @Test
    public void detectLanguage() {
        String testlang = nlpService.detectLanguage("Het gaat binnenkort regenen.");
        assertTrue("nld".equals(testlang));

        testlang = nlpService.detectLanguage("The rain will come soon.");
        assertTrue("eng".equals(testlang));

        testlang = nlpService.detectLanguage("La pluie va commencer");
        assertTrue("fra".equals(testlang));

        testlang = nlpService.detectLanguage("Es geht regnen");
        assertTrue("deu".equals(testlang));


    }

}
