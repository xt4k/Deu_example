package starter.assertions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.pojo.Account;
import starter.pojo.Drink;

import java.util.HashMap;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.lang.String.format;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static net.serenitybdd.rest.SerenityRest.then;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.within;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static starter.enums.Const.*;


public class ResponseVerification {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseVerification.class);

    @And("Verify response schema with {string}")
    public void verifyResponseSchema(String schemaJson) {
        then().body(matchesJsonSchemaInClasspath(SCHEMA_PATH.getValue() + schemaJson));
    }

    @Then("response with code 200 (Successful)")
    public void success() {
        then().statusCode(200);
    }

    @Then("Response error with code 404 (Not Found)")
    public void notFound() {
        then()
                .statusCode(404)
                .body("detail.error", is(true));
    }

    @Then("Response error with code 401 (Not Authorized)")
    public void notAuthenticated() {
        then()
                .statusCode(401)
                .body("detail", is("Not authenticated"));
    }

    @Then("Verify that product {string} is present in response")
    public void verifyProductInResponseResult(String product) {
        List<HashMap<String, Object>> products = lastResponse().jsonPath().getList("$");
        assertThat(products).anyMatch(e -> e.get("title").toString().contains(product));
    }

    @And("JSON object contains {string}")
    public void accountFieldContain(String value) throws JsonProcessingException {
        String content = then()
                .extract()
                .jsonPath()
                .prettyPrint();
        Account account = new ObjectMapper().readValue(content, new TypeReference<Account>() { });
        assertThat(account.getName()).as("Verify account name field").contains(value);
    }

    @Then("Error message for unauthorized access")
    public void unauthorized() {
        then()
                .statusCode(401)
                .body(UNAUTHORIZED.getField(), is(UNAUTHORIZED.getValue()));
    }

    @Then("Each product in search results contains {string}")
    public void allProductTitlesContains(String type, String title) throws JsonProcessingException {
        String content = then()
                .extract()
                .jsonPath()
                .prettyPrint();
        List<Drink> items = new ObjectMapper().readValue(content, new TypeReference<List<Drink>>() {});
        assertThat(items)
                .as(format("every '%s' field of element in list should contain '%s'", title, type))
                .allMatch(e -> e.title.toLowerCase().contains(type.toLowerCase()));
    }

    @Then("Response field {string} has value {string}")
    public void fieldHasValue(String field, String value) {
        then().body(field, is(value));
    }

    @And("Response field {string} value is about {string}")
    public void fieldHasApproximatelyValue(String field, String value) {
        Integer actual = then().extract().body().path(field);
        assertThat(actual).isCloseTo(Integer.parseInt(value), within(3));
    }

    @Then("Return error result")
    public void returnError() {
        then().body(DETAIL_ERROR.getField(), equalTo(true));
    }

    @Then("Verify that response list isn't an empty list")
    public void responseShouldNotBeEmptyList() {
        List<HashMap<String, Object>> res = lastResponse().getBody().jsonPath().getList("$");
       // LOG.info("Response list size is {}", res.size());
        assertThat("Response list size is 0", res.size() == 0);
    }
}