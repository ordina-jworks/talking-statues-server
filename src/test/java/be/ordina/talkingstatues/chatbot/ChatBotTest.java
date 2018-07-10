package be.ordina.talkingstatues.chatbot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ChatBotTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ChatBot chatBot = new ChatBot();

    @Test
    public void chat() {
        String response = chatBot.chat("What's your name?");

        assertTrue(response.contains("SUPER"));
    }

    @Test
    public void chat_emptyInput_throwsException() {
        expectedException.expect(IllegalArgumentException.class);
        chatBot.chat("");
    }
}