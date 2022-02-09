package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.telegram.webhook.component.TelegramWebhookBotComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RestController
@RequestMapping("/telegram")
@RequiredArgsConstructor
public class TelegramRestController {

    private final TelegramWebhookBotComponent telegramWebhookBotComponent;

    @PostMapping
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramWebhookBotComponent.onWebhookUpdateReceived(update);
    }
}
