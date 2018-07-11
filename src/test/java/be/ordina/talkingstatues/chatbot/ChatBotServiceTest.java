package be.ordina.talkingstatues.chatbot;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ChatBotServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ChatBotService chatBotService = new ChatBotService();

    @Test
    public void chat() {
        String response = chatBotService.processUserInput("What's your name?");

        assertTrue(response.contains("SUPER"));
    }

    @Test
    public void chat_emptyInput_throwsException() {
        expectedException.expect(IllegalArgumentException.class);
        chatBotService.processUserInput("");
    }
}