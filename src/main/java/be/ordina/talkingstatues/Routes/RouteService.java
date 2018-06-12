package be.ordina.talkingstatues.Routes;

import be.ordina.talkingstatues.Monument.MonumentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {


    private final RouteRepository routeRepository;
    private final MonumentRepository monumentRepository;

    public RouteService(RouteRepository routeRepository, MonumentRepository monumentRepository) {
        this.routeRepository = routeRepository;
        this.monumentRepository = monumentRepository;
    }

    List<Route> getRoutes(){
        return routeRepository.findAll();
    }
    Route getRouteById(String id){
        return routeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Route met id "+id+" bestaat niet"));
    }

    Route create(RouteRequest routeRequest) {
       if(routeRequest.getLocations()==null){
            routeRequest.setLocations(new ArrayList<>());
       }
       return routeRepository.save(new Route(routeRequest.getName(),
               routeRequest.getLocations().stream()
                       .map(monumentRepository::findById)
                       .filter(Optional::isPresent)
                       .map(Optional::get)
                       .collect(Collectors.toList())
       ));
    }

    void deleteRoute(String id){
        routeRepository.deleteById(id);
    }
}
