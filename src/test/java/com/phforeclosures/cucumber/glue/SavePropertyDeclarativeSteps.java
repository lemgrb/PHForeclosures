package com.phforeclosures.cucumber.glue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phforeclosures.model.PropertyRequest;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SavePropertyDeclarativeSteps {

    private ObjectMapper objectMapper = new ObjectMapper();
    private List<JsonNode> savedProperties = new ArrayList<>();
    private PropertyRequest lastPropertyRequest;

    private int getPort() {
        return SharedTestContext.getPort();
    }

    private void postProperty(String jsonPayload) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("http://localhost:" + getPort() + "/api/properties");
            request.setHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(jsonPayload, ContentType.APPLICATION_JSON));

            httpClient.execute(request, response -> {
                int statusCode = response.getCode();
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                SharedTestContext.setStatusCode(statusCode);
                SharedTestContext.setResponseBody(responseBody);

                if (statusCode == 200 && responseBody != null) {
                    try {
                        JsonNode savedProperty = objectMapper.readTree(responseBody);
                        savedProperties.add(savedProperty);
                    } catch (Exception e) {
                        // Ignore
                    }
                }
                return null;
            });
        }
    }

    private String buildJsonPayload(Map<String, String> propertyData) {
        lastPropertyRequest = new PropertyRequest();
        StringBuilder json = new StringBuilder("{");

        if (propertyData.containsKey("title")) {
            String title = propertyData.get("title");
            lastPropertyRequest.setTitle(title);
            json.append("\"title\": \"").append(title).append("\",");
        }

        if (propertyData.containsKey("description")) {
            String description = propertyData.get("description");
            lastPropertyRequest.setDescription(description);
            if (description != null && !description.isEmpty()) {
                json.append("\"description\": \"").append(description).append("\",");
            } else {
                json.append("\"description\": null,");
            }
        }

        if (propertyData.containsKey("price")) {
            String price = propertyData.get("price");
            lastPropertyRequest.setPrice(new BigDecimal(price));
            json.append("\"price\": ").append(price).append(",");
        }

        if (propertyData.containsKey("address")) {
            String address = propertyData.get("address");
            lastPropertyRequest.setAddress(address);
            json.append("\"address\": \"").append(address).append("\",");
        }

        if (propertyData.containsKey("imageUrls")) {
            String imageUrls = propertyData.get("imageUrls");
            // This is a simplification. A real implementation would parse the string into a list
            json.append("\"imageUrls\": ").append(imageUrls).append(",");
        }

        if (json.charAt(json.length() - 1) == ',') {
            json.setLength(json.length() - 1);
        }

        json.append("}");
        return json.toString();
    }

    @Given("a user wants to list a new property")
    public void a_user_wants_to_list_a_new_property() {
        // This step can be used for setup if needed, like clearing previous contexts.
        savedProperties.clear();
    }

    @Given("a user wants to list multiple properties")
    public void a_user_wants_to_list_multiple_properties() {
        savedProperties.clear();
    }

    @When("the user provides the following details for the listing:")
    public void the_user_provides_the_following_details_for_the_listing(DataTable dataTable) throws IOException {
        Map<String, String> propertyData = dataTable.asMap(String.class, String.class);
        String jsonPayload = buildJsonPayload(propertyData);
        postProperty(jsonPayload);
    }

    @Then("the property is successfully listed")
    public void the_property_is_successfully_listed() {
        assertEquals(200, SharedTestContext.getStatusCode());
    }

    @Then("the new listing is assigned a unique identifier")
    public void the_new_listing_is_assigned_a_unique_identifier() throws IOException {
        String responseBody = SharedTestContext.getResponseBody();
        assertNotNull(responseBody, "Response body should not be null");
        JsonNode response = objectMapper.readTree(responseBody);
        assertTrue(response.has("id") && response.get("id").asLong() > 0, "Should have a positive ID");
    }

    @Then("the listing's details are correctly displayed")
    public void the_listing_s_details_are_correctly_displayed() throws IOException {
        String responseBody = SharedTestContext.getResponseBody();
        JsonNode response = objectMapper.readTree(responseBody);
        assertEquals(lastPropertyRequest.getTitle(), response.get("title").asText());
        assertEquals(lastPropertyRequest.getDescription(), response.get("description").asText());
        assertEquals(0, lastPropertyRequest.getPrice().compareTo(new BigDecimal(response.get("price").asText())));
        assertEquals(lastPropertyRequest.getAddress(), response.get("address").asText());
    }

    @When("the user provides the following essential details for the listing:")
    public void the_user_provides_the_following_essential_details_for_the_listing(DataTable dataTable) throws IOException {
        Map<String, String> propertyData = dataTable.asMap(String.class, String.class);
        String jsonPayload = buildJsonPayload(propertyData);
        postProperty(jsonPayload);
    }

    @Then("the listing's essential details are correctly displayed")
    public void the_listing_s_essential_details_are_correctly_displayed() throws IOException {
        String responseBody = SharedTestContext.getResponseBody();
        JsonNode response = objectMapper.readTree(responseBody);
        assertEquals(lastPropertyRequest.getTitle(), response.get("title").asText());
        assertEquals(0, lastPropertyRequest.getPrice().compareTo(new BigDecimal(response.get("price").asText())));
        assertEquals(lastPropertyRequest.getAddress(), response.get("address").asText());
    }

    @When("the user provides multiple images for the listing:")
    public void the_user_provides_multiple_images_for_the_listing(DataTable dataTable) throws IOException {
        Map<String, String> propertyData = dataTable.asMap(String.class, String.class);
        String jsonPayload = buildJsonPayload(propertyData);
        postProperty(jsonPayload);
    }

    @Then("the listing includes all the provided images")
    public void the_listing_includes_all_the_provided_images() throws IOException {
        String responseBody = SharedTestContext.getResponseBody();
        JsonNode response = objectMapper.readTree(responseBody);
        JsonNode imageUrls = response.get("imageUrls");
        assertTrue(imageUrls.isArray() && imageUrls.size() == 3);
    }

    @When("the user provides a listing without a description:")
    public void the_user_provides_a_listing_without_a_description(DataTable dataTable) throws IOException {
        Map<String, String> propertyData = dataTable.asMap(String.class, String.class);
        String jsonPayload = buildJsonPayload(propertyData);
        postProperty(jsonPayload);
    }

    @Then("the listing is displayed with no description")
    public void the_listing_is_displayed_with_no_description() throws IOException {
        String responseBody = SharedTestContext.getResponseBody();
        JsonNode response = objectMapper.readTree(responseBody);
        assertTrue(response.get("description") == null || response.get("description").isNull() || response.get("description").asText().isEmpty());
    }

    @When("the user attempts to provide an invalid price for the listing")
    public void the_user_attempts_to_provide_an_invalid_price_for_the_listing() throws IOException {
        String jsonPayload = "{\"title\": \"Invalid Price\", \"price\": \"not-a-number\", \"address\": \"123 Invalid\"}";
        postProperty(jsonPayload);
    }

    @Then("the system rejects the listing due to a data format error")
    public void the_system_rejects_the_listing_due_to_a_data_format_error() {
        assertEquals(400, SharedTestContext.getStatusCode());
    }

    @When("the user lists a {string} for {double} at {string}")
    public void the_user_lists_a_for_at(String title, Double price, String address) throws IOException {
        String jsonPayload = String.format("{\"title\": \"%s\", \"price\": %f, \"address\": \"%s\"}", title, price, address);
        postProperty(jsonPayload);
    }

    @Then("both properties are successfully listed")
    public void both_properties_are_successfully_listed() {
        assertEquals(2, savedProperties.size());
    }

    @Then("each new listing is assigned a unique identifier")
    public void each_new_listing_is_assigned_a_unique_identifier() {
        Long firstId = savedProperties.get(0).get("id").asLong();
        Long secondId = savedProperties.get(1).get("id").asLong();
        assertNotEquals(firstId, secondId);
    }
}
