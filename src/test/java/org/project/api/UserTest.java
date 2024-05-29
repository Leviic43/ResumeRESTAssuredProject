package org.project.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.project.api.assertions.api.AssertResponse;
import org.project.api.controllers.UsersController;
import org.project.api.payloads.get_users.GetUsersPageResponse;
import org.project.api.payloads.post_create_user.request.PostCreateUserRequest;
import org.project.api.payloads.post_create_user.response.PostCreateUserResponse;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("API")
@Feature("Эндпоинт User")
public class UserTest {

    private static final UsersController USERS_CONTROLLER = new UsersController(); //инстанцируем контроллер
    private static final SoftAssertions softly = new SoftAssertions(); //soft assert для мягких проверок //todo вынести в общий класс родитель?

    private static final String NAME = "anton";
    private static final String JOB = "tester";

    @Test
    @Tag("smoke")
    @Story("Get User")
    @DisplayName("Стандартный тест")
    public void getUsersPageTest() {

        Response response = USERS_CONTROLLER.getUsersByPageNumber(2);
        response.then().statusCode(200);
        assertThat(response.statusCode()).isEqualTo(200); //hard assert, если статус код не 200, то в остальных проверках нет смысла
        GetUsersPageResponse getUsersResponse = response.as(GetUsersPageResponse.class); // десериализация в модель

        softly.assertThat(response.getBody().jsonPath().getInt("page")).isEqualTo(2);// валидация response

        softly.assertThat(getUsersResponse.getPage())
                .isEqualTo(2); // валидация с применением модели

        softly.assertAll();
    }


    @SneakyThrows
    @Test
    @Story("Get User")
    @DisplayName("Использование Map и ObjectMapper")
    public void getUsersMapPageTest() {
        int id = 10;
        Response response = USERS_CONTROLLER.getUsersByPageNumber(2);
        response.then().statusCode(200);
        assertThat(response.statusCode()).isEqualTo(200);

        ObjectMapper objectMapper = new ObjectMapper(); //используется для сериализации/десериализации

        String responseBody = objectMapper.writeValueAsString(response.getBody().jsonPath().get()); //получить тело ответа как строку
        Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<>() {
        }); // представить ответ как Map

        softly.assertThat(responseMap.get("page")).isEqualTo(2);

        @SuppressWarnings("unchecked") //подавить ворнинг (чтоб не отсвечивал)
        List<Map<String, Object>> dataMap = (List<Map<String, Object>>) responseMap.get("data"); //Представить массив data как Лист мап

        boolean isHasId = dataMap.stream()
                .anyMatch(e -> e.get("id").equals(id));

        softly.assertThat(isHasId)
                .withFailMessage("Id %s отсутствует в массиве data".formatted(id)) // сообщение при ошибке (заменяет дефолтное)
                .isTrue();
        softly.assertAll();
    }

    @Test
    @Story("Get User")
    @DisplayName("Какой-то")
    public void getOneUser() {
        int id = 4;
        Response response = USERS_CONTROLLER.getUsersByPageNumber(1);
        response.then().statusCode(200);
        assertThat(response.statusCode()).isEqualTo(200);

        GetUsersPageResponse getUserResponse = response.as(GetUsersPageResponse.class);

        getUserResponse.getData().stream()
                .filter(e -> e.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new AssertionError("Id %s отсутствует в массиве data".formatted(id)));
    }

    @SneakyThrows
    @Test
    @Story("Post User")
    @DisplayName("Создание пользователя из файла")
    public void createUserFromJsonFile() {
        File file = new File(Resources.getResource("testData/user.json").getFile());
        String json = Files.readString(file.toPath());

        Response response = USERS_CONTROLLER.postCreateEntity(json);
        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("name"))
                .isEqualTo("tester");
    }


    @Test
    @Story("Post User")
    @DisplayName("Использование собственного Assert класса")
    public void createUserWithCustomAssertionsTest() {


        PostCreateUserRequest request = PostCreateUserRequest // создание модели запроса
                .builder() // ключевое слово, после него нужно начинать заполнять поля
                .name(NAME)
                .job(JOB)
                .build(); // завершение заполнения
        Response response = USERS_CONTROLLER.postCreateEntity(request); // создание сущности

        AssertResponse.assertThat(response) //собственный Ассерт класс
                .checkStatusCode(201)
                .checkStringByPath("name", NAME)
                .checkStringByPath("job", JOB)
                .assertAll();
    }

    @Test
    @Story("Post User")
    @DisplayName("Валидация модели")
    public void createUserWithPropertiesValidations() {
        PostCreateUserRequest postCreateUserRequest = new PostCreateUserRequest();
        postCreateUserRequest.setName(NAME);
        postCreateUserRequest.setJob(JOB);

        Response response = USERS_CONTROLLER.postCreateEntity(postCreateUserRequest);

        assertThat(response.statusCode()).isEqualTo(201);

        PostCreateUserResponse postCreateUserResponse = response.as(PostCreateUserResponse.class);

        softly.assertThat(postCreateUserResponse)
                .hasFieldOrPropertyWithValue("name", NAME)
                .hasFieldOrPropertyWithValue("job", JOB);
        softly.assertAll();
    }


}