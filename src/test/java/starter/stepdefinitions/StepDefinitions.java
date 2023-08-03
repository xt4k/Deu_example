package starter.stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.actions.CommonCalls;
import starter.assertions.ResponseVerification;

import static starter.enums.Const.USER_NAME;

public class StepDefinitions {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseVerification.class);

    @Steps
    public ResponseVerification responseVerification;

    @Steps
    public CommonCalls actions;

    /********************************* WHEN *********************************/

    @When("^I am searching for (.*)$")
    public void searchProducts(String item) {
        actions.searchTestProducts(item);
    }

    @When("I do search for product {string}")
    public void searchProduct(String product) {
        actions.searchTestProducts(product);
    }

    @When("I am searching without {string}")
    public void searchWithoutCriteria(String item) {
        actions.searchTestProducts("");
    }

    @When("an authorized user call endpoint {string}")
    public void authorizedUserCall(String endPoint) {
        actions.authorizedCall(endPoint);
    }

    @When("an unauthorized user call {string}")
    public void unauthorizedUserCall(String endPoint) {
        actions.unauthorizedCall(endPoint);
    }

    /********************************* THEN *********************************/

    @Then("get unauthorized error message")
    public void getUnauthorizedErrorMessage() {
        responseVerification.unauthorized();
    }

    @Then("does not see the results")
    public void noResults() {
        responseVerification.returnError();
    }

    @Then("search result is successful")
    public void searchResultIsSuccessful() {
        responseVerification.success();
    }

    @Then("verify the product list should not be empty in Search results")
    public void theProductShouldBeDisplayedInSearchResults() {
        responseVerification.responseShouldNotBeEmptyList();
    }

    @Then("search result return not found error")
    public void notFoundErrorShouldBeDisplayedInSearchResult() {
        responseVerification.notFound();
    }

    @Then("verify not authenticated error should be displayed in search result")
    public void notAuthenticatedErrDisplayedInSearchResult() {
        responseVerification.notAuthenticated();
    }

    @Then("^search result should contain (.*)$")
    public void productShouldBePresentInSearchResults(String product) {
        responseVerification.verifyProductInResponseResult(product);
    }

    @Then("all product in search results contains {string} in {string}")
    public void allProductInSearchResultContain(String type,String title) throws JsonProcessingException {
        responseVerification.allProductTitlesContains(type,title);
    }

    /********************************* AND *********************************/
    @And("the object structure should match to specification defined in {string}")
    public void schemaMatchWithSpecification(String spec) {
        responseVerification.verifyResponseSchema(spec);
    }

    @And("response field {string} has value {string}")
    public void responseFieldHasValue(String field, String value) {
        responseVerification.fieldHasValue(field,value);
    }

    @And("response field {string} value is about {string}")
    public void responseFieldIsAbout(String field, String value) {
        responseVerification.fieldHasApproximatelyValue(field,value);
    }

    @And("account contains user name")
    public void accountContainsUserName() throws JsonProcessingException {
        responseVerification.accountFieldContain(USER_NAME.getValue());
    }
}