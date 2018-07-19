package be.ordina.talkingstatues.routes;

import be.ordina.talkingstatues.monuments.Monument;
import be.ordina.talkingstatues.monuments.MonumentService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.Optional;

import static be.ordina.talkingstatues.monuments.MonumentTestConstants.MON_ID;
import static be.ordina.talkingstatues.routes.RouteTestConstants.ROUTE_ID;
import static be.ordina.talkingstatues.routes.RouteTestConstants.ROUTE_NAME;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RouteServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private RouteRepository routeRepository;
    @Mock
    private MonumentService monumentService;
    @InjectMocks
    private RouteService routeService;

    @Test
    public void getRoutes() {
        List<Route> expected = singletonList(new Route());
        when(routeRepository.findAll()).thenReturn(expected);

        List<Route> actual = routeService.getRoutes();

        assertEquals(expected, actual);
    }

    @Test
    public void getRouteById() {
        Route expected = new Route();
        expected.setId(ROUTE_ID);
        when(routeRepository.findById(ROUTE_ID)).thenReturn(Optional.of(expected));

        Route actual = routeService.getRouteById(ROUTE_ID);

        assertEquals(expected, actual);
    }

    @Test
    public void getRouteById_notFound() {
        when(routeRepository.findById(ROUTE_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> routeService.getRouteById(ROUTE_ID))
                .withMessage("Route met id " + ROUTE_ID + " bestaat niet");
    }

    @Test
    public void create() {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setLocations(null);
        routeRequest.setName(ROUTE_NAME);

        Route expected = new Route();
        expected.setName(ROUTE_NAME);
        expected.setMonuments(emptyList());
        expected.setId(null);
        when(monumentService.getMonumentsInRoute(routeRequest)).thenReturn(emptyList());
        when(routeRepository.save(refEq(expected))).thenReturn(expected);

        Route actual = routeService.create(routeRequest);

        assertEquals(expected, actual);
    }

    @Test
    public void create_withMonuments() {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setLocations(singletonList(MON_ID));
        routeRequest.setName(ROUTE_NAME);

        Monument monument = new Monument();
        Route expected = new Route();
        expected.setName(ROUTE_NAME);
        expected.setMonuments(singletonList(monument));
        expected.setId(null);
        when(monumentService.getMonumentsInRoute(routeRequest)).thenReturn(singletonList(monument));
        when(routeRepository.save(refEq(expected))).thenReturn(expected);

        Route actual = routeService.create(routeRequest);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteRoute() {
        routeService.deleteRoute(ROUTE_ID);

        verify(routeRepository).deleteById(ROUTE_ID);
    }
}