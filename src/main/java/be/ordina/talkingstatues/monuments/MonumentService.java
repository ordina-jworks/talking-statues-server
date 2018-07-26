package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.routes.RouteRequest;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

@Service
public class MonumentService {

    private final MonumentRepository monumentRepository;
    private final GridFsTemplate gridFsTemplate;

    public MonumentService(MonumentRepository statueRepository, GridFsTemplate gridFsTemplate) {
        this.monumentRepository = statueRepository;
        this.gridFsTemplate = gridFsTemplate;
    }

    public void initializeData(List<Monument> initialData) {
        monumentRepository.deleteAll();
        Arrays.stream(gridFsTemplate.getResources("*"))
                .forEach(resource -> gridFsTemplate.delete(query(whereFilename().is(resource.getFilename()))));
        for (Monument m : initialData) {
            monumentRepository.save(m);
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("images/" + m.getPicture());
            saveImage(inputStream, m.getId());
            //     System.out.println(m.toString() + " has been saved.\n");
        }
    }

    public List<Monument> getAllMonuments() {
        return monumentRepository.findAll();
    }

    public List<Monument> getMonumentsInRoute(RouteRequest routeRequest) {
        return routeRequest.getLocations().stream()
                       .map(monumentRepository::findById)
                       .filter(Optional::isPresent)
                       .map(Optional::get)
                       .collect(toList());
    }


    Monument getMonumentById(String id) {
        return monumentRepository.findById(id)
                       .orElseThrow(() -> new RuntimeException("Monument with id: " + id + " does not exist"));
    }

    Information getMonumentInformationByIdAndLanguage(String id, String language) {
        return getMonumentById(id).getInformation().stream()
                       .filter(information -> information.getLanguage().toString().equalsIgnoreCase(language))
                       .findFirst()
                       .orElseThrow(() -> new RuntimeException("Requested language is not supported"));
    }

    String findAnswer(String id, String language, String inputQuestion) {
        final List<Conversation> conversations = getMonumentInformationByIdAndLanguage(id, language).getConversations();
        final Map<String, Long> answerMap = new HashMap<>();
        conversations.forEach(conversation -> answerMap.put(conversation.getAnswer(), countMatches(inputQuestion, conversation.getQuestion())));

        return findBestMatch(answerMap);
    }


    List<Monument> getRandomSelection(String area, String language) {
        final List<Monument> monuments = monumentRepository.findAllByArea(area).stream()
                                                 .peek(monument -> monument.setInformation(monument.getInformation().stream()
                                                                                                   .filter(information -> information.getLanguage().toString().equalsIgnoreCase(language))
                                                                                                   .collect(toList())))
                                                 .collect(toList());
        Collections.shuffle(monuments);
        return monuments.size() >= 10 ? IntStream.range(0, 10).mapToObj(monuments::get).collect(toList()) : monuments;
    }

    List<String> getAllAreas() {
        return getAllMonuments().stream()
                       .map(Monument::getArea)
                       .distinct()
                       .collect(toList());
    }

    Monument addMonument(Monument monument) {
        if (monument.getId() == null) {
            return monumentRepository.save(monument);
        } else {
            throw new RuntimeException("the new monument already has an id");
        }
    }

    void addInformationToMonument(String monId, Information info) {
        final Monument foundMonument = getMonumentById(monId);

        if (!Objects.equals(monId, "") && info != null) {
            foundMonument.addInformationObject(info);
            monumentRepository.save(foundMonument);
        }
    }

    void editMonument(String id, Monument monument) {
        monumentRepository.findById(id).ifPresent(mon -> {
            monument.setId(id);
            monumentRepository.save(monument);
        });
    }

    void deleteMonument(String id) {
        monumentRepository.deleteById(id);
    }

    void saveImage(InputStream stream, String monumentID) {
        gridFsTemplate.store(stream, monumentID, MediaType.IMAGE_JPEG_VALUE);
    }

    GridFsResource getImageForMonumentId(String id) {
        return gridFsTemplate.getResource(id);
    }

    private String findBestMatch(Map<String, Long> questionMap) {
        return Collections.max(questionMap.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();
    }

    private long countMatches(String inputQuestion, String question) {
        return Arrays.stream(inputQuestion.split(" "))
                       .filter(keyword -> hasMatch(question, keyword))
                       .count();
    }

    private boolean hasMatch(String question, String keyword) {
        return question.matches("(?i:.*" + keyword + ".*)");
    }
}
