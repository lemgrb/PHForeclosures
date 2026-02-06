package com.phforeclosures.cucumber.glue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthCheckSteps {

    @LocalServerPort
    private int port;

    private int statusCode;
    private String responseBody;

    @Before
    public void setUp() {
        // Make port available to other step definitions at the start of each scenario
        SharedTestContext.setPort(port);
    }

    @When("the client calls {string}")
    public void the_client_calls_api_health(String path) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("http://localhost:" + port + path);
            httpClient.execute(request, response -> {
                statusCode = response.getCode();
                HttpEntity entity = response.getEntity();
                responseBody = EntityUtils.toString(entity);
                
                // Share status and response with other step definitions
                SharedTestContext.setStatusCode(statusCode);
                SharedTestContext.setResponseBody(responseBody);
                
                return null;
            });
        }
    }

    @Then("the client receives status code {int}")
    public void the_client_receives_status_code(Integer expectedStatusCode) {
        int actualStatusCode = SharedTestContext.getStatusCode();
        assertEquals(expectedStatusCode.intValue(), actualStatusCode);
    }

    @Then("the client receives server status is UP")
    public void the_client_receives_server_status_is_up() {
        String actualResponseBody = SharedTestContext.getResponseBody();
        assertEquals("UP", actualResponseBody);
    }
}
