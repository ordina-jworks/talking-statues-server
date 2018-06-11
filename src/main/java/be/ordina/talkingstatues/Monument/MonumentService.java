package be.ordina.talkingstatues.Monument;

import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        monumentRepository.deleteAll();
        for (Monument m : MonumentInitialData.DATA) {
            monumentRepository.save(m);
        }
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

    List<Information> getTinderSelection(String area, String language){
        List<Information> informationList = monumentRepository.findAllByArea(area).stream()
                .map(Monument::getInformation)
                .flatMap(Collection::stream)
                .filter(monument -> monument.getLanguage().toString().equalsIgnoreCase(language))
                .collect(Collectors.toList());
        Collections.shuffle(informationList);
        if(informationList.size() >=10){
            return IntStream.range(0,10)
                    .mapToObj(informationList::get)
                    .collect(Collectors.toList());
        }else {
            return informationList;
        }
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

    void putMonument(String id, Monument monument){
        monumentRepository.findById(id).ifPresent(mon-> {
            monument.setId(id);
            monumentRepository.save(monument);
        });
    }
    void saveImage(MultipartFile file, String id){
        try {
            gridFsTemplate.store(file.getInputStream(),id,file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    GridFsResource getImageForMonumentId(String id){
        return gridFsTemplate.getResource(id);
    }
}
