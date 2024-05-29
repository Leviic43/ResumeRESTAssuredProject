package org.project.api.controllers;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseApiController {

    private static final String BASE_URI = "https://reqres.in/api";

    protected RequestSpecification getBaseRequestSpec() {
        RestAssuredConfig config = RestAssuredConfig.config()
                .httpClient
                        (
                                HttpClientConfig.httpClientConfig()
                                        .setParam("http.connection.timeout", 6000)
                        );
        return RestAssured.given()
                .config(config)
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .filter(new AllureRestAssured())
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}
