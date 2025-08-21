package com.telegram.bot;

import com.telegram.bot.logic.ApiCall;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.name}")
    private String botName;

    private final ApiCall apiCall;

    public BotConfig(ApiCall apiCall) {
        this.apiCall = apiCall;
    }

    @Bean
    public NDSBot botExample() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        NDSBot bot = new NDSBot(token, botName, apiCall);
        botsApi.registerBot(bot);
        return bot;
    }
}

