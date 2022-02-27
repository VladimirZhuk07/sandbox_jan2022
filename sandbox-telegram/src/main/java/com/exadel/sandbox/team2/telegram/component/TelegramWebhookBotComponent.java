package com.exadel.sandbox.team2.telegram.component;

import com.exadel.sandbox.team2.telegram.configuration.TelegramProperties;
import com.exadel.sandbox.team2.telegram.service.TelegramMessageService;
import com.exadel.sandbox.team2.telegram.service.TelegramWebhookService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Getter
@Setter
@RequiredArgsConstructor
@Component
@Profile("prod")
public class TelegramWebhookBotComponent extends TelegramWebhookBot  {

    private final TelegramProperties telegramProperties;
    private final TelegramMessageService messageService;
    private final TelegramWebhookService telegramWebhookService;

    @PostConstruct
    public void initializeWebhook() throws TelegramApiException {
        var webhookInfo = telegramWebhookService.getWebhookInfo();
        if(webhookInfo.isEmpty()){
            throw new TelegramApiException("1");
        }

        if(!webhookInfo.get().isOk()){
            throw new TelegramApiException("2");
        }

        String url = webhookInfo.get().getResult().getUrl();

        if(telegramProperties.getBot().getWebhook().getPath().equals(url)) {
            return;
        }

        var webhook = telegramWebhookService.setWebhookInfo();

        if(webhook.isEmpty()){
            throw new TelegramApiException("3");
        }

        if(webhook.get().isOk() && webhook.get().isResult()){
            return;
        }

        throw new TelegramApiException("4");

    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return messageService.handleUpdate(update);
    }

    @Override
    public String getBotPath() {
        return telegramProperties.getBot().getWebhook().getPath();
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
