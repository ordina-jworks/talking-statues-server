package be.ordina.talkingstatues.Monument;

import be.ordina.talkingstatues.Monument.Model.Information;
import be.ordina.talkingstatues.Monument.Model.Language;
import be.ordina.talkingstatues.Monument.Model.Monument;
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
        Monument monument1 = new Monument(Collections.singletonList(new Information(Language.NL, "Antoon van Dyck", "...", null)),1.1,1.2,"Meir-Leysstraat-Jezusstraat");
        Monument monument2 = new Monument(Collections.singletonList(new Information(Language.NL,"Hendrik Conscience","...",null)),1.1,1.2,"Hendrik Conscienceplein");

        monumentRepository.save(monument1);
        monumentRepository.save(monument2);
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
