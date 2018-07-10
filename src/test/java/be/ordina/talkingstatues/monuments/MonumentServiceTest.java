package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.chatbot.ChatBotService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MonumentServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private MonumentRepository monumentRepository;
    @Mock
    private GridFsTemplate gridFsTemplate;
    @Mock
    private ChatBotService chatBotService;

    @InjectMocks
    private MonumentService monumentService;

    @Test
    public void chatWithMonument() {
        final String expected = "Hi! It's delightful to see you.";
        Monument monument = new Monument();
        monument.setId("1");

        when(chatBotService.processUserInput("Hi")).thenReturn(expected);
        when(monumentRepository.findById("1")).thenReturn(Optional.of(monument));

        String actual = monumentService.chatWithMonument("1", "Hi");

        assertEquals(actual, expected);
    }
}