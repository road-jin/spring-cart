package cart.product.web;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import cart.product.web.dto.ProductCreateRequest;
import cart.product.web.dto.ProductUpdateRequest;
import io.restassured.RestAssured;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("상품 목록을 조회 할 때 상품이 있으면 모든 상품들을 반환한다.")
    @Test
    void getProducts() {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/products")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("상품 생성 할 때 이름, 이미지 주소, 가격을 입력하면 상품이 생성되고 반환한다.")
    @Test
    void create() {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new ProductCreateRequest("샌드위치", "http://localhost:8080/image/sandwich.png", 10000L))
            .when()
            .post("/products")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("상품 생성 할 때 이름이 없거나 20자리가 초과된 경우 에러메시지를 반환한다.")
    @ParameterizedTest
    @MethodSource("provideValueForValidatorName")
    void createFalse(String name) {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new ProductCreateRequest(name, "http://localhost:8080/image/sandwich.png", 10000L))
            .when()
            .post("/products")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private static Stream<Arguments> provideValueForValidatorName() {
        return Stream.of(Arguments.of(""),
            Arguments.of(" "),
            Arguments.of("1".repeat(21)));
    }

    @DisplayName("상품 생성 할 때 이미지 주소가 없거나 1000자리가 초과된 경우 에러메시지를 반환한다.")
    @ParameterizedTest
    @MethodSource("provideValueForValidatorImage")
    void createFalse2(String image) {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new ProductCreateRequest("샌드위치", image, 10000L))
            .when()
            .post("/products")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private static Stream<Arguments> provideValueForValidatorImage() {
        return Stream.of(Arguments.of(""),
            Arguments.of(" "),
            Arguments.of("1".repeat(1001)));
    }

    @DisplayName("상품 생성 할 때 가격이 0원 미만인 경우 에러메시지를 반환한다.")
    @Test
    void createFalse3() {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new ProductCreateRequest("샌드위치", "http://localhost:8080/image/sandwich.png", -1L))
            .when()
            .post("/products")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품 수정 할 때 이름, 이미지 주소, 가격을 입력하면 상품이 수정되고 반환한다.")
    @Test
    void update() {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new ProductUpdateRequest(null, "샌드위치", "http://localhost:8080/image/sandwich.png", 10000L))
            .when()
            .put("/products/1")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("상품 수정 할 때 이름이 없거나 20자리가 초과된 경우 에러메시지를 반환한다.")
    @ParameterizedTest
    @MethodSource("provideValueForValidatorName")
    void updateFalse(String name) {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new ProductUpdateRequest(null, name, "http://localhost:8080/image/sandwich.png", 10000L))
            .when()
            .put("/products/1")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품 수정 할 때 이미지 주소가 없거나 1000자리가 초과된 경우 에러메시지를 반환한다.")
    @ParameterizedTest
    @MethodSource("provideValueForValidatorImage")
    void updateFalse2(String image) {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new ProductUpdateRequest(null, "샌드위치", image, 10000L))
            .when()
            .put("/products/1")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품 수정 할 때 가격이 0원 미만인 경우 에러메시지를 반환한다.")
    @Test
    void updateFalse3() {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new ProductUpdateRequest(null, "샌드위치", "http://localhost:8080/image/sandwich.png", -1L))
            .when()
            .put("/products/1")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품 수정 할 때 해당 상품이 없는 경우 에러메시지를 반환한다.")
    @Test
    void updateFalse4() {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new ProductUpdateRequest(null, "샌드위치", "http://localhost:8080/image/sandwich.png", 10000L))
            .when()
            .put("/products/10")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("상품 삭제 할 때 상품 아이디를 입력하면 상품이 삭제된다.")
    @Test
    void delete() {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .delete("/products/2")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품 삭제 할 때 해당 상품이 없는 경우 에러메시지를 반환한다.")
    @Test
    void deleteFalse() {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .delete("/products/10")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
