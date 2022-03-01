package com.exadel.sandbox.team2.handler.base;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BaseHandler {
  SendMessage handle(Update update);
}
