package com.exadel.sandbox.team2.telegram.webhook.component;

import com.exadel.sandbox.team2.telegram.webhook.service.TelegramMessageService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
@Component
public class TelegramWebhookBotComponent extends TelegramWebhookBot {
    private final TelegramMessageService messageService;

    @Value("${telegrambot.botUsername}")
    private String botUsername = "Exadel_office_bot";
    @Value("${telegrambot.botToken}")
    private String botToken = "5286582269:AAHWN4NhOps8q4Rz-T1PaMMNVBk0yuhLeoo";
    @Value("${telegrambot.botPath}")
    private String botPath = "https://b0ff-31-148-162-123.ngrok.io/api/telegram";


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return messageService.handleUpdate(update);
    }

    public TelegramWebhookBotComponent(TelegramMessageService messageService) {
        this.messageService = messageService;
    }
}

