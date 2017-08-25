package app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HttpClientService {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientService.class);

    @Autowired
    private RestTemplate restTemplate;

    public HttpResponse get(String url, HttpHeaders headers, Map<String, String> urlParam) throws IOException {
        HttpEntity httpEntityHeaders = new HttpEntity(headers);
        ResponseEntity<String> entity = null;

        if (urlParam == null) {
            entity = restTemplate.exchange(url, HttpMethod.GET, httpEntityHeaders, String.class);
        } else {
            entity = restTemplate.exchange(url, HttpMethod.GET, httpEntityHeaders, String.class, urlParam);
        }
        return new HttpResponse(entity);
    }

    public HttpResponse post(String url, HttpHeaders postHeader, MultiValueMap<String, String> form) throws IOException {
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(form, postHeader);
        ResponseEntity<String> entity = restTemplate.postForEntity(url, requestEntity, String.class);
        return new HttpResponse(entity);
    }

    public HttpResponse postJson(String url, String json) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return new HttpResponse(entity);
    }

    public class HttpResponse {
        ResponseEntity entity;
        Map<String, Object> body;

        public HttpResponse(ResponseEntity entity) throws IOException {
            this.entity = entity;
            List<String> list = entity.getHeaders().get("Content-type");
            String contentType = list != null ? list.get(0) : "";
            if (contentType.contains("application/json")) {
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
                body = mapper.readValue(entity.getBody().toString(), typeRef);
            } else if (contentType.contains("text/html")) {
                body = new HashMap<String, Object>();
                body.put("document", Jsoup.parse(entity.getBody().toString()));
            } else {
                body = new HashMap<String, Object>();
            }
        }

        public ResponseEntity getEntity() {
            return entity;
        }

        public Map<String, Object> getBody() {
            return body;
        }

        public int getStatusCodeValue() {
            return entity.getStatusCodeValue();
        }

        public Object get(String key) {
            return body.get(key);
        }

    }

}
