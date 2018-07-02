package be.ordina.talkingstatues.routes;

import be.ordina.talkingstatues.monuments.Monument;
import be.ordina.talkingstatues.monuments.MonumentService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteService {


    private final RouteRepository routeRepository;
    private final MonumentService monumentService;

    public RouteService(RouteRepository routeRepository, MonumentService monumentService) {
        this.routeRepository = routeRepository;
        this.monumentService = monumentService;

        List<Monument> monuments = monumentService.getAllMonuments();
        System.out.println(monuments);
    }


    List<Route> getRoutes(){
        return routeRepository.findAll();
    }
    Route getRouteById(String id){
        return routeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Route met id "+id+" bestaat niet"));
    }

    public Route create(RouteRequest routeRequest) {
       if(routeRequest.getLocations()==null){
            routeRequest.setLocations(new ArrayList<>());
       }

       return routeRepository.save(new Route(routeRequest.getName(), monumentService.getSortedMonuments(routeRequest)));
    }


    void deleteRoute(String id){
        routeRepository.deleteById(id);
    }
}
