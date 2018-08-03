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
import org.thymeleaf.util.StringUtils;

import org.junit.Assert;

import java.util.List;

public class NaturalLanguageServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private NaturalLanguageService nlpService;

    @Test
    public void testInitialization() {
        for (Language l : Language.values()) {
            Assert.assertEquals(
                    !StringUtils.isEmptyOrWhitespace(l.getSentenceDetectorFile()),
                    nlpService.hasSentenceDetectorFor(l));
        }
    }

    @Test
    public void detectLanguage() {
        String testlang = nlpService.detectLanguage("Het gaat binnenkort regenen.");
        Assert.assertTrue("nld".equals(testlang));

        testlang = nlpService.detectLanguage("The rain will come soon.");
        Assert.assertTrue("eng".equals(testlang));

        testlang = nlpService.detectLanguage("La pluie va commencer");
        Assert.assertTrue("fra".equals(testlang));

        testlang = nlpService.detectLanguage("Es geht regnen");
        Assert.assertTrue("deu".equals(testlang));

    }

    @Test(expected = IllegalArgumentException.class)
    public void hasSentenceDetectorFor() {
        nlpService.hasSentenceDetectorFor(null);
    }

    @Test
    public void splitIntoPhrases() {
        List<String> nederlands = nlpService.splitIntoPhrases(Language.NL,
                "Het gaat regenen! De zon schijnt? Het ijs is slipperig en het monster is onderweg.");
        Assert.assertEquals(3, nederlands.size());

        List<String> engels = nlpService.splitIntoPhrases(Language.EN,
                "The sun is shining! But the ice is slippery? This cannot be. There must be something more to this.");
        Assert.assertEquals(4, engels.size());

        List<String> frans = nlpService.splitIntoPhrases(Language.FR,
                "La pluie va commencer. Il me faut quelque chose pour me secher!");
        Assert.assertEquals(1, frans.size());

    }
}
