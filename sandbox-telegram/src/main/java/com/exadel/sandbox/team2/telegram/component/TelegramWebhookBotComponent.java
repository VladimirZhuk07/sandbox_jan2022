package com.exadel.sandbox.team2.telegram.component;

import com.exadel.sandbox.team2.telegram.service.TelegramMessageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
@RequiredArgsConstructor
@Component
@Profile("prod")
public class TelegramWebhookBotComponent extends TelegramWebhookBot  {

    private final TelegramMessageService messageService;
    @Value("${telegrambot.botUsername}")
    private  String botUsername;
    @Value("${telegrambot.botToken}")
    private  String botToken;
    @Value("${telegrambot.botPath}")
    private  String botPath;

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return messageService.handleUpdate(update);
    }
}
