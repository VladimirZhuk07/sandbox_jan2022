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
public class CallbackQueryHandler implements BaseHandler {

    TelegramUtils utils;
    LocaleMessageService lms;

    @Override
    public SendMessage handle(Update update, User user) {
        String chatId = utils.getChatId(update);
        String command = update.getCallbackQuery().getData();
        SendMessage sendMessage;
        switch (command) {
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
            case "LANGUAGE" -> {
                sendMessage = utils.getMessage(chatId, lms.getMessage("settings.pleaseSelectLanguage"),
                        new String[][]{{lms.getMessage("language.belarusian"), lms.getMessage("language.english")},
                                {lms.getMessage("language.uzbek"), lms.getMessage("language.russian")}},
                        new String[][]{{"BY", "EN"}, {"UZ", "RU"}});
            }
            case "PHONE" -> {
                sendMessage = utils.getMessage(chatId, lms.getMessage("cBQH.reply.enterYourNewNumber"),
                        lms.getMessage("cBQH.reply.pleaseSendYourNumber"));
            }
            case "BY" -> {
                lms.setLocale("by-BY");
                sendMessage = getMessageChanged(chatId);
            }
            case "EN" -> {
                lms.setLocale("en-EN");
                sendMessage = getMessageChanged(chatId);
            }
            case "UZ" -> {
                lms.setLocale("uz-UZ");
                sendMessage = getMessageChanged(chatId);
            }
            case "RU" -> {
                lms.setLocale("ru-RU");
                sendMessage = getMessageChanged(chatId);
            }
            default -> {
                sendMessage = utils.getMessage(chatId, lms.getMessage("cBQH.status.weWorkWithThisCommand").concat(" ").concat(command));
            }
        }
        return sendMessage;

    }

    private SendMessage getMessageChanged(String chatId) {
        return utils.getMessage(chatId, lms.getMessage("status.languageChanged").concat(lms.getCurrentLanguage()));
    }
}
