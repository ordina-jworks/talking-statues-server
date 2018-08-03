package be.ordina.talkingstatues.nlp;

import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NaturalLanguageService {

    private LanguageDetector languageDetector;
    private SentenceDetector[] sentenceDetectors;

    public NaturalLanguageService() {
        /** Initialise the Language Detector */
        try (InputStream model = Thread.currentThread().getContextClassLoader().getResourceAsStream("nlpmodels/langdetect-183.bin")) {
            LanguageDetectorModel ldm = new LanguageDetectorModel(model);
            this.languageDetector = new LanguageDetectorME(ldm);
        } catch (IOException ioe) {
            // Throw a meaningful exception...
        }
        /** Initialise the Sentence Detectors */
        this.sentenceDetectors = new SentenceDetector[Language.values().length];
        Arrays.stream(Language.values())
                .forEach(lang -> this.sentenceDetectors[lang.ordinal()] = this.loadSentenceDetector(lang.getSentenceDetectorFile()))
        ;
    }

    private SentenceDetector loadSentenceDetector(String path) {
        if (StringUtils.isEmptyOrWhitespace(path)) return null;
        try (InputStream model = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            return new SentenceDetectorME(new SentenceModel(model));
        } catch (IOException ioe) {
            System.out.println("Unable to load Sentence Detector based on file [" + path + "]");
            return null;
        }
    }

    public String detectLanguage (String phrase) {
        opennlp.tools.langdetect.Language language = this.languageDetector.predictLanguage(phrase);
        System.out.println("Detected " + language.toString() + " for phrase [" + phrase + "]");
        // Check confidence + Available languages for UnknownLanguageException!
        return language.getLang();
    }

    public boolean hasSentenceDetectorFor(Language language) {
        if (language == null) throw new IllegalArgumentException("Provided language cannot be empty.");
        return this.sentenceDetectors[language.ordinal()] != null;
    }

    public List<String> splitIntoPhrases(Language language, String conversation) {
        if (StringUtils.isEmptyOrWhitespace(conversation)) return new ArrayList<String>();
        if (this.hasSentenceDetectorFor(language)) {
            return Arrays.asList(this.sentenceDetectors[language.ordinal()].sentDetect(conversation));
        } else {
            System.out.println("There is no Sentence Detector calibrated for Language [" + language + "]. Returning the phrase as inputted...");
            return Arrays.asList(conversation);
        }
    }
}
