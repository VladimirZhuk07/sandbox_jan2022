package com.exadel.sandbox.team2.service;

import com.exadel.sandbox.team2.configuration.TelegramProperties;
import com.exadel.sandbox.team2.dto.TelegramGetInfoResponseDto;
import com.exadel.sandbox.team2.dto.TelegramSetWebhookResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
@Service
@Profile("prod")
public class TelegramWebhookService {

    private final RestTemplate restTemplate;
    private final TelegramProperties telegramProperties;

    public Optional<TelegramGetInfoResponseDto> getWebhookInfo() {
        String url = telegramProperties.getApi().getBase().getPath().concat(telegramProperties.getBot().getToken()).concat(telegramProperties.getBot().getWebhook().getWebhookInfoPath());

        ResponseEntity<TelegramGetInfoResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, TelegramGetInfoResponseDto.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return Optional.ofNullable(response.getBody());
        }
        return Optional.empty();
    }

    public Optional<TelegramSetWebhookResponseDto> setWebhookInfo() {
        String url = telegramProperties.getApi().getBase().getPath().concat(telegramProperties.getBot().getToken()).concat(telegramProperties.getBot().getWebhook().getSetWebhookPath().concat("?url=")).concat(telegramProperties.getBot().getWebhook().getPath());

        ResponseEntity<TelegramSetWebhookResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, TelegramSetWebhookResponseDto.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return Optional.ofNullable(response.getBody());
        }

        return Optional.empty();
    }
}
