package starter.actions;

import io.restassured.http.Header;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import starter.specification.RequestSpecification;

import static net.serenitybdd.rest.SerenityRest.given;
import static starter.enums.Const.*;

public class CommonCalls {
    private Header header= new Header(ACCEPT_HEADER.getField(), ACCEPT_HEADER.getValue());

    @Step("Get response from search product {string}")
    public Response searchTestProducts(String product) {
        return given()
                .log()
                .uri()
                .spec(RequestSpecification.searchReqSpec())
                .get(API_PATH.getValue()+"/"+product);
    }

    @Step("Authorized user call {string}")
    public void authorizedCall(String endPoint) {
        given()
                .header(header)
                .header(AUTH_TOKEN.getField(), AUTH_TOKEN.getValue())
                .get(endPoint);
    }

    @Step("UnAuthorized user call {string}")
    public void unauthorizedCall(String endPoint) {
        given()
                .header(header)
                .get(endPoint);
    }
}
