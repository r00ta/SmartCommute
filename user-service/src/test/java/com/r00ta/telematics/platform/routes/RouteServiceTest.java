package com.r00ta.telematics.platform.routes;

import java.time.DayOfWeek;
import java.util.List;

import com.r00ta.telematics.platform.mocks.RoutesStorageMock;
import com.r00ta.telematics.platform.mocks.UserServiceMock;
import com.r00ta.telematics.platform.routes.models.DayRouteDrive;
import com.r00ta.telematics.platform.routes.models.DriverPreferences;
import com.r00ta.telematics.platform.routes.models.DriverRideReference;
import com.r00ta.telematics.platform.routes.models.PassengerRideReference;
import com.r00ta.telematics.platform.routes.models.Route;
import com.r00ta.telematics.platform.routes.requests.NewRouteRequest;
import com.r00ta.telematics.platform.users.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RouteServiceTest {

    RouteService routeService = new RouteService();

    @BeforeAll
    void setup() {
        routeService.userService = new UserServiceMock();
        routeService.storageExtension = new RoutesStorageMock();
    }

    @Test
    public void GivenANewRouteRequest_WhenARouteIsStored_ThenItIsAvailable() {
        NewRouteRequest routeRequest = createNewDriverRouteRequest();

        Route route = new Route(routeRequest, "MyRouteId");
        Assertions.assertEquals(true, routeService.storeRoute(route));

        Route retrievedRoute = routeService.getRouteById(route.routeId);
        Assertions.assertEquals(route.routeId, retrievedRoute.routeId);
        Assertions.assertEquals(route.availableAsPassenger, retrievedRoute.availableAsPassenger);

        List<Route> routes = routeService.getUserRoutes(routeRequest.userId);
        Assertions.assertEquals(1, routes.size());
    }

    @Test
    public void testGreetingService() {
        NewRouteRequest driverRouteRequest = createNewDriverRouteRequest();

        Route driverRoute = new Route(driverRouteRequest, "driverRouteId");
        DayRouteDrive driverDay = driverRoute.dayRides.get(DayOfWeek.FRIDAY);
        driverDay.passengerReferences.add(new PassengerRideReference("passenger", "passengerRouteId", "renzo"));
        Assertions.assertEquals(true, routeService.storeRoute(driverRoute));

        NewRouteRequest passengerRequest = createNewPassengerRouteRequest();
        Route passengerRoute = new Route(passengerRequest, "passengerRouteId");
        DayRouteDrive passengerDay = passengerRoute.dayRides.get(DayOfWeek.FRIDAY);
        passengerDay.isADriverRide = false;
        passengerDay.isAPassengerRoute = true;
        passengerDay.passengerReferences = null;
        passengerDay.driverReference = new DriverRideReference("driver", "driverRouteId", "lucia");
        Assertions.assertEquals(true, routeService.storeRoute(passengerRoute));

        boolean success = routeService.deletePassenger(driverRoute.userId, driverRoute.routeId, "passenger", new DayOfWeek[]{DayOfWeek.FRIDAY});

        Assertions.assertEquals(true, success);

        Route retrievedDriverRoute = routeService.getRouteById(driverRoute.routeId);
        Assertions.assertEquals(0, retrievedDriverRoute.dayRides.get(DayOfWeek.FRIDAY).passengerReferences.size());

        Route retrievedPassengerRoute = routeService.getRouteById(passengerRoute.routeId);
        Assertions.assertEquals(true, retrievedPassengerRoute.dayRides.get(DayOfWeek.FRIDAY).isADriverRide);
        Assertions.assertEquals(0, retrievedPassengerRoute.dayRides.get(DayOfWeek.FRIDAY).passengerReferences.size());
    }

    private NewRouteRequest createNewPassengerRouteRequest() {
        NewRouteRequest routeRequest = new NewRouteRequest();
        routeRequest.userId = "passenger";
        routeRequest.availableAsPassenger = true;
        routeRequest.days = List.of(DayOfWeek.FRIDAY);
        routeRequest.driverPreferences = new DriverPreferences(true, 0.0f, true, 500L);
        routeRequest.endPositionUserLabel = "testEnd";
        routeRequest.startPositionUserLabel = "testStart";

        User user = new User();
        user.name = "renzo";
        user.userId = "passenger";
        routeService.userService.createUser(user);

        return routeRequest;
    }

    private NewRouteRequest createNewDriverRouteRequest() {
        NewRouteRequest routeRequest = new NewRouteRequest();
        routeRequest.userId = "driver";
        routeRequest.availableAsPassenger = true;
        routeRequest.days = List.of(DayOfWeek.FRIDAY);
        routeRequest.driverPreferences = new DriverPreferences(true, 0.0f, true, 500L);
        routeRequest.endPositionUserLabel = "testEnd";
        routeRequest.startPositionUserLabel = "testStart";

        User user = new User();
        user.name = "lucia";
        user.userId = "driver";
        routeService.userService.createUser(user);

        return routeRequest;
    }
}