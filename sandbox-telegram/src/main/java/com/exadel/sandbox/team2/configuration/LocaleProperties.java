package com.exadel.sandbox.team2.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class LocaleProperties {
    @Value("${language.english}")
    private String english;

    @Value("${language.belarusian}")
    private String belarusian;

    @Value("${language.uzbek}")
    private String uzbek;

    @Value("${language.russian}")
    private String russian;
}
