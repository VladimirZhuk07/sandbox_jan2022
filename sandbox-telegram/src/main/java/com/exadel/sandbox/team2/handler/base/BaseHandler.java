package com.exadel.sandbox.team2.handler.base;

import com.exadel.sandbox.team2.domain.User;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface BaseHandler {
  SendMessage handleSendMessage(Update update, User user);
  EditMessageText handleEditMessage(Update update, User user);
}
