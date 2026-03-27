package com.dishant.person.api;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class PersonApiApplicationTests {

    @Autowired
    private RestTestClient restTestClient;

    @Test
    public void getHealthCheck() {
        EntityExchangeResult<Void> response = this.restTestClient.method(HttpMethod.GET)
            .uri("/management/health")
            .exchange()
            .returnResult(Void.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    }
}
