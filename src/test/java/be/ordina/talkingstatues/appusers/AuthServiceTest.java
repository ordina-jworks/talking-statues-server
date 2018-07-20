package be.ordina.talkingstatues.appusers;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static be.ordina.talkingstatues.appusers.AppUserTestUtils.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AppUserRepository appUserRepository;
    @InjectMocks
    private AuthService authService;

    @Test
    public void initializeUserData() {
        AppUser appUsers[] = new AppUser[]{APP_USER, ANOTHER_APP_USER};

        authService.initializeUserData(appUsers);

        verify(appUserRepository).deleteAll();
        Arrays.stream(appUsers).forEach(appUser -> verify(appUserRepository).save(appUser));
    }

    @Test
    public void registerUser() {
        when(appUserRepository.save(APP_USER)).thenReturn(APP_USER);

        AppUser actual = authService.registerUser(APP_USER);

        assertEquals(APP_USER, actual);
    }

    @Test
    public void getUserByHandle() {
        when(appUserRepository.findByHandle(HANDLE)).thenReturn(Optional.of(APP_USER));

        AppUser actual = authService.getUserByHandle(HANDLE);

        assertEquals(APP_USER, actual);
    }

    @Test
    public void getUserByHandle_notFound() {
        when(appUserRepository.findByHandle(HANDLE)).thenReturn(Optional.empty());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> authService.getUserByHandle(HANDLE))
                .withMessage("user not found");
    }

    @Test
    public void getAllUsersFromDb() {
        List<AppUser> expected = Arrays.asList(APP_USER, ANOTHER_APP_USER);
        when(appUserRepository.findAll()).thenReturn(expected);

        List<AppUser> actual = authService.getAllUsersFromDb();

        assertEquals(expected, actual);
    }

    @Test
    public void deleteUserFromDb() {
        authService.deleteUserFromDb(APP_USER_ID);
        verify(appUserRepository).deleteById(APP_USER_ID);
    }

    @Test
    public void getUserById() {
        when(appUserRepository.findById(APP_USER_ID)).thenReturn(Optional.of(APP_USER));

        AppUser actual = authService.getUserById(APP_USER_ID);

        assertEquals(APP_USER, actual);
    }

    @Test
    public void getUserById_userNotFound() {
        when(appUserRepository.findById(APP_USER_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> authService.getUserById(APP_USER_ID))
                .withMessage("user not present");
    }
}