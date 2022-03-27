package com.exadel.sandbox.team2.configuration;

import com.exadel.sandbox.team2.handler.GeneralResponseErrorHandler;
import com.exadel.sandbox.team2.handler.utils.TelegramUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Component
public class BeanConfiguration {

  @Bean
  public DefaultBotOptions defaultBotOptions(){
    DefaultBotOptions options=new DefaultBotOptions();
    options.setProxyType(DefaultBotOptions.ProxyType.HTTP);
    options.setProxyHost("10.9.0.29");
    options.setProxyPort(42897);
    return options;
  }

  @Bean
  public RestTemplate restTemplate(final RestTemplateBuilder builder) {
    return builder
      //.requestFactory(() -> new BufferingClientHttpRequestFactory(getClientHttpRequestFactory()))
      .errorHandler(new GeneralResponseErrorHandler())
      .build();
  }

  @Bean
  public TelegramUtils telegramUtils(){
    return new TelegramUtils();
  }

  private ClientHttpRequestFactory getClientHttpRequestFactory() {
    SSLContext sslContext = null;
    try {
      sslContext = SSLContexts
        .custom()
        .loadTrustMaterial((chain, authType) -> true)
        .build();
    } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
      e.printStackTrace();
    }
    final RequestConfig.Builder configBuilder = RequestConfig.custom()
      .setConnectTimeout(30000)
      .setConnectionRequestTimeout(30000)
      .setSocketTimeout(30000)
      .setProxy(new HttpHost("10.9.0.29", 42897));

    final RequestConfig config = configBuilder.build();
    final CloseableHttpClient client = HttpClientBuilder
      .create()
      .setSSLContext(sslContext)
      .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
      .setDefaultRequestConfig(config)
      .build();
    return new HttpComponentsClientHttpRequestFactory(client);
  }
}
