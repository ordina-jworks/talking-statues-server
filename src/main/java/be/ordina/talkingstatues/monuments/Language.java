package be.ordina.talkingstatues.monuments;

public enum Language {
    NL("nl","nld"),
    FR("fr","fra"),
    EN("en","eng"),
    DE("de","deu");

    private String iso2code;
    private String iso3code;

    Language(String iso2code, String iso3code) {
        this.iso2code = iso2code;
        this.iso3code = iso3code;
    }
}