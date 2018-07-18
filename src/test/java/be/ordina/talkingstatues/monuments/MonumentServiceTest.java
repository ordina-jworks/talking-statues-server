package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.chatbot.ChatBotService;
import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

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

}