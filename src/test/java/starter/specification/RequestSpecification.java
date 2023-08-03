package starter.specification;

import io.restassured.builder.RequestSpecBuilder;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;

import static io.restassured.http.ContentType.JSON;
import static net.serenitybdd.core.environment.EnvironmentSpecificConfiguration.from;
import static starter.enums.Const.API_PATH;

public class RequestSpecification {

    public static io.restassured.specification.RequestSpecification searchReqSpec() {
        EnvironmentVariables environmentVariables = Injectors.getInjector()
                .getInstance(EnvironmentVariables.class);

        return new RequestSpecBuilder()
                .setBaseUri(from(environmentVariables).getProperty("baseUrl"))
                .setBasePath(API_PATH.getField())
                .setContentType(JSON)
                .build();
    }
}