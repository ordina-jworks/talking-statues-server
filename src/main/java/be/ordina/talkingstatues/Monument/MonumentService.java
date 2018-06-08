package be.ordina.talkingstatues.Monument;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonumentService {

    private final MonumentRepository monumentRepository;

    public MonumentService(MonumentRepository statueRepository) {
        this.monumentRepository = statueRepository;

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
}
