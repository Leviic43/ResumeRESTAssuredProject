package org.project.api.assertions.api;

import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.SoftAssertions;

public class AssertResponse extends AbstractAssert<AssertResponse, Response> {

    //наследуемся от AbstractAssert для написания своих проверок

    public AssertResponse(Response response) {
        super(response, AssertResponse.class);
    }

    private static final SoftAssertions softAssertions = new SoftAssertions();

    public AssertResponse checkStatusCode(int expectedCode) {
        if (actual.statusCode() != expectedCode) {
            softAssertions.collectAssertionError(failure("Expected code %s, but was %s"
                    .formatted(expectedCode, actual.statusCode()))); //собираем ошибки в коллекцию
        }
        return this;
    }


    public AssertResponse checkStringByPath(String path, String value) {
        if (!actual.jsonPath().getString(path).equals(value)) {
            softAssertions.collectAssertionError(failure("Value %s not found on path %s".formatted(value, path)));
        }
        return this;
    }

    public void assertAll() {
        softAssertions.assertAll();
    }

    public static AssertResponse assertThat(Response actual) {
        return new AssertResponse(actual);
    }


}
