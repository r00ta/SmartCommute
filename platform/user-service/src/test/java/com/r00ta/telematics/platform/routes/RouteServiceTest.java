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
        String userId = "pippo";
        Route route = new Route(userId, routeRequest, "MyRouteId");
        Assertions.assertEquals(true, routeService.storeRoute(route));

        Route retrievedRoute = routeService.getRouteById(route.routeId).get();

        Assertions.assertEquals(route.routeId, retrievedRoute.routeId);
        Assertions.assertEquals(route.availableAsPassenger, retrievedRoute.availableAsPassenger);

        List<Route> routes = routeService.getUserRoutes(userId);
        Assertions.assertEquals(1, routes.size());
    }

    @Test
    public void GivenTwoUsersPaired_WhenAPassengerIsRemovedFromARide_ThenTheRidesAreProperlyModified() {
        NewRouteRequest driverRouteRequest = createNewDriverRouteRequest();
        String driverId = "driver";

        Route driverRoute = new Route(driverId, driverRouteRequest, "driverRouteId");
        DayRouteDrive driverDay = driverRoute.dayRides.get(DayOfWeek.FRIDAY);
        driverDay.passengerReferences.add(new PassengerRideReference("passenger", "passengerRouteId", "renzo"));
        Assertions.assertEquals(true, routeService.storeRoute(driverRoute));

        NewRouteRequest passengerRequest = createNewPassengerRouteRequest();
        String passengerId = "passenger";

        Route passengerRoute = new Route(passengerId, passengerRequest, "passengerRouteId");
        DayRouteDrive passengerDay = passengerRoute.dayRides.get(DayOfWeek.FRIDAY);
        passengerDay.isADriverRide = false;
        passengerDay.isAPassengerRoute = true;
        passengerDay.passengerReferences = null;
        passengerDay.driverReference = new DriverRideReference(driverId, "driverRouteId", "lucia");
        Assertions.assertEquals(true, routeService.storeRoute(passengerRoute));

        boolean success = routeService.deletePassenger(driverRoute.userId, driverRoute.routeId, passengerId, new DayOfWeek[]{DayOfWeek.FRIDAY});

        Assertions.assertEquals(true, success);

        Route retrievedDriverRoute = routeService.getRouteById(driverRoute.routeId).get();
        Assertions.assertEquals(0, retrievedDriverRoute.dayRides.get(DayOfWeek.FRIDAY).passengerReferences.size());

        Route retrievedPassengerRoute = routeService.getRouteById(passengerRoute.routeId).get();
        Assertions.assertEquals(true, retrievedPassengerRoute.dayRides.get(DayOfWeek.FRIDAY).isADriverRide);
        Assertions.assertEquals(0, retrievedPassengerRoute.dayRides.get(DayOfWeek.FRIDAY).passengerReferences.size());
    }

    @Test
    public void GivenTwoUsersPaired_WhenADriverIsRemovedFromARide_ThenTheRidesAreProperlyModified() {
        NewRouteRequest driverRouteRequest = createNewDriverRouteRequest();
        String driverId = "driver";

        Route driverRoute = new Route(driverId, driverRouteRequest, "driverRouteId");
        DayRouteDrive driverDay = driverRoute.dayRides.get(DayOfWeek.FRIDAY);
        driverDay.passengerReferences.add(new PassengerRideReference("passenger", "passengerRouteId", "renzo"));
        Assertions.assertEquals(true, routeService.storeRoute(driverRoute));

        NewRouteRequest passengerRequest = createNewPassengerRouteRequest();
        String passengerId = "passenger";

        Route passengerRoute = new Route(passengerId, passengerRequest, "passengerRouteId");
        DayRouteDrive passengerDay = passengerRoute.dayRides.get(DayOfWeek.FRIDAY);
        passengerDay.isADriverRide = false;
        passengerDay.isAPassengerRoute = true;
        passengerDay.passengerReferences = null;
        passengerDay.driverReference = new DriverRideReference("driver", "driverRouteId", "lucia");
        Assertions.assertEquals(true, routeService.storeRoute(passengerRoute));

        boolean success = routeService.deleteDriver(passengerRoute.userId, passengerRoute.routeId, "driver", new DayOfWeek[]{DayOfWeek.FRIDAY});

        Assertions.assertEquals(true, success);

        Route retrievedDriverRoute = routeService.getRouteById(driverRoute.routeId).get();
        Assertions.assertEquals(0, retrievedDriverRoute.dayRides.get(DayOfWeek.FRIDAY).passengerReferences.size());

        Route retrievedPassengerRoute = routeService.getRouteById(passengerRoute.routeId).get();
        Assertions.assertEquals(true, retrievedPassengerRoute.dayRides.get(DayOfWeek.FRIDAY).isADriverRide);
        Assertions.assertEquals(0, retrievedPassengerRoute.dayRides.get(DayOfWeek.FRIDAY).passengerReferences.size());
    }

    private NewRouteRequest createNewPassengerRouteRequest() {
        NewRouteRequest routeRequest = new NewRouteRequest();
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