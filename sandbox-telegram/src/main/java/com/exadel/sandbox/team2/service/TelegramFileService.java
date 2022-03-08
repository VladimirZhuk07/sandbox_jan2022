package com.exadel.sandbox.team2.service;

import com.exadel.sandbox.team2.configuration.TelegramProperties;
import com.exadel.sandbox.team2.dto.TelegramFileResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class TelegramFileService {

    RestTemplate restTemplate;
    TelegramProperties telegramProperties;

    public boolean sendDocument(String chatId, String filePath){
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("document", new FileSystemResource(filePath));
        parameters.add("chat_id",chatId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "multipart/form-data");
        headers.set("Accept", "text/plain");

        ResponseEntity<TelegramFileResponse> result = restTemplate.postForEntity(
                new StringBuilder().append(telegramProperties.getApi().getBase().getPath()).append(telegramProperties.getBot().getToken()).append("/sendDocument").toString(),
                new HttpEntity<>(parameters, headers),
                TelegramFileResponse.class);

        return result.getStatusCode() == HttpStatus.OK && Optional.ofNullable(result.getBody()).map(TelegramFileResponse::isOk).orElse(false);
    }
}
