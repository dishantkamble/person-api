package com.dishant.person.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonApiApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getHealthCheck() {
        ResponseEntity<Void> response = this.restTemplate.getForEntity("/management/health", Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}