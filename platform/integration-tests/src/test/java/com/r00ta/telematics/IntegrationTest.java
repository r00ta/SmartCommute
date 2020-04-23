package com.r00ta.telematics;

import java.time.DayOfWeek;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.models.AuthenticationResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.wildfly.common.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static String jwtToken;
    private static String userId;
    private static String routeId;

    @Test
    @Order(1)
    public void createUser() {

        System.out.println("TEST 1");
        String body = new JsonObject().put("birthDay", "2020-04-22").put("email", "pippo@gmail.com").put("name", "pippo").put("passwordHash", "pass").put("surename", "ciccio").toString();

        Response response1 = given().contentType(ContentType.JSON).body(body)
                .when().post("http://localhost:1339/users").thenReturn();
        response1.prettyPrint();
//          .then()
//             .statusCode(200);
        System.out.println("TEST 1 end");

        Response response = given().contentType(ContentType.JSON).body("{\"size\": 10000, \"query\" : { \"match_all\" : {} }}").when().post("http://localhost:9200/userindex/_search/").thenReturn();
        response.prettyPrint();
    }

    @Test
    @Order(2)
    public void authenticateUser() throws InterruptedException {
        System.out.println("TEST 2");
        String body = new JsonObject().put("email", "pippo@gmail.com").put("password", "pass").toString();

        AuthenticationResponse response = null;
        for(int i = 0; i <= 15; i++){
            Response post = given().contentType(ContentType.JSON).body(body)
                    .when().post("http://localhost:1339/users/auth");

            if (post.statusCode() == 200){
                response =  post.then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", AuthenticationResponse.class);
                break;
            }
            Thread.sleep(1000);
        }

        userId = response.userId;
        jwtToken = response.jwtBearer;
        System.out.println(userId);
        System.out.println(jwtToken);

        Assertions.assertNotNull(response.userId);
        Assertions.assertNotNull(response.jwtBearer);
    }

    @Test
    @Order(3)
    public void authenticationTest() {
        System.out.println("TEST 3");

        given().header("Authorization", "Bearer " + jwtToken)
                .when().get("http://localhost:1339/users/" + userId).then().statusCode(200);
    }

    @Test
    @Order(4)
    public void createRoute() {
        System.out.println("TEST 4");

        String body = new JsonObject()
                .put("availableAsPassenger", true)
                .put("days", new JsonArray().add("FRIDAY"))
                .put("endPositionUserLabel", "office")
                .put("startPositionUserLabel", "home")
                .put("driverPreferences", new JsonObject().put("availableAsDriver", true).put("flexibility", 500).put("onTheRouteOption", true).put("radiusStartPoint", 500))
                .toString();

        String routeId = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken).body(body)
                .when().post("http://localhost:1339/users/" + userId + "/routes").then().extract().jsonPath().getString("routeId");

        Assertions.assertNotNull(routeId);
        this.routeId = routeId;
    }
}