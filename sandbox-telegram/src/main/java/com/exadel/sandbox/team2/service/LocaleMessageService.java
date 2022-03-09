package com.exadel.sandbox.team2.service;


import com.exadel.sandbox.team2.configuration.LocaleProperties;
import net.bytebuddy.asm.Advice;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Works with the template file "messages" messages.properties
 */

@Service
public class LocaleMessageService {
    private Locale locale;
    private MessageSource messageSource;
    private LocaleProperties localeProperties;

    public LocaleMessageService(MessageSource messageSource, LocaleProperties localeProperties) {
        this.messageSource = messageSource;
        this.localeProperties = localeProperties;
        this.locale = Locale.forLanguageTag(localeProperties.getEnglish());

    }

    public String getCurrentLanguage() {
        return locale.getDisplayLanguage();
    }

    public void setLocale(String localeProperty) {
        this.locale = Locale.forLanguageTag(localeProperty);
        System.out.println(localeProperty);
    }

    public String getMessage(String message) {
        return messageSource.getMessage(message, null, locale);
    }

    public String getMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, locale);
    }
}
