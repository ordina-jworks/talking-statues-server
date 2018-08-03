package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.appusers.AppUser;
import be.ordina.talkingstatues.appusers.AuthService;
import be.ordina.talkingstatues.nlp.Language;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static be.ordina.talkingstatues.appusers.AppUserTestUtils.APP_USER_ID;
import static be.ordina.talkingstatues.appusers.AppUserTestUtils.PRINCIPAL;
import static be.ordina.talkingstatues.monuments.MonumentTestUtils.*;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

public class MonumentControllerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AuthService authService;
    @Mock
    private MonumentService monumentService;
    @InjectMocks
    private MonumentController monumentController;

    @Test
    public void getMonument() {
        Monument expected = new Monument();
        expected.setId(MON_ID);
        when(monumentService.getMonumentById(MON_ID)).thenReturn(expected);

        Monument actual = monumentController.getMonument(MON_ID);

        assertEquals(expected, actual);
    }

    @Test
    public void getImage() {
        GridFsResource gridFsResource = mock(GridFsResource.class);
        when(monumentService.getImageForMonumentId(MON_ID)).thenReturn(gridFsResource);

        ResponseEntity actual = monumentController.getImage(MON_ID);
        ResponseEntity<GridFsResource> expected = ResponseEntity.ok().header(CONTENT_DISPOSITION).body(gridFsResource);

        assertEquals(expected, actual);
    }

    @Test
    public void getImageBase64() {
        GridFsResource gridFsResource = mock(GridFsResource.class);
        when(monumentService.getImageForMonumentId(MON_ID)).thenReturn(gridFsResource);

        ResponseEntity actual = monumentController.getImageBase64(MON_ID);
        ResponseEntity<GridFsResource> expected = ResponseEntity.ok().header(CONTENT_DISPOSITION).body(gridFsResource);

        assertEquals(expected, actual);
    }

    @Test
    public void getMonumentInformation() {
        Information expected = new Information(Language.NL, "name", "description", emptyList());
        when(monumentService.getMonumentInformationByIdAndLanguage(MON_ID, NL)).thenReturn(expected);

        Information actual = monumentController.getMonumentInformation(MON_ID, NL);

        assertEquals(expected, actual);
        assertNull(expected.getConversations());
    }

    @Test
    public void getMonumentQuestions() {
        Conversation expected = new Conversation(QUESTION, ANSWER);
        when(monumentService.findAnswer(MON_ID, NL, QUESTION_VALUE))
                .thenReturn(expected.getAnswer());

        String actual = monumentController.getMonumentAnswer(MON_ID, NL, QUESTION_VALUE);

        assertEquals(expected.getAnswer(), actual);
    }

    @Test
    public void getRandomSelection() {
        List<Monument> expected = Arrays.asList(new Monument(), new Monument());
        when(monumentService.getRandomSelection(AREA, NL)).thenReturn(expected);

        List<Monument> actual = monumentController.getRandomSelection(AREA, NL);

        assertEquals(expected, actual);
    }

    @Test
    public void addInformationToMonument() {
        Information info = new Information();
        monumentController.addInformationToMonument(MON_ID, info);

        verify(monumentService).addInformationToMonument(MON_ID, info);
    }

    @Test
    public void getAllAreas() {
        monumentController.getAllAreas();

        verify(monumentService).getAllAreas();
    }

    @Test
    public void getMonuments() {
        List<Monument> expected = Arrays.asList(new Monument(), new Monument());
        when(monumentService.getAllMonuments()).thenReturn(expected);

        List<Monument> actual = monumentController.getMonuments();

        assertEquals(expected, actual);
    }

    @Test
    public void addMonuments() {
        Monument newMonument = new Monument();
        when(monumentService.addMonument(newMonument)).thenReturn(newMonument);
        Monument createdMonument = monumentController.addMonument(newMonument);

        assertEquals(newMonument, createdMonument);
    }

    @Test
    public void editMonument() {
        Monument monument = new Monument();
        monument.setId(MON_ID);
        monumentController.editMonument(MON_ID, monument);

        verify(monumentService).editMonument(MON_ID, monument);
    }

    @Test
    public void editMonument_nonMatchingIDs() {
        Monument monument = new Monument();
        monument.setId("randomId");

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> monumentController.editMonument(MON_ID, monument))
                .withMessage("ID's don't match");
    }

    @Test
    public void editMonument_monumentHasNoID() {
        Monument monument = new Monument();

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> monumentController.editMonument(MON_ID, monument))
                .withMessage("invalid Monument");
    }

    @Test
    public void uploadImage() throws IOException {
        MultipartFile mockedFile = mock(MultipartFile.class);
        ResponseEntity actual = monumentController.uploadImage(mockedFile, MON_ID);

        verify(monumentService).saveImage(mockedFile.getInputStream(), MON_ID);
        assertEquals(ResponseEntity.ok().build(), actual);
    }

    @Test
    public void delete() {
        ResponseEntity actual = monumentController.delete(MON_ID);
        verify(monumentService).deleteMonument(MON_ID);

        assertEquals(ResponseEntity.ok().build(), actual);
    }

    @Test
    public void addVisit() {
        AppUser appUser = new AppUser(PRINCIPAL, "name", "lastName");
        appUser.setId(APP_USER_ID);
        when(authService.getUserByHandle(PRINCIPAL)).thenReturn(appUser);

        monumentController.addVisit(MON_ID, () -> PRINCIPAL);

        assertEquals(1, appUser.getVisits().size());
        assertEquals(APP_USER_ID, appUser.getVisits().get(0).getUserId());
        assertEquals(MON_ID, appUser.getVisits().get(0).getMonumentId());
    }
}