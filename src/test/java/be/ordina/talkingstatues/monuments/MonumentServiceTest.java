package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.routes.RouteRequest;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static be.ordina.talkingstatues.dbpopulation.InitialMonumentData.MONUMENTS;
import static be.ordina.talkingstatues.monuments.Language.*;
import static be.ordina.talkingstatues.monuments.MonumentTestConstants.AREA;
import static be.ordina.talkingstatues.monuments.MonumentTestConstants.MON_ID;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MonumentServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private MonumentRepository monumentRepository;
    @Mock
    private GridFsTemplate gridFsTemplate;

    @InjectMocks
    private MonumentService monumentService;

    @Test
    public void initializeData() {

        monumentService.initializeData(MONUMENTS);

        verify(monumentRepository).deleteAll();
        MONUMENTS.forEach(monument -> verify(monumentRepository).save(monument));
    }

    @Test
    public void getAllMonuments() {
        List<Monument> expected = getMonumentsInDifferentAreas();
        when(monumentRepository.findAll()).thenReturn(expected);

        List<Monument> actual = monumentService.getAllMonuments();

        assertEquals(expected, actual);
    }

    @Test
    public void getMonumentsInRoute() {
        Monument monument = new Monument();
        monument.setId(MON_ID);
        when(monumentRepository.findById(MON_ID)).thenReturn(Optional.of(monument));

        List<Monument> actual = monumentService.getMonumentsInRoute(buildRouteRequest());

        assertTrue(actual.contains(monument));
    }

    @Test
    public void getMonumentById() {
        Monument expected = new Monument();
        when(monumentRepository.findById(MON_ID)).thenReturn(Optional.of(expected));

        Monument actual = monumentService.getMonumentById(MON_ID);

        assertEquals(expected, actual);
    }

    @Test
    public void getMonumentById_notFound() {
        when(monumentRepository.findById(MON_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> monumentService.getMonumentById(MON_ID))
                .withMessage("Monument with id: " + MON_ID + " does not exist");
    }

    @Test
    public void getMonumentInformationByIdAndLanguage() {
        Monument expected = new Monument();
        expected.setInformation(singletonList(new Information(NL, "name", "description", singletonList(new Conversation("1", "2")))));
        when(monumentRepository.findById(MON_ID)).thenReturn(Optional.of(expected));

        Information actual = monumentService.getMonumentInformationByIdAndLanguage(MON_ID, NL.name());

        assertEquals(expected.getInformation().get(0), actual);
    }

    @Test
    public void getMonumentInformationByIdAndLanguage_languageNotSupported() {
        Monument expected = new Monument();
        expected.setInformation(singletonList(new Information(NL, "name", "description", singletonList(new Conversation("1", "2")))));
        when(monumentRepository.findById(MON_ID)).thenReturn(Optional.of(expected));

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> monumentService.getMonumentInformationByIdAndLanguage(MON_ID, DE.name()))
                .withMessage("Requested language is not supported");
    }

    @Test
    public void getMonumentQuestionByIdAndLanguageAndQuestion() {
        // TODO david
    }

    @Test
    public void getRandomSelection_smallerThanTEN() {
        List<Monument> monuments = buildRandomMonuments(AREA, Language.NL, 3);
        when(monumentRepository.findAllByArea(AREA)).thenReturn(monuments);

        List<Monument> actual = monumentService.getRandomSelection(AREA, "NL");

        assertTrue(monuments.containsAll(actual));
    }

    @Test
    public void getRandomSelection_wrongLanguagesGetFilteredOut() {
        List<Monument> monuments = buildRandomMonuments(AREA, Language.NL, 3);
        monuments.add(getMonumentWithMultipleLanguages());
        when(monumentRepository.findAllByArea(AREA)).thenReturn(monuments);

        List<Monument> actual = monumentService.getRandomSelection(AREA, "NL");

        Monument specialOne = actual.stream()
                                      .filter(monument -> monument.getPicture().equals("specialOne"))
                                      .findFirst()
                                      .get();

        assertEnglishIsFilteredOut(specialOne);
    }

    @Test
    public void getRandomSelection_biggerThanTEN() {
        List<Monument> monuments = buildRandomMonuments(AREA, Language.NL, 15);
        when(monumentRepository.findAllByArea(AREA)).thenReturn(monuments);

        List<Monument> actual = monumentService.getRandomSelection(AREA, "NL");

        assertTrue(actual.size() <= 10);
    }

    @Test
    public void getAllAreas() {
        when(monumentRepository.findAll()).thenReturn(getMonumentsInDifferentAreas());

        List<String> actual = monumentService.getAllAreas();

        assertEquals(2, actual.size());
        assertEquals("hier", actual.get(0));
        assertEquals("daar", actual.get(1));
    }

    @Test
    public void addMonument() {
        Monument createdMonument = new Monument();
        createdMonument.setId(MON_ID);
        Monument monumentToSave = new Monument();
        when(monumentRepository.save(monumentToSave)).thenReturn(createdMonument);

        Monument actual = monumentService.addMonument(monumentToSave);

        assertEquals(createdMonument, actual);
    }

    @Test
    public void addMonument_hasId() {
        Monument createdMonument = new Monument();
        createdMonument.setId(MON_ID);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> monumentService.addMonument(createdMonument))
                .withMessage("the new monument already has an id");
    }

    @Test
    public void addInformation() {
        Monument monument = new Monument();
        monument.setId(MON_ID);
        monument.setInformation(new ArrayList<>());
        when(monumentRepository.findById(MON_ID)).thenReturn(Optional.of(monument));

        Information info = new Information();
        monumentService.addInformationToMonument(MON_ID, info);

        monument.setInformation(singletonList(info));
        verify(monumentRepository).save(monument);
    }

    @Test
    public void editMonument() {
        Monument existingMonument = new Monument();
        existingMonument.setId(MON_ID);
        existingMonument.setArea("firstArea");
        when(monumentRepository.findById(MON_ID)).thenReturn(Optional.of(existingMonument))
        ;
        Monument changedMonument = new Monument();
        changedMonument.setArea("changedArea");
        monumentService.editMonument(MON_ID, changedMonument);

        changedMonument.setId(MON_ID);
        verify(monumentRepository).save(changedMonument);
    }

    @Test
    public void editMonument_notFound() {
        when(monumentRepository.findById(MON_ID)).thenReturn(Optional.empty());

        Monument monument = new Monument();
        monumentService.editMonument(MON_ID, monument);
        Mockito.reset(monumentRepository);

        verifyNoMoreInteractions(monumentRepository);
    }

    @Test
    public void deleteMonument() {
        monumentService.deleteMonument(MON_ID);
        verify(monumentRepository).deleteById(MON_ID);
    }


    private List<Monument> getMonumentsInDifferentAreas() {
        Monument monument1 = new Monument(getInfo(NL), 5.00, 5.00, "hier", "pic");
        Monument monument2 = new Monument(getInfo(NL), 5.00, 5.00, "daar", "pic");
        Monument monument3 = new Monument(getInfo(NL), 5.00, 5.00, "hier", "pic");
        return Arrays.asList(monument1, monument2, monument3);
    }

    private void assertEnglishIsFilteredOut(Monument specialOne) {
        assertNotEquals(specialOne.getInformation().get(0).getLanguage(), EN);
        assertEquals(specialOne.getInformation().get(0).getLanguage(), NL);
    }

    private Monument getMonumentWithMultipleLanguages() {
        Information info1 = new Information(NL, "name", "desc", singletonList(new Conversation("1", "2")));
        Information info2 = new Information(EN, "name", "desc", singletonList(new Conversation("1", "2")));
        return new Monument(Arrays.asList(info1, info2), 5.00, 5.00, AREA, "specialOne");
    }

    private List<Monument> buildRandomMonuments(String area, Language language, int number) {
        ArrayList<Monument> monuments = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            monuments.add(new Monument(getInfo(language), 5.00, 5.00, area, "picture"));
        }
        return monuments;
    }

    private List<Information> getInfo(Language language) {
        return singletonList(new Information(language, "name", "description", singletonList(new Conversation("1", "2"))));
    }

    private RouteRequest buildRouteRequest() {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setName("test");
        routeRequest.setLocations(singletonList("monId"));
        return routeRequest;
    }
}