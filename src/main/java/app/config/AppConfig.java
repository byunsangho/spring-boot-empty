package app.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by sangho.byun on 2017/08/25.
 */
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() throws Exception {
//        SSLContext sslContext = SSLContexts.custom()
//                .loadTrustMaterial(new TrustSelfSignedStrategy()).build();
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setSSLContext(sslContext)
//                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
//                .build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory =
//                new HttpComponentsClientHttpRequestFactory();
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        ((HttpComponentsClientHttpRequestFactory) restTemplate
//                .getRequestFactory()).setHttpClient(httpClient);
//        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(
                CookieSpecs.STANDARD).build()).build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

}
