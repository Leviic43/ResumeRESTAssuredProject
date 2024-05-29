package org.project.api.controllers;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.project.api.controllers.uris.Uris;
import org.project.api.payloads.post_create_user.request.PostCreateUserRequest;

import static io.qameta.allure.Allure.step;

public class UsersController extends BaseApiController {

    @Step("Получить страницу с пользователями по номеру {0}")
    public Response getUsersByPageNumber(int pageNumber) {
        return getBaseRequestSpec()
                .basePath(Uris.USERS)
                .queryParam("page", pageNumber)
                .when()
                .get();
    }

    @Step("Создать пользователя")
    public Response postCreateEntity(String body) {
        return step("API. Выполнить запрос",
                () -> getBaseRequestSpec()
                        .basePath(Uris.USERS)
                        .body(body)
                        .when()
                        .post()
        );
    }

    @Step("Создать пользователя")
    public Response postCreateEntity(PostCreateUserRequest body) {
        return step("API. Выполнить запрос",
                () -> getBaseRequestSpec()
                        .basePath(Uris.USERS)
                        .body(body)
                        .when()
                        .post()
        );

    }
}
