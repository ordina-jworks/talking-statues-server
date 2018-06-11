package be.ordina.talkingstatues.Monument;

import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
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

    Monument getMonumentByIdAndLanguage(String id, String language){
        Monument monument = monumentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Monument with id: "+id+" does not exist"));
        return filterOnLanguage(monument,language);
    }
    Monument getMonumentById(String id){
        return monumentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Monument with id: "+id+" does not exist"));
    }
    List<Monument> getMonumentsByAreaAndLanguage(String area, String language){
        return monumentRepository.findAllByArea(area).stream()
        .peek(monument -> filterOnLanguage(monument,language)).collect(Collectors.toList());
    }
    Monument filterOnLanguage(Monument monument, String language){
        monument.setInformation(monument.getInformation().stream()
                .filter(mon -> mon.getLanguage().toString().equalsIgnoreCase(language))
                .collect(Collectors.toList()));
        return monument;
    }

    List<Monument> getTinderSelection(String area,String language){
        List<Monument> monuments = getMonumentsByAreaAndLanguage(area,language);
        Collections.shuffle(monuments);
        if(monuments.size() >=10){
            return IntStream.range(0,10)
                    .mapToObj(monuments::get)
                    .collect(Collectors.toList());
        }else {
            return monuments;
        }
    }

    List<Monument> findAllForLanguage(String language){
        return monumentRepository.findAll().stream()
                .peek(monument -> monument.setInformation(
                        monument.getInformation().stream()
                        .filter(mon -> mon.getLanguage().toString().equalsIgnoreCase(language))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
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
