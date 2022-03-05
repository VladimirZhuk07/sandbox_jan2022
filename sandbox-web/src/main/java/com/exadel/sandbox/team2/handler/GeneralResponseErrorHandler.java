package com.exadel.sandbox.team2.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Slf4j
public class GeneralResponseErrorHandler implements ResponseErrorHandler {


  @Override
  public boolean hasError(final ClientHttpResponse response) throws IOException {
    return (
      response.getStatusCode().series() == CLIENT_ERROR
        || response.getStatusCode().series() == SERVER_ERROR);
  }

  @Override
  public void handleError(final ClientHttpResponse response) throws IOException {
    log.info(response.getStatusText());
  }

}
