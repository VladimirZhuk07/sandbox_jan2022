package com.exadel.sandbox.team2.handler;

import com.exadel.sandbox.team2.handler.base.BaseHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageHandle implements BaseHandler {
  @Override
  public SendMessage handle(Update update) {
    return null;
  }
}
