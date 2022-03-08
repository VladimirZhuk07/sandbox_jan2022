package com.exadel.sandbox.team2.handler;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.handler.base.BaseHandler;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import com.exadel.sandbox.team2.service.LocaleMessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageHandler implements BaseHandler {

    TelegramUtils utils;
    LocaleMessageService lms;

    @Override
    public SendMessage handle(Update update, User user) {
        String chatId = utils.getChatId(update);
        String text = update.getMessage().getText();
        SendMessage sendMessage;

        switch (text) {
            case "\uD83D\uDDD2 Menu" -> {
                sendMessage = utils.getMessage(chatId, "Please select functionality",
                        new String[][]{{lms.getMessage("menu.booking"), lms.getMessage("menu.lastInformation")},
                                {lms.getMessage("menu.cancelBooking"), lms.getMessage("menu.changeBooking")}},
                        new String[][]{{"BOOK", "INFO"}, {"CANCEL", "CHANGE"}});
            }
            case "\uD83D\uDC64 Account" -> {
                sendMessage = utils.getMessage(chatId,
                        String.format("\uD83C\uDF1D " + lms.getMessage("account.firstName") + ": %s"
                                        + "\n\uD83C\uDF1A " + lms.getMessage("account.lastName") + ": %s"
                                        + "\n\uD83D\uDCDE " + lms.getMessage("account.phoneNumber") + ": %s"
                                        + "\n\uD83D\uDCE7 " + lms.getMessage("account.email") + ": %s",
                                user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail()));
            }
            case "\uD83D\uDCD8 Contact" -> {
                sendMessage = utils.getMessage(chatId, lms.getMessage("contact.contact"));
            }
            case "⚙️ Settings" -> {
                sendMessage = utils.getMessage(chatId, lms.getMessage("settings.pleaseSelectAction"),
                        new String[][]{{lms.getMessage("settings.changePhoneNumber"), lms.getMessage("settings.editAccountInformation")},
                                {lms.getMessage("settings.changeLanguage"), lms.getMessage("settings.report")}},
                        new String[][]{{"PHONE", "INFORMATION"}, {"LANGUAGE", "REPORT"}});
            }
            default -> {
                sendMessage = utils.getMessage(chatId, lms.getMessage("mH.commandNotFound"));
            }
        }
        return sendMessage;
    }
}
