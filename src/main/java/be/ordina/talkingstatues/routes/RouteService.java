package be.ordina.talkingstatues.routes;

import be.ordina.talkingstatues.monuments.Monument;
import be.ordina.talkingstatues.monuments.MonumentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteService {


    private final RouteRepository routeRepository;
    private final MonumentRepository monumentRepository;

    public RouteService(RouteRepository routeRepository, MonumentRepository monumentRepository) {
        this.routeRepository = routeRepository;
        this.monumentRepository = monumentRepository;

        List<Monument> monuments = monumentRepository.findAll();
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
       List<Monument> sortedMonuments =
               routeRequest.getLocations().stream()
                       .map(monumentRepository::findById)
                       .filter(Optional::isPresent)
                       .map(Optional::get)
                       .collect(Collectors.toList())
       ;
       return routeRepository.save(new Route(routeRequest.getName(),sortedMonuments));
    }


    void deleteRoute(String id){
        routeRepository.deleteById(id);
    }
}
