package com.exadel.sandbox.team2.service;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.handler.CallbackQueryHandler;
import com.exadel.sandbox.team2.handler.MessageHandler;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TelegramMessageService {

    TelegramAuthorizationService authorizationService;
    CallbackQueryHandler callbackQueryHandler;
    MessageHandler messageHandler;
    TelegramUtils utils;
    LocaleMessageService lms;

    public SendMessage handleUpdate(Update update) {

        String chatId = utils.getChatId(update);
        Optional<User> current = authorizationService.findActiveUserByChatId(chatId);

        if (current.isPresent()) {
            return update.hasCallbackQuery() ? callbackQueryHandler.handle(update, current.get()) : messageHandler.handle(update, current.get());
        }

        return update.getMessage().hasText() ?
                beforeGetContact(chatId, update.getMessage().getText().trim()) :
                afterGetContact(chatId, update.getMessage().getContact());
    }

    private SendMessage beforeGetContact(String chatId, String command) {
        String authorizationCode = command.substring("/start".length()).trim();
        if (!authorizationCode.isBlank()) {
            return authorizationService.authenticate(chatId, authorizationCode)
                    .map(user ->
                            utils.getMessage(chatId, lms.getMessage("reply.askPhoneForAuthentication"), lms.getMessage("reply.askToSharePhoneNumber")))
                    .orElse(utils.getMessage(chatId, lms.getMessage("error.youAreNotAbleToUseThisBot")));
        }
        return utils.getMessage(chatId, lms.getMessage("reply.askPhoneForInvitation"), lms.getMessage("reply.askToSharePhoneNumber"));
    }

    private SendMessage afterGetContact(String chatId, Contact contact) {
        return authorizationService.authenticatePhoneNumber(chatId, contact.getPhoneNumber())
                .map(user -> user.getStatus() == UserState.NEW ?
                        utils.getMessage(chatId, lms.getMessage("reply.weSentInvitationToYourEmail")) :
                        utils.getMessage(chatId, "Please select action",
                                new String[][]{{lms.getMessage("menu.menu"), lms.getMessage("menu.account")},
                                        {lms.getMessage("menu.contact"), lms.getMessage("menu.settings")}},
                                new String[][]{{"\uD83D\uDDD2 Menu", "\uD83D\uDC64 Account"},
                                        {"\uD83D\uDCD8 Contact", "⚙️ Settings"}})
                ).orElse(null);
    }
}
