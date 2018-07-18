package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.appusers.AppUser;
import be.ordina.talkingstatues.appusers.AuthService;
import org.apache.http.auth.BasicUserPrincipal;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

public class MonumentControllerTest {

    private static final String ID = "id";
    private static final String NL = "NL";
    private static final String QUESTION = "what's your name?";
    private static final String ANSWER = "My name is David.";
    private static final String AREA = "randomArea";
    private static final String PRINCIPAL = "userName";
    private static final String MON_ID = "monId";

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
        expected.setId(ID);
        when(monumentService.getMonumentById(ID)).thenReturn(expected);

        Monument actual = monumentController.getMonument(ID);

        assertEquals(expected, actual);
    }

    @Test
    public void getImage() {
        Image image = new Image("content");
        when(monumentService.getImageForMonumentId(ID)).thenReturn(image);

        ResponseEntity actual = monumentController.getImage(ID);
        ResponseEntity<Image> expected = ResponseEntity.ok().header(CONTENT_DISPOSITION).body(image);

        assertEquals(expected, actual);
    }

    @Test
    public void getImageBase64() {
        Image image = new Image("content");
        when(monumentService.getImageForMonumentId(ID)).thenReturn(image);

        ResponseEntity actual = monumentController.getImageBase64(ID);
        ResponseEntity<Image> expected = ResponseEntity.ok().header(CONTENT_DISPOSITION).body(image);

        assertEquals(expected, actual);
    }

    @Test
    public void getMonumentInformation() {
        Information expected = new Information(Language.NL, "name", "description", emptyList());
        when(monumentService.getMonumentInformationByIdAndLanguage(ID, NL)).thenReturn(expected);

        Information actual = monumentController.getMonumentInformation(ID, NL);

        assertEquals(expected, actual);
        assertNull(expected.getQuestions());
    }

    @Test
    public void getMonumentQuestions() {
        Question expected = new Question(QUESTION, ANSWER);
        when(monumentService.getMonumentQuestionByIdAndLanguageAndQuestion(ID, NL, QUESTION))
                .thenReturn(expected);

        Question actual = monumentController.getMonumentQuestions(ID, NL, QUESTION);

        assertEquals(expected, actual);
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
        monumentController.addInformationToMonument(ID, info);

        verify(monumentService).addInformationToMonument(ID, info);
    }

    @Test
    public void getAllAreas() {
        monumentController.getAllAreas();

        verify(monumentService).getAllAreas();
    }

    @Test
    public void chatWithMonument() {
        when(monumentService.chatWithMonument(ID, QUESTION)).thenReturn(ANSWER);

        String actual = monumentController.chatWithMonument(ID, QUESTION);

        assertEquals(ANSWER, actual);
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
        monument.setId(ID);
        monumentController.editMonument(ID, monument);

        verify(monumentService).editMonument(ID, monument);
    }

    @Test
    public void editMonument_nonMatchingIDs() {
        Monument monument = new Monument();
        monument.setId("randomId");

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> monumentController.editMonument(ID, monument))
                .withMessage("ID's don't match");
    }

    @Test
    public void editMonument_monumentHasNoID() {
        Monument monument = new Monument();

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> monumentController.editMonument(ID, monument))
                .withMessage("invalid Monument");
    }

    @Test
    public void uploadImage() throws IOException {
        MultipartFile mockedFile = mock(MultipartFile.class);
        ResponseEntity actual = monumentController.uploadImage(mockedFile, ID);

        verify(monumentService).saveImage(mockedFile.getInputStream(), ID);
        assertEquals(ResponseEntity.ok().build(), actual);
    }

    @Test
    public void delete() {
        ResponseEntity actual = monumentController.delete(ID);
        verify(monumentService).deleteMonument(ID);

        assertEquals(ResponseEntity.ok().build(), actual);
    }

    @Test
    public void addVisit() {
        AppUser appUser = new AppUser(PRINCIPAL, "name", "lastName");
        appUser.setId(ID);
        when(authService.getUserByHandle(PRINCIPAL)).thenReturn(appUser);

        monumentController.addVisit(MON_ID, new BasicUserPrincipal(PRINCIPAL));

        assertEquals(1, appUser.getVisits().size());
        assertEquals(ID, appUser.getVisits().get(0).getUserId());
        assertEquals(MON_ID, appUser.getVisits().get(0).getMonumentId());
    }
}