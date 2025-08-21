package com.telegram.bot;

import com.telegram.bot.domain.BotConstants;
import com.telegram.bot.logic.ApiCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class NDSBot extends TelegramLongPollingBot {

    private final String botName;
    private final String botToken;
    private final ApiCall apiCall;

    private final Logger log = LoggerFactory.getLogger(NDSBot.class);

    public NDSBot(String botToken, String botName, ApiCall apiCall) {
        super(botToken);
        this.botToken = botToken;
        this.botName = botName;
        this.apiCall = apiCall;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        log.info(String.format("Received message: %s", message));

        if (message.equals(BotConstants.start)) {
            sendLongMessage(chatId, BotConstants.greetings);
        } else {
            String roms;
            try {
                roms = apiCall.fetchHtml(message);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            sendLongMessage(chatId, roms);
        }
    }

    private SendMessage generateSendMessage(Long chatId, String roms) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(roms);
        message.enableHtml(true); // allow HTML parsing for beautifying message
        return message;
    }

    private void sendLongMessage(Long chatId, String text) {
        final int MAX_LENGTH = 4096;
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + MAX_LENGTH, text.length());

            // Try to split at a newline if possible
            if (end < text.length()) {
                int lastNewline = text.lastIndexOf('\n', end);
                if (lastNewline > start) {
                    end = lastNewline + 1; // include the newline
                }
            }

            String chunk = text.substring(start, end);
            try {
                execute(generateSendMessage(chatId, chunk));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            start = end;
        }
    }
}
