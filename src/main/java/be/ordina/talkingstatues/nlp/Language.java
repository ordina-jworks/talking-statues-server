package be.ordina.talkingstatues.nlp;

public enum Language {
    NL("nl","nld","nlpmodels/nld-sent-150.bin"),
    FR("fr","fra",null),
    EN("en","eng","nlpmodels/eng-sent-150.bin"),
    DE("de","deu","nlpmodels/deu-sent-150.bin");

    private String iso2code;
    private String iso3code;
    private String sentenceDetectorFile;

    Language(String iso2code, String iso3code, String sentenceDetectorFile) {
        this.iso2code = iso2code;
        this.iso3code = iso3code;
        this.sentenceDetectorFile = sentenceDetectorFile;
    }

    public String getSentenceDetectorFile() {
        return this.sentenceDetectorFile;
    }
}