package com.exadel.sandbox.team2.telegram.webhook.component;

import com.exadel.sandbox.team2.telegram.webhook.service.TelegramMessageService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final String botUsername;
    private final String botToken;
    private final String botPath;

    /**
     * In this method, I use Constructor Dependency Injection to set values
     * from application.yml that is in sandbox-web, using @Value spring annotation
     * before type of variable.
     *
     * @param messageService Parameter
     * @param botUsername    the value is taken from application.yml.
     * @param botToken       the value is taken from application.yml.
     * @param botPath        the value is taken from application.yml.
     */
    @Autowired
    public TelegramWebhookBotComponent(TelegramMessageService messageService,
                                       @Value("${telegramBot.username}") String botUsername,
                                       @Value("${telegramBot.token}") String botToken,
                                       @Value("${telegramBot.path}") String botPath
    ) {
        this.messageService = messageService;
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botPath = botPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return messageService.handleUpdate(update);
    }
}
