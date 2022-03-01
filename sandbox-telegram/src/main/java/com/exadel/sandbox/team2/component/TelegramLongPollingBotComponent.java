package com.exadel.sandbox.team2.component;

import com.exadel.sandbox.team2.service.TelegramMessageService;
import com.exadel.sandbox.team2.configuration.TelegramProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
@RequiredArgsConstructor
//@Component
//@Profile("dev")
public class TelegramLongPollingBotComponent extends TelegramLongPollingBot {

    private final TelegramProperties telegramProperties;
    private final TelegramMessageService telegramMessageService;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        var message = telegramMessageService.handleUpdate(update);
        execute(message);
    }


    @Override
    public String getBotUsername() {
        return telegramProperties.getBot().getUsername();
    }

    @Override
    public String getBotToken() {
        return telegramProperties.getBot().getToken();
    }
}
