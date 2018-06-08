package be.ordina.talkingstatues.Routes;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {


    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    List<Route> getRoutes(){
        return routeRepository.findAll();
    }

    Route save(Route route) {
        return routeRepository.save(route);
    }

    void deleteRoute(String id){
        routeRepository.deleteById(id);
    }
}
