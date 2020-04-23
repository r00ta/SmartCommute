package com.r00ta.telematics;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.UUID;
import java.util.concurrent.Callable;

import javax.ws.rs.NotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.models.AuthenticationResponse;
import com.r00ta.telematics.models.AvailableLiveSessionsResponse;
import com.r00ta.telematics.models.LiveChunksResponse;
import com.r00ta.telematics.models.LiveSessionSummary;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static String email = UUID.randomUUID().toString();
    private static String jwtToken;
    private static String userId;
    private static String routeId;
    private static String sessionId;

    @Test
    @Order(1)
    public void createUser() {
        String body = new JsonObject().put("birthDay", "2020-04-22").put("email", email).put("name", "pippo").put("passwordHash", "pass").put("surename", "ciccio").toString();

        given().contentType(ContentType.JSON).body(body)
                .when().post("http://localhost:1339/users")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void authenticateUser() throws InterruptedException {
        String body = new JsonObject().put("email", email).put("password", "pass").toString();

        AuthenticationResponse response = executeUntilSuccess(() -> given().contentType(ContentType.JSON).body(body).when().post("http://localhost:1339/users/auth"))
                .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", AuthenticationResponse.class);


        userId = response.userId;
        jwtToken = response.jwtBearer;

        Assertions.assertNotNull(response.userId);
        Assertions.assertNotNull(response.jwtBearer);
    }

    @Test
    @Order(3)
    public void authenticationTest() {
        given().header("Authorization", "Bearer " + jwtToken)
                .when().get("http://localhost:1339/users/" + userId).then().statusCode(200);
    }

    @Test
    @Order(4)
    public void createRoute() {
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

    @Test
    @Order(5)
    public void createLiveTripRoute() {
        String body = new JsonObject()
                .put("userId", userId)
                .put("day", "FRIDAY")
                .put("routeId", routeId)
                .toString();

        LiveSessionSummary session = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken).body(body)
                .when().post("http://localhost:1337/users/" + userId + "/liveSessions").then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveSessionSummary.class);

        sessionId = session.sessionId;

        Assertions.assertNotNull(sessionId);
    }

    @Test
    @Order(6)
    public void retrieveLiveTripSessions() throws InterruptedException {
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get("http://localhost:1337/users/" + userId + "/liveSessions")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", AvailableLiveSessionsResponse.class).sessions.size() == 1);

        Assertions.assertTrue(true);
    }

    @Test
    @Order(7)
    public void putNewChunksInLiveTrips() throws InterruptedException, IOException {


        String chunk0 = getResourceAsString("/chunk0.json");
        String chunk1 = getResourceAsString("/chunk1.json");
        String chunk2 = getResourceAsString("/chunk2.json");

        given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken).body(chunk0).when().put("http://localhost:1337/users/" + userId + "/liveSessions/" + sessionId).then().statusCode(200);
        given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken).body(chunk1).when().put("http://localhost:1337/users/" + userId + "/liveSessions/" + sessionId).then().statusCode(200);
        given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken).body(chunk2).when().put("http://localhost:1337/users/" + userId + "/liveSessions/" + sessionId).then().statusCode(200);

        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get("http://localhost:1337/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=0")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).chunks.size() == 3);
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get("http://localhost:1337/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=1")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).chunks.size() == 2);
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get("http://localhost:1337/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=2")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).chunks.size() == 1);
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get("http://localhost:1337/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=3")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).chunks.size() == 0);
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get("http://localhost:1337/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=0")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).isLive == false);
    }

    private String getResourceAsString(String path) throws IOException {
        InputStream resourceAsStream = IntegrationTest.class.getResourceAsStream(path);
        StringWriter writer = new StringWriter();
        IOUtils.copy(resourceAsStream, writer, "UTF-8");
        return writer.toString();
    }

    private Response executeUntilSuccess(Callable callable) throws InterruptedException {
        for (int i = 0; i <= 20; i++) {
            try {
                Response response = (Response) callable.call();
                if (response.statusCode() == 200) {
                    return response;
                }
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
        throw new NotFoundException("Request did not success.");
    }

    private boolean retryUntilSuccess(Callable callable) throws InterruptedException {
        for (int i = 0; i <= 20; i++) {
            try {
                boolean assertionResult = (boolean) callable.call();
                if (assertionResult) {
                    return true;
                }
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
        throw new NotFoundException("Can not retrieve the right data after 20 seconds.");
    }
}