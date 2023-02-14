package com.capitole.exam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExamApplicationTest {

  @LocalServerPort
  private int port;

  @BeforeAll
  public static void setup() {
    RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
        new ObjectMapperConfig().jackson2ObjectMapperFactory((type, s) -> new ObjectMapper()
            .setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy())
            .registerModule(new JavaTimeModule())));
  }

  protected ValidatableResponse post(String url, Object dto) {
    return with()
        .body(dto)
        .when()
        .post(url)
        .then();
  }

  private RequestSpecification with() {
    return RestAssured
        .with()
        .port(port)
        .contentType(ContentType.JSON);
  }
}
