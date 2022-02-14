package com.exadel.sandbox.team2.telegram.component;

import com.exadel.sandbox.team2.telegram.service.TelegramMessageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
@RequiredArgsConstructor
@Component
@Profile("dev")
public class TelegramLongPollingBotComponent extends TelegramLongPollingBot {

    @Value("${telegrambot.botUsername}")
    private  String botUsername;
    @Value("${telegrambot.botToken}")
    private  String botToken;

    private final TelegramMessageService telegramMessageService;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        var message = telegramMessageService.handleUpdate(update);
        execute(message);
    }


}
