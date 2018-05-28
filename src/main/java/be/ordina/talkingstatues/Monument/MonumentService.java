package be.ordina.talkingstatues.Monument;

import be.ordina.talkingstatues.Monument.Model.Monument;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonumentService {

    private final MonumentRepository monumentRepository;

    public MonumentService(MonumentRepository statueRepository) {
        this.monumentRepository = statueRepository;
    }

    Monument getStatueByIdAndLanguage(String id,String language){
        Monument monument =monumentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Monument with id: "+id+" does not exist"));
        monument.setInformation(monument.getInformation().stream()
                .filter(mon -> mon.getLanguage().toString().equals(language))
                .collect(Collectors.toList()));
        return monument;
    }

    List<Monument> findAll(){
        return monumentRepository.findAll();
    }
}
