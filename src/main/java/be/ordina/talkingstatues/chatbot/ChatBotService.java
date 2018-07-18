package be.ordina.talkingstatues.chatbot;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ChatBotService {

    private static final boolean TRACE_MODE = false;
    private static final String BOT_NAME = "super";

    /*
    public String chat(String userInput) {
        final String processedInput = processUserInput(userInput);
        final Chat chatSession = setupBot();

        if (MagicBooleans.trace_mode) {
            return "STATE=" + processedInput + ":THAT=" + chatSession.thatHistory.get(0).get(0) + ":TOPIC=" + chatSession.predicates.get("topic");
        }

        return getResponseFromBot(processedInput, chatSession);
    }
    */

    private String processUserInput(String userInput) {
        if (userInput == null || userInput.length() < 1) {
            throw new IllegalArgumentException();
        }
        return userInput.contains("\"") ? userInput.substring(userInput.indexOf('"') + 1, userInput.lastIndexOf('"')) : userInput;
    }

    /*
    private String getResponseFromBot(String userInput, Chat chatSession) {
        String response = chatSession.multisentenceRespond(userInput);
        while (response.contains("&lt;")) {
            response = response.replace("&lt;", "<");
        }
        while (response.contains("&gt;")) {
            response = response.replace("&gt;", ">");
        }
        return response;
    }
    */

    /*
    private Chat setupBot() {
        MagicBooleans.trace_mode = TRACE_MODE;
        Bot bot = new Bot(BOT_NAME, getResourcesPath());
        Chat chatSession = new Chat(bot);
        bot.brain.nodeStats();
        return chatSession;
    }
    */

    /*
    private String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        return path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    }
    */
}
