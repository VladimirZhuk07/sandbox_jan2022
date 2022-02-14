package com.exadel.sandbox.team2.telegram.controller;

import com.exadel.sandbox.team2.telegram.component.TelegramWebhookBotComponent;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;


@RestController
@RequestMapping("/telegram")
@Profile("prod")
public class TelegramRestController {

    private final TelegramWebhookBotComponent telegramWebhookBotComponent;

    public TelegramRestController(TelegramWebhookBotComponent telegramWebhookBotComponent) {
        this.telegramWebhookBotComponent = telegramWebhookBotComponent;
    }

    @PostMapping
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramWebhookBotComponent.onWebhookUpdateReceived(update);
    }
}
