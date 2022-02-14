package com.exadel.sandbox.team2.telegram.webhook.controller;

import com.exadel.sandbox.team2.telegram.webhook.component.TelegramWebhookBotComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RestController
@RequestMapping("/telegram")
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
