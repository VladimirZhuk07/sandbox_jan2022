package com.exadel.sandbox.team2.component;

import com.exadel.sandbox.team2.service.TelegramMessageService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Getter
@Setter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramWebhookBotComponent extends TelegramWebhookBot {
    private final TelegramMessageService messageService;
    @Value("${telegrambot.botPath}")
    String botPath;
    @Value("${telegrambot.botUsername}")
    String botUsername;
    @Value("${telegrambot.botToken}")
    String botToken;

    public TelegramWebhookBotComponent(TelegramMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return messageService.handleUpdate(update);
    }
}
