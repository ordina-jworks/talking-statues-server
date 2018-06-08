package be.ordina.talkingstatues.Routes;
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
    List<Route> getRoutes(){
        return routeService.getRoutes();
    }

    @PostMapping(value = "", produces = {"application/vnd.ordina.v1.0+json"})
    ResponseEntity<Route> saveRoute(@RequestBody Route route){
        Route savedRoute = routeService.save(route);
        return new ResponseEntity<>(savedRoute,HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/{id}", produces = {"application/vnd.ordina.v1.0+json"})
    @ResponseBody
    ResponseEntity deleteRoute(@PathVariable String id){
        routeService.deleteRoute(id);
        return ResponseEntity.ok().build();

    }
}
