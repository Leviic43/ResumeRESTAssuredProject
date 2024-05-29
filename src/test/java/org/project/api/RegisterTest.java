package org.project.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.project.api.controllers.AuthController;
import org.project.api.payloads.post_register_user.request.PostRegister;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterTest {
    private static final AuthController AUTH_CONTROLLER = new AuthController();

    private static final String EMAIL = "eve.holt@reqres.in";
    private static final String PASSWORD = "pistol";


    @Test
    @DisplayName("Создание тела запроса с помощью string")
    public void createUserWithStringBodyTest() {


        String json = "{\n" +
                "    \"email\": \"%s\",\n".formatted(EMAIL) +
                "    \"password\": \"%s\"\n".formatted(PASSWORD) +
                "}";

        Response response = AUTH_CONTROLLER.postRegisterEntity(json);
        response.then().statusCode(200); // проверка статус кода через ValidatableResponse
        assertThat(response.jsonPath().getString("token")).isNotNull();

    }


    @Test
    @Tag("smoke")
    @DisplayName("Создание тела запроса из pojo класса")
    public void createUserWithPojoTest() {


        PostRegister request = new PostRegister();
        request.setEmail(EMAIL);
        request.setPassword(PASSWORD);

        Response response = AUTH_CONTROLLER.postRegisterEntity(request);
        assertThat(response.statusCode())
                .isEqualTo(200);

    }
}
