package com.r00ta.telematics.platform;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class scoringTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/enrich")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}