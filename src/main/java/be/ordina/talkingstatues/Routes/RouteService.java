package be.ordina.talkingstatues.Routes;

import be.ordina.talkingstatues.Monument.Monument;
import be.ordina.talkingstatues.Monument.MonumentRepository;
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

        UserLocation start = new UserLocation(4.454304,51.051742);

        List<Monument> monuments = Dijkstra(monumentRepository.findAll(), start);
        System.out.println(monuments);
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
       List<Monument> sortedMonuments = Dijkstra(
               routeRequest.getLocations().stream()
                       .map(monumentRepository::findById)
                       .filter(Optional::isPresent)
                       .map(Optional::get)
                       .collect(Collectors.toList()),routeRequest.getUserLocation()
       );
       return routeRepository.save(new Route(routeRequest.getName(),sortedMonuments));
    }

    List<Monument> Dijkstra(List<Monument> monuments, UserLocation userLocation){
        Node start = new Node("start",userLocation.longitude,userLocation.latitude);
        Set<Node> nodes = monuments.stream()
                .map(monument -> new Node(monument.getId(),monument.getLongitude(),monument.getLatitude())).collect(Collectors.toSet());
        nodes.add(start);
        nodes.forEach(nodeA -> nodes.stream().filter(nodeB-> nodeB!=nodeA)
                .forEach(nodeB -> nodeB.addDestination(nodeA,calculateDistance(nodeA.getLongitude(),nodeB.getLongitude(),nodeA.getLatitude(),nodeB.getLatitude()))));

        Set<Node> sorted = calculateShortestPathFromSource(nodes, start);
        return sorted.stream()
                .map(node -> monumentRepository.findById(node.getName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    Double calculateDistance(Double long1,Double long2, Double lat1, Double lat2){
        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(long2 - long1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        return Math.sqrt(Math.pow(distance, 2));
    }

    private static Set<Node> calculateShortestPathFromSource(Set<Node> graph, Node source) {
        source.setDistance(0.0);
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {

            Node currentNode = unsettledNodes.stream()
                    .sorted(Comparator.comparing(Node::getDistance))
                    .findFirst().orElseThrow(RuntimeException::new);

            unsettledNodes.remove(currentNode);
            currentNode.getAdjacentNodes().entrySet().stream()
                    .filter(i ->!settledNodes.contains(i.getKey()))
                    .forEach(i -> {
                        CalculateMinimumDistance(i.getKey(), i.getValue(), currentNode);
                        unsettledNodes.add(i.getKey());
                    });
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static void CalculateMinimumDistance(Node evaluationNode, Double distanceToSourceNode, Node sourceNode) {
        if (sourceNode.getDistance() + distanceToSourceNode < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceNode.getDistance() + distanceToSourceNode);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    void deleteRoute(String id){
        routeRepository.deleteById(id);
    }
}
