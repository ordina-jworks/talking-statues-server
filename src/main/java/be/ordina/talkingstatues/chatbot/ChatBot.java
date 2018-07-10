package be.ordina.talkingstatues.chatbot;

import org.alicebot.ab.*;

import java.io.File;

public class ChatBot {

    private static final boolean TRACE_MODE = false;
    private static final String BOT_NAME = "super";

    public String chat(String userInput) {
        if (userInput == null || userInput.length() < 1) {
            throw new IllegalArgumentException();
        }
        MagicBooleans.trace_mode = TRACE_MODE;
        Bot bot = new Bot(BOT_NAME, getResourcesPath());
        Chat chatSession = new Chat(bot);
        bot.brain.nodeStats();

        if (MagicBooleans.trace_mode) {
            return "STATE=" + userInput + ":THAT=" + chatSession.thatHistory.get(0).get(0) + ":TOPIC=" + chatSession.predicates.get("topic");
        }

        String response = chatSession.multisentenceRespond(userInput);
        while (response.contains("&lt;")) {
            response = response.replace("&lt;", "<");
        }
        while (response.contains("&gt;")) {
            response = response.replace("&gt;", ">");
        }

        return response;
    }

    private String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        return path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    }
}
