package com.r00ta.telematics;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.ws.rs.NotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.models.user.AuthenticationResponse;
import com.r00ta.telematics.models.livetrip.AvailableLiveSessionsResponse;
import com.r00ta.telematics.models.scoring.EnrichedTrip;
import com.r00ta.telematics.models.livetrip.LiveChunksResponse;
import com.r00ta.telematics.models.livetrip.LiveSessionSummary;
import com.r00ta.telematics.models.livetrip.TripModel;
import com.r00ta.telematics.models.user.UserStatistics;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationTest.class);

//    private static final String liveTripEndpoint = "http://live-trip-service-smartcommute-2020.apps-crc.testing:80";
//    private static final String scoringEndpoint = "http://scoring-service-smartcommute-2020.apps-crc.testing:80";
//    private static final String userEndpoint = "http://user-service-smartcommute-2020.apps-crc.testing:80";
    private static final String liveTripEndpoint = "http://localhost:1337";
    private static final String scoringEndpoint = "http://localhost:1338";
    private static final String userEndpoint = "http://localhost:1339";

    private static final ObjectMapper mapper = new ObjectMapper();

    private static String email = UUID.randomUUID().toString();
    private static String jwtToken;
    private static String userId;
    private static String routeId;
    private static String sessionId;
    private static String tripId;

    @Test
    @Order(1)
    public void createUser() {
        LOGGER.info("Creating user test.");
        String body = new JsonObject().put("birthDay", "2020-04-22").put("email", email).put("name", "pippo").put("passwordHash", "pass").put("surename", "ciccio").toString();

        given().contentType(ContentType.JSON).body(body)
                .when().post(userEndpoint + "/users")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void authenticateUser() throws InterruptedException {
        LOGGER.info("Auth user test.");
        String body = new JsonObject().put("email", email).put("password", "pass").toString();

        AuthenticationResponse response = executeUntilSuccess(() -> given().contentType(ContentType.JSON).body(body).when().post(userEndpoint + "/users/auth"))
                .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", AuthenticationResponse.class);


        userId = response.userId;
        jwtToken = response.jwtBearer;

        Assertions.assertNotNull(response.userId);
        Assertions.assertNotNull(response.jwtBearer);
    }

    @Test
    @Order(3)
    public void authenticationTest() {
        LOGGER.info("Testing authentication.");
        given().header("Authorization", "Bearer " + jwtToken)
                .when().get(userEndpoint + "/users/" + userId).then().statusCode(200);
    }

    @Test
    @Order(4)
    public void createRoute() {
        LOGGER.info("Creating route.");
        String body = new JsonObject()
                .put("availableAsPassenger", true)
                .put("days", new JsonArray().add("FRIDAY"))
                .put("endPositionUserLabel", "office")
                .put("startPositionUserLabel", "home")
                .put("driverPreferences", new JsonObject().put("availableAsDriver", true).put("flexibility", 500).put("onTheRouteOption", true).put("radiusStartPoint", 500))
                .toString();

        String routeId = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken).body(body)
                .when().post(userEndpoint + "/users/" + userId + "/routes").then().extract().jsonPath().getString("routeId");

        Assertions.assertNotNull(routeId);
        this.routeId = routeId;
    }

    @Test
    @Order(5)
    public void createLiveTripRoute() {
        LOGGER.info("Creating live trip");
        LiveSessionSummary session = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken)
                .when().post(liveTripEndpoint + "/users/" + userId + "/liveSessions?day=FRIDAY&routeId=" + routeId).then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveSessionSummary.class);

        sessionId = session.sessionId;

        Assertions.assertNotNull(sessionId);
    }

    @Test
    @Order(6)
    public void retrieveLiveTripSessions() throws InterruptedException {
        LOGGER.info("Retrieving live trip.");
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/liveSessions")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", AvailableLiveSessionsResponse.class).sessions.size() == 1);

        Assertions.assertTrue(true);
    }

    @Test
    @Order(7)
    public void putNewChunksInLiveTrips() throws InterruptedException, IOException {
        LOGGER.info("Submit new chunks");
        String chunk0 = getResourceAsString("/chunk0.json");
        String chunk1 = getResourceAsString("/chunk1.json");
        String chunk2 = getResourceAsString("/chunk2.json");

        given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken).body(chunk0).when().put(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId).then().statusCode(200);
        given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken).body(chunk1).when().put(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId).then().statusCode(200);
        given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken).body(chunk2).when().put(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId).then().statusCode(200);

        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=0")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).chunks.size() == 3);
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=1")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).chunks.size() == 2);
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=2")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).chunks.size() == 1);
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=3")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).chunks.size() == 0);
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=0")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).isLive == false);
    }


    @Test
    @Order(8)
    public void createNewTrip() throws InterruptedException, IOException {
        LOGGER.info("Creating trip.");
        String trip = getResourceAsString("/testTrip.json");

        tripId = UUID.randomUUID().toString();

        given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken).body(trip).when().post(liveTripEndpoint + "/users/" + userId + "/trips/" + tripId).then().statusCode(200);

        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/trips/" +  tripId)
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", TripModel.class).positions.size() >= 20);
    }

    @Test
    @Order(9)
    public void retrieveEnrichedTrip() throws InterruptedException {
        LOGGER.info("Fetching enriched trip.");
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(scoringEndpoint + "/users/" + userId + "/enrichedTrips/" +  tripId)
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", EnrichedTrip.class).positions.size() >= 20);
    }

    @Test
    @Order(10)
    public void retrieveUpdatedUserStatistics() throws InterruptedException {
        LOGGER.info("Fetching user statistics.");
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(userEndpoint + "/users/" + userId)
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", UserStatistics.class).totalDistanceDrivenInM >= 20);
    }

    private String getResourceAsString(String path) throws IOException {
        InputStream resourceAsStream = IntegrationTest.class.getResourceAsStream(path);
        StringWriter writer = new StringWriter();
        IOUtils.copy(resourceAsStream, writer, "UTF-8");
        return writer.toString();
    }

    private Response executeUntilSuccess(Callable callable) throws InterruptedException {
        ExecutorService executor = new ScheduledThreadPoolExecutor(1);
        for (int i = 0; i <= 60; i++) {
            try {
                Future<Response> future = executor.submit(callable);
                Response response = future.get();
                if (response.statusCode() == 200) {
                    return response;
                }
            } catch (Exception e) {
                LOGGER.info("Request failed due to " + e.getMessage());
            }
            Thread.sleep(1000);
        }
        throw new NotFoundException("Request did not success.");
    }

    private boolean retryUntilSuccess(Callable callable) throws InterruptedException {
        ExecutorService executor = new ScheduledThreadPoolExecutor(1);
        for (int i = 0; i <= 60; i++) {
            try {
                Future<Boolean> future = executor.submit(callable);
                Boolean assertionResult = future.get();
                if (assertionResult) {
                    return true;
                }
            } catch (Exception e) {
                LOGGER.info("Request failed due to " + e.getMessage());
            }
            Thread.sleep(1000);
        }
        throw new NotFoundException("Can not retrieve the right data after 20 seconds.");
    }
}