package be.ordina.talkingstatues.Monument;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MonumentController {

    private final MonumentService monumentService;

    public MonumentController(MonumentService monumentService) {
        this.monumentService = monumentService;
    }

    @GetMapping(value = "/{language}/monuments/{id}", produces = {"application/vnd.ordina.v1.0+json"})
    Monument getStatue_v1(@PathVariable String language, @PathVariable String id){
        return monumentService.getStatueByIdAndLanguage(id,language);
    }
    @GetMapping(value = "/{language}/monuments/{id}", produces = {"application/vnd.ordina.v2.0+json"})
    Monument getStatue_v2(@PathVariable String language, @PathVariable String id){
        return monumentService.getStatueByIdAndLanguage(id,language);
    }
    @GetMapping(value = "/{language}/monuments", produces = {"application/vnd.ordina.v1.0+json"})
    List<Monument> getStatues(@PathVariable String language){
        return monumentService.findAllForLanguage(language);
    }
}
