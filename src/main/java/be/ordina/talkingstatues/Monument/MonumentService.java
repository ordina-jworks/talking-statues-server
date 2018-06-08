package be.ordina.talkingstatues.Monument;

import org.springframework.stereotype.Service;

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

    Monument getStatueByIdAndLanguage(String id,String language){
        Monument monument = monumentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Monument with id: "+id+" does not exist"));
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
}
