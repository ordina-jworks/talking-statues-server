package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.appusers.AuthService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.ResponseEntity;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

public class MonumentControllerTest {

    private static final String ID = "id";
    private static final String NL = "NL";

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
    }

    @Test
    public void getRandomSelection() {
    }

    @Test
    public void addInformationToMonument() {
    }

    @Test
    public void getAllAreas() {
    }

    @Test
    public void chatWithMonument() {
    }

    @Test
    public void getMonuments() {
    }

    @Test
    public void addMonuments() {
    }

    @Test
    public void editMonument() {
    }

    @Test
    public void uploadImage() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void addVisit() {
    }
}