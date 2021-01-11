package com.smallyuan.labs.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebFlux
@AutoConfigureWebTestClient
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testList() {
        webTestClient.get().uri("/users/list")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[{\"id\":1,\"name\":\"yuan\"}]");
    }

    @Test
    public void testGet() {
        webTestClient.get().uri("/users/get?id=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("{\"id\":1,\"name\":\"yuan\"}");
    }

}
