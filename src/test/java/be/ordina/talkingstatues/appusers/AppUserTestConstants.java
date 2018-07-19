package be.ordina.talkingstatues.appusers;

public class AppUserTestConstants {

    private AppUserTestConstants() {
    }

    public static final String HANDLE = "handle";
    public static final String NAME = "aName";
    public static final String LAST_NAME = "aLastName";
    public static final AppUser APP_USER = new AppUser(HANDLE, NAME, LAST_NAME);
    public static final String APP_USER_ID = "anId";
    public static final String PRINCIPAL = "principal";
    public static final AppUser ANOTHER_APP_USER = new AppUser(HANDLE, NAME, LAST_NAME);
}
