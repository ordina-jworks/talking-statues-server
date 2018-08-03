package be.ordina.talkingstatues.nlp;

import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Service
public class NaturalLanguageService {

    private LanguageDetector detector;

    public NaturalLanguageService() {
        try (InputStream model = Thread.currentThread().getContextClassLoader().getResourceAsStream("langdetect-183.bin")) {
            LanguageDetectorModel ldm = new LanguageDetectorModel(model);
            this.detector = new LanguageDetectorME(ldm);
        } catch (IOException ioe) {
            // Throw a meaningful exception...
        }
    }

    public String detectLanguage (String phrase) {
        Language language = this.detector.predictLanguage(phrase);
        System.out.println("Detected " + language.toString() + " for phrase [" + phrase + "]");
        return language.getLang();
    }

}
