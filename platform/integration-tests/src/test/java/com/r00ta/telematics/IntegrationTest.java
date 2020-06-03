package com.r00ta.telematics;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.time.DayOfWeek;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.ws.rs.NotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.models.livetrip.AvailableLiveSessionsResponse;
import com.r00ta.telematics.models.livetrip.LiveChunksResponse;
import com.r00ta.telematics.models.livetrip.LiveSessionSummary;
import com.r00ta.telematics.models.livetrip.TripModel;
import com.r00ta.telematics.models.routes.AvailableMatchingsResponse;
import com.r00ta.telematics.models.routes.Route;
import com.r00ta.telematics.models.routes.RouteMatching;
import com.r00ta.telematics.models.scoring.EnrichedTrip;
import com.r00ta.telematics.models.scoring.EnrichedTripsByTimeRangeResponse;
import com.r00ta.telematics.models.user.AuthenticationResponse;
import com.r00ta.telematics.models.user.UserStatistics;
import com.r00ta.telematics.utils.Gzip;
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

    private static final String adminJwt = "eyJraWQiOiIvcHJpdmF0ZUtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJncm91cHMiOlsiQWRtaW4iXSwic3ViIjoiYWRtaW4iLCJpc3MiOiJodHRwczovL3F1YXJrdXMuaW8vdXNpbmctand0LXJiYWMiLCJpYXQiOjE1OTA2NzM4MjEsImF1dGhfdGltZSI6MTU5MDY3MzgyMSwiZXhwIjoxNzQwODcwMDQ1LCJqdGkiOiIyRUV5TzFiZWUwNXdSS1BwVGotUWhnIn0.Xpx6u8GOhHvj3KA6KPL3mdnNvDPZyGAov3adgU-qi0LVyJgaGrJJZgddEXeSKS3CXUBNPXPJ6PhSElRBqpP8zLdwoBbApo6VvP6d9tbW-FVb5hUnubCHSQRl01p9opF0GlHfcJrvFNaU6rgY_PmXuMyvye0Cw5jpNI8YnOh_Lt7TFwfUdlGt6HcyMkZy2M6q4XZiPS7xHsXHElDPLM0AV5ULGztlbiZzTy8eKcUn9VLnYDbeJGaH0rdYRUFaEJv0Rgd58pbm23wD9ekeK1MPdVaViZ3L3mZZKknEhek6w_2ppSKoyBK86oT-5XC1MOURn0N7TyZ95SsW0TADw4jrHA";
    private static String driverEmail = UUID.randomUUID().toString();
    private static String passengerEmail = UUID.randomUUID().toString();
    private static String jwtToken;
    private static String userId;
    private static String routeId;
    private static String sessionId;
    private static String tripId;
    private static String passengerUserId;
    private static String passengerRouteId;

    @Test
    @Order(1)
    public void createUser() {
        LOGGER.info("Creating user test.");
        String body = new JsonObject().put("birthDay", "2020-04-22").put("email", driverEmail).put("name", "pippo").put("passwordHash", "pass").put("surename", "ciccio").toString();

        given().contentType(ContentType.JSON).body(body)
                .when().post(userEndpoint + "/users")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void authenticateUser() throws InterruptedException {
        LOGGER.info("Auth user test.");
        String body = new JsonObject().put("email", driverEmail).put("password", "pass").toString();

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

        LOGGER.info("Equals 3");
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=0")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).chunks.size() == 3);
        LOGGER.info("Equals 2");
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=1")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).chunks.size() == 2);
        LOGGER.info("Equals 1");
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=2")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).chunks.size() == 1);
        LOGGER.info("Equals 0");
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=3")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).chunks.size() == 0);
        LOGGER.info("Equals 3");
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/liveSessions/" + sessionId + "?lastChunk=0")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", LiveChunksResponse.class).isLive == false);
    }

    @Test
    @Order(8)
    public void createNewTrip() throws InterruptedException, IOException {
        LOGGER.info("Creating trip.");
        String plainTrip = getResourceAsString("/testTrip.json");
        String body = new JsonObject()
                .put("base64gzipNewTripRequest", new String(Base64.getEncoder().encode(Gzip.compress(plainTrip))))
                .toString();

        tripId = UUID.randomUUID().toString();

        given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken).body(body).when().post(liveTripEndpoint + "/users/" + userId + "/trips/" + tripId).then().statusCode(200);

        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(liveTripEndpoint + "/users/" + userId + "/trips/" + tripId)
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", TripModel.class).positions.size() >= 20);
    }

    @Test
    @Order(9)
    public void retrieveEnrichedTripHeaders() throws InterruptedException {
        LOGGER.info("Fetching enriched trip.");
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(scoringEndpoint + "/users/" + userId + "/enrichedTrips?from=1587755000000&to=1587765000000")
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", EnrichedTripsByTimeRangeResponse.class).enrichedTrips.size() >= 1);
    }

    @Test
    @Order(10)
    public void retrieveEnrichedTrip() throws InterruptedException {
        LOGGER.info("Fetching enriched trip.");
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(scoringEndpoint + "/users/" + userId + "/enrichedTrips/" + tripId)
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", EnrichedTrip.class).positions.size() >= 20);
    }

    @Test
    @Order(11)
    public void retrieveUpdatedUserStatistics() throws InterruptedException {
        LOGGER.info("Fetching user statistics.");
        retryUntilSuccess(
                () -> given().header("Authorization", "Bearer " + jwtToken).when().get(userEndpoint + "/users/" + userId)
                        .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", UserStatistics.class).totalDistanceDrivenInM >= 20);
    }

    @Test
    @Order(12)
    public void createPassengerUserAndRoute() throws InterruptedException {
        LOGGER.info("Create passenger user and route user.");

        String body = new JsonObject().put("birthDay", "2020-04-22").put("email", passengerEmail).put("name", "passenger").put("passwordHash", "pass").put("surename", "pasticcio").toString();

        given().contentType(ContentType.JSON).body(body)
                .when().post(userEndpoint + "/users")
                .then()
                .statusCode(200);

        LOGGER.info("Passenger created");
        String authBody = new JsonObject().put("email", passengerEmail).put("password", "pass").toString();

        AuthenticationResponse response = executeUntilSuccess(() -> given().contentType(ContentType.JSON).body(authBody).when().post(userEndpoint + "/users/auth"))
                .then().contentType(ContentType.JSON).extract().response().jsonPath().getObject("$", AuthenticationResponse.class);

        Assertions.assertNotNull(response.userId);
        Assertions.assertNotNull(response.jwtBearer);
        LOGGER.info("Passenger authenticated");

        passengerUserId = response.userId;
        String passengerJwtToken = response.jwtBearer;

        String routeBody = new JsonObject()
                .put("availableAsPassenger", true)
                .put("days", new JsonArray().add("FRIDAY"))
                .put("endPositionUserLabel", "office")
                .put("startPositionUserLabel", "home")
                .put("driverPreferences", new JsonObject().put("availableAsDriver", true).put("flexibility", 500).put("onTheRouteOption", true).put("radiusStartPoint", 500))
                .toString();

        String routeId = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + passengerJwtToken).body(routeBody)
                .when().post(userEndpoint + "/users/" + passengerUserId + "/routes").then().extract().jsonPath().getString("routeId");
        LOGGER.info("Passenger route created");

        Assertions.assertNotNull(routeId);
        passengerRouteId = routeId;
    }

    @Test
    @Order(13)
    public void createANewRouteMatching() throws JsonProcessingException {
        LOGGER.info("Create new Route Matching.");

        RouteMatching routeMatching = new RouteMatching(UUID.randomUUID().toString(), userId, routeId, passengerUserId, passengerRouteId, null, null, DayOfWeek.FRIDAY);
        given().contentType(ContentType.JSON).header("Authorization", "Bearer " + adminJwt).body(mapper.writeValueAsString(routeMatching))
                .when().post(userEndpoint + "/users/" + userId + "/matchings")
                .then().statusCode(200);
    }

    @Test
    @Order(14)
    public void changePendingMatchingStatus() throws JsonProcessingException {
        LOGGER.info("Change pending status");

        AvailableMatchingsResponse response = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken)
                .when().get(userEndpoint + "/users/" + userId + "/matchings")
                .as(AvailableMatchingsResponse.class);

        String matchingId = response.matchings.get(0).matchingId;

        String body = new JsonObject().put("status", "ACCEPTED").toString();

        given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken).body(body)
                .when().put(userEndpoint + "/users/" + userId + "/matchings/" + matchingId)
                .then().statusCode(200);
    }

    @Test
    @Order(15)
    public void passengerAccepts() {
        LOGGER.info("Passenger changes pending matching");

        AvailableMatchingsResponse response = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken)
                .when().get(userEndpoint + "/users/" + userId + "/matchings")
                .as(AvailableMatchingsResponse.class);

        String matchingId = response.matchings.get(0).matchingId;

        String body = new JsonObject().put("status", "ACCEPTED").toString();

        given().contentType(ContentType.JSON).header("Authorization", "Bearer " + adminJwt).body(body)
                .when().put(userEndpoint + "/users/" + passengerUserId + "/matchings/" + matchingId)
                .then().statusCode(200);
    }

    @Test
    @Order(16)
    public void matchingIsNotPendingAnymore() {
        LOGGER.info("Matching is not pending anymore");

        AvailableMatchingsResponse response = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken)
                .when().get(userEndpoint + "/users/" + userId + "/matchings")
                .as(AvailableMatchingsResponse.class);

        Assertions.assertEquals(0, response.matchings.size());

        response = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + adminJwt)
                .when().get(userEndpoint + "/users/" + passengerUserId + "/matchings")
                .as(AvailableMatchingsResponse.class);

        Assertions.assertEquals(0, response.matchings.size());
    }

    @Test
    @Order(17)
    public void routesAreUpdated() {
        LOGGER.info("Driver and passenger routes are updated.");

        Route driverRoute = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + jwtToken)
                .when().get(userEndpoint + "/users/" + userId + "/routes/" + routeId ).as(Route.class);

        Assertions.assertTrue(driverRoute.dayRides.get(DayOfWeek.FRIDAY).isADriverRide);
        Assertions.assertEquals(1, driverRoute.dayRides.get(DayOfWeek.FRIDAY).passengerReferences.size());
        Assertions.assertEquals(passengerUserId, driverRoute.dayRides.get(DayOfWeek.FRIDAY).passengerReferences.get(0).passengerUserId);
        Assertions.assertEquals(passengerRouteId, driverRoute.dayRides.get(DayOfWeek.FRIDAY).passengerReferences.get(0).passengerRouteId);
        Assertions.assertFalse(driverRoute.dayRides.get(DayOfWeek.FRIDAY).isAPassengerRoute);

        Route passengerRoute = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + adminJwt)
                .when().get(userEndpoint + "/users/" + passengerUserId + "/routes/" + passengerRouteId ).as(Route.class);

        Assertions.assertFalse(passengerRoute.dayRides.get(DayOfWeek.FRIDAY).isADriverRide);
        Assertions.assertEquals(0, passengerRoute.dayRides.get(DayOfWeek.FRIDAY).passengerReferences.size());
        Assertions.assertEquals(userId, passengerRoute.dayRides.get(DayOfWeek.FRIDAY).driverReference.driverUserId);
        Assertions.assertEquals(routeId, passengerRoute.dayRides.get(DayOfWeek.FRIDAY).driverReference.driverRouteId);
        Assertions.assertTrue(passengerRoute.dayRides.get(DayOfWeek.FRIDAY).isAPassengerRoute);
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