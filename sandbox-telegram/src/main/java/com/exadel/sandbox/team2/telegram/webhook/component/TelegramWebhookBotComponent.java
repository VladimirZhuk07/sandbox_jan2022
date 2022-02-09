package com.exadel.sandbox.team2.telegram.webhook.component;

import com.exadel.sandbox.team2.telegram.webhook.service.TelegramMessageService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
@Component
public class TelegramWebhookBotComponent extends TelegramWebhookBot {
    private TelegramMessageService messageService;

    /*    @Value("${telegramBot.botUsername}")
    private String botUsername;
    @Value("${telegramBot.botToken}")
    private String botToken;
    @Value("${telegramBot.botPath}")
    private String botPath;*/

    private String botUsername;
    private String botToken;
    private String botPath;

    @Autowired
    public TelegramWebhookBotComponent(String botUsername, String botToken, String botPath) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botPath = botPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return messageService.handleUpdate(update);
    }

    public TelegramWebhookBotComponent(TelegramMessageService messageService) {
        this.messageService = messageService;
    }
}
