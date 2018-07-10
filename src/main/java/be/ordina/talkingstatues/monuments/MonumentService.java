package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.routes.RouteRequest;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MonumentService {

    private final MonumentRepository monumentRepository;
    private final GridFsTemplate gridFsTemplate;

    public MonumentService(MonumentRepository statueRepository, GridFsTemplate gridFsTemplate) {
        this.monumentRepository = statueRepository;
        this.gridFsTemplate = gridFsTemplate;
    }

    public void initializeData(Monument[] initialData) {
        monumentRepository.deleteAll();
        for (Monument m : initialData) {
            monumentRepository.save(m);
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("images/" + m.getPicture());
            saveImage(inputStream, m.getId());
       //     System.out.println(m.toString() + " has been saved.\n");
        }
    }

    public List<Monument> getAllMonuments(){
        return monumentRepository.findAll();
    }

    public List<Monument> getSortedMonuments(RouteRequest routeRequest){
        List<Monument> sortedMonuments =
                routeRequest.getLocations().stream()
                        .map(monumentRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList())
                ;
        return sortedMonuments;
    }



    Monument getMonumentById(String id){
        return monumentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Monument with id: "+id+" does not exist"));
    }

    Information getMonumentInformationByIdAndLanguage(String id, String language){
        Monument monument = monumentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Monument with id: "+id+" does not exist"));
        return monument.getInformation().stream()
                .filter(information -> information.getLanguage().toString().equalsIgnoreCase(language))
                .findFirst().orElseThrow(()-> new RuntimeException("Requested language is not supported"));
    }

    Question getMonumentQuestionByIdAndLanguageAndQuestion(String id, String language,String question) {
        List<Question> questions = getMonumentInformationByIdAndLanguage(id,language).getQuestion();
        Map<Question,Long> questionMap = new HashMap<>();
        questions.forEach(question1 -> questionMap.put(question1,
                Arrays.stream(question.split(" "))
                        .filter(keyword -> question1.getQuestion().matches("(?i:.*"+keyword+".*)"))
                        .count()));
        return Collections.max(questionMap.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();
    }


    List<Monument> getRandomSelection(String area, String language){
        List<Monument> monuments = monumentRepository.findAllByArea(area).stream()
                .peek(monument -> monument.setInformation(monument.getInformation().stream()
                        .filter(information -> information.getLanguage().toString().equalsIgnoreCase(language))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
        Collections.shuffle(monuments);
        return monuments.size() >= 10 ? IntStream.range(0,10).mapToObj(monuments::get).collect(Collectors.toList()) : monuments;
    }


    List<Monument> findAll(){
        return monumentRepository.findAll();
    }

    Monument addMonument(Monument monument){
        if(monument.getId()==null){
            return monumentRepository.save(monument);
        }else {
            throw new RuntimeException("the new monument already has an id");
        }
    }

    void addInformationToMonument(String monId, Information info){
        Monument foundMonument = getMonumentById(monId);

        if(monId != "" && info != null){
            foundMonument.addInformationObject(info);
            monumentRepository.save(foundMonument);
        }
    }

    void putMonument(String id, Monument monument){
        monumentRepository.findById(id).ifPresent(mon-> {
            monument.setId(id);
            monumentRepository.save(monument);
        });
    }

    void deleteMonument(String id){
        monumentRepository.deleteById(id);
    }

    void saveImage(InputStream stream, String id){
        gridFsTemplate.store(stream, id, MediaType.IMAGE_JPEG_VALUE);
    }

    GridFsResource getImageForMonumentId(String id){
        return gridFsTemplate.getResource(id);
    }
}
