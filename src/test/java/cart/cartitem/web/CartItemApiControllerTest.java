package cart.cartitem.web;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import cart.cartitem.web.dto.CartItemAddRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartItemApiControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("사용자 정보를 헤더에 포함하고 보냈을 때 장바구니 상품을 조회하면 해당 사용자가 장바구니에 담긴 상품들을 반환한다.")
    @Test
    void getCartItems() {
        var result = given()
            .auth().preemptive().basic("a@a.com", "password1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new CartItemAddRequest(1L))
            .when()
            .get("/cart/items")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("사용자 정보를 헤더에 포함하지 않았을 때 장바구니 상품을 조회하면 에러를 반환한다.")
    @Test
    void getCartItemsFalse() {
        var result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new CartItemAddRequest(1L))
            .when()
            .get("/cart/items")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("불일치 하는 사용자 정보를 헤더에 포함하고 보냈을 때 장바구니 상품을 조회하면 에러를 반환한다.")
    @Test
    void getCartItemsFalse2() {
        var result = given()
            .auth().preemptive().basic("c@c.com", "password3")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new CartItemAddRequest(1L))
            .when()
            .get("/cart/items")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("사용자 정보를 헤더에 포함하고 상품 아이디를 보냈을 때 장바구니에 상품을 추가하면 추가된 상품을 반환한다.")
    @Test
    void add() {
        var result = given()
            .auth().preemptive().basic("a@a.com", "password1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new CartItemAddRequest(1L))
            .when()
            .post("/cart/items")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("사용자 정보를 헤더에 포함하고 상품 아이디를 보내지 않았을 때 장바구니에 상품을 추가하면 에러를 반환한다.")
    @Test
    void addFalse() {
        var result = given()
            .auth().preemptive().basic("a@a.com", "password1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new CartItemAddRequest())
            .when()
            .post("/cart/items")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("불일치 하는 사용자 정보를 헤더에 포함하고 상품 아이디를 보냈을 때 장바구니에 상품을 추가하면 에러를 반환한다.")
    @Test
    void addFalse2() {
        var result = given()
            .auth().preemptive().basic("a@a.com", "password2")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new CartItemAddRequest(1L))
            .when()
            .post("/cart/items")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("사용자 정보를 헤더에 포함하고 장바구니 아이디를 보냈을 때 장바구니에 상품을 제거하면 장바구니에 해당 상품이 제거된다.")
    @Test
    void remove() {
        given()
        .auth().preemptive().basic("a@a.com", "password1")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(new CartItemAddRequest(1L))
        .when().post("/cart/items");

        var result = given()
            .auth().preemptive().basic("a@a.com", "password1")
            .when()
            .delete("/cart/items/1")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("사용자 정보를 헤더에 포함하고 장바구니가 없는 아이디를 보냈을 때 장바구니에 상품을 제거하면 에러를 반환한다")
    @Test
    void removeFalse() {
        var result = given()
            .auth().preemptive().basic("a@a.com", "password1")
            .when()
            .delete("/cart/items/1")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("장바구니 소유자가 아닌 사용자 정보를 헤더에 포함하고 장바구니가 아이디를 보냈을 때 장바구니에 상품을 제거하면 에러를 반환한다")
    @Test
    void removeFalse2() {
        given()
            .auth().preemptive().basic("a@a.com", "password1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new CartItemAddRequest(1L))
            .when().post("/cart/items");

        var result = given()
            .auth().preemptive().basic("b@b.com", "password2")
            .when()
            .delete("/cart/items/1")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
