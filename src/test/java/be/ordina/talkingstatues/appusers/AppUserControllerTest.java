package be.ordina.talkingstatues.appusers;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static be.ordina.talkingstatues.appusers.AppUserTestUtils.*;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

public class AppUserControllerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AuthService authService;

    @InjectMocks
    private AppUserController appUserController;

    @Test
    public void getAllUsers() {
        List<AppUser> expected = singletonList(APP_USER);
        when(authService.getAllUsersFromDb()).thenReturn(expected);

        List<AppUser> actual = appUserController.getAllUsers();

        assertEquals(expected, actual);
    }

    @Test
    public void findUserById() {
        when(authService.getUserById(APP_USER_ID)).thenReturn(APP_USER);

        AppUser actual = appUserController.findUserById(APP_USER_ID);

        assertEquals(APP_USER, actual);
    }

    @Test
    public void addUser() {
        when(authService.registerUser(APP_USER)).thenReturn(APP_USER);

        AppUser actual = appUserController.addUser(APP_USER);

        assertEquals(APP_USER, actual);
    }

    @Test
    public void addUser_null() {
        AppUser actual = appUserController.addUser(null);

        assertNull(actual);
    }

    @Test
    public void userCreation_authenticated() {
        AppUser expected = new AppUser(PRINCIPAL, NAME, LAST_NAME);

        final OAuth2AuthenticationToken token = buildToken();
        appUserController.userCreation(token);

        verify(authService).registerUser(refEq(expected));

    }

    @Test
    public void userCreation_notAuthenticated() {
        final OAuth2AuthenticationToken token = buildToken();
        token.setAuthenticated(false);
        appUserController.userCreation(token);

        verifyNoMoreInteractions(authService);
    }

    @Test
    public void deleteFoundUser() {
        appUserController.deleteFoundUser(APP_USER_ID);

        verify(authService).deleteUserFromDb(APP_USER_ID);
    }

    @Test
    public void forgetFoundUser() {
        when(authService.getUserById(APP_USER_ID)).thenReturn(APP_USER);

        appUserController.forgetFoundUser(APP_USER_ID);

        verify(authService).registerUser(refEq(new AppUser("", "", "")));
    }

    private OAuth2AuthenticationToken buildToken() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("nameAttributeKey", PRINCIPAL);
        attributes.put("name", NAME + " " + LAST_NAME);
        authorities.add(new OAuth2UserAuthority(" authority", attributes));
        DefaultOAuth2User principal = new DefaultOAuth2User(authorities, attributes, "nameAttributeKey");
        return new OAuth2AuthenticationToken(principal, authorities, "registrationId");
    }
}