package be.ordina.talkingstatues.routes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping(value = "", produces = {"application/vnd.ordina.v1.0+json"})
    List<Route> getAllRoutes() {
        return routeService.getRoutes();
    }

    @GetMapping(value = "/{id}", produces = {"application/vnd.ordina.v1.0+json"})
    Route getRoute(@PathVariable String id) {
        return routeService.getRouteById(id);
    }

    @PutMapping(value = "", produces = {"application/vnd.ordina.v1.0+json"})
    ResponseEntity<Route> createRoute(@RequestBody RouteRequest routeRequest) {
        Route savedRoute = routeService.create(routeRequest);
        System.out.println("SavedRoute = " + savedRoute + " RouteRequest = " + routeRequest);
        return new ResponseEntity<>(savedRoute, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/vnd.ordina.v1.0+json"})
    @ResponseBody
    ResponseEntity deleteRoute(@PathVariable String id) {
        routeService.deleteRoute(id);
        return ResponseEntity.ok().build();

    }
}
