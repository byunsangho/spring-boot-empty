package app.service;/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpClientServicelTest {

    @Autowired
    private HttpClientService service;

    @Test
    public void test01() throws IOException {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.ALL));

        HttpClientService.HttpResponse response = service.get("https://www.google.co.jp", requestHeaders, null);
        System.out.println(response.getStatusCodeValue());
        System.out.println(response.get("document"));
    }
}
