package ru.netology.integrationtesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileIntegrationTest {
    private static final GenericContainer<?> DEV_APP = new GenericContainer<>(DockerImageName.parse("devapp:latest"))
            .withExposedPorts(8080);
    private static final GenericContainer<?> PROD_APP = new GenericContainer<>(DockerImageName.parse("prodapp:latest"))
            .withExposedPorts(8081);

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    static void setUp() {
        DEV_APP.start();
        PROD_APP.start();
    }

    @AfterAll
    static void tearDown() {
        PROD_APP.stop();
        DEV_APP.stop();
    }

    @Test
    void devImageReturnsDevProfile() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + DEV_APP.getMappedPort(8080) + "/profile", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Current profile is dev", response.getBody());
    }

    @Test
    void prodImageReturnsProductionProfile() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + PROD_APP.getMappedPort(8081) + "/profile", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Current profile is production", response.getBody());
    }
}

