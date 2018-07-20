package be.ordina.talkingstatues.routes;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static be.ordina.talkingstatues.routes.RouteTestConstants.ROUTE_ID;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RouteControllerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private RouteService routeService;
    @InjectMocks
    private RouteController routeController;

    @Test
    public void getAllRoutes() {
        List<Route> expected = singletonList(new Route());
        when(routeService.getRoutes()).thenReturn(expected);

        List<Route> actual = routeController.getAllRoutes();

        assertEquals(expected, actual);
    }

    @Test
    public void getRoute() {
        Route expected = new Route();
        when(routeService.getRouteById(ROUTE_ID)).thenReturn(expected);

        Route actual = routeController.getRoute(ROUTE_ID);

        assertEquals(expected, actual);
    }

    @Test
    public void createRoute() {
        RouteRequest routeRequest = new RouteRequest();
        Route expected = new Route();
        when(routeService.create(routeRequest)).thenReturn(expected);

        ResponseEntity<Route> actual = routeController.createRoute(routeRequest);
        ResponseEntity<Route> expectedResponseEntity = new ResponseEntity<>(expected, HttpStatus.CREATED);

        assertEquals(expectedResponseEntity, actual);
    }

    @Test
    public void deleteRoute() {
        ResponseEntity actual = routeController.deleteRoute(ROUTE_ID);

        verify(routeService).deleteRoute(ROUTE_ID);
        assertEquals(ResponseEntity.ok().build(), actual);
    }
}