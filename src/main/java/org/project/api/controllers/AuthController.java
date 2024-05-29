package org.project.api.controllers;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.project.api.controllers.uris.Uris;
import org.project.api.payloads.post_register_user.request.PostRegister;

import static io.qameta.allure.Allure.step;

public class AuthController extends BaseApiController {

    @Step("Регистрация пользователя")
    public Response postRegisterEntity(String body) {
        return step("API. Выполнить запрос",
                () -> getBaseRequestSpec()
                        .basePath(Uris.REGISTER)
                        .body(body)
                        .when()
                        .post()
        );
    }

    @Step("Регистрация пользователя")
    public Response postRegisterEntity(PostRegister body) {
        return step("API. Выполнить запрос",
                () -> getBaseRequestSpec()
                        .basePath(Uris.REGISTER)
                        .body(body)
                        .when()
                        .post()
        );

    }
}
