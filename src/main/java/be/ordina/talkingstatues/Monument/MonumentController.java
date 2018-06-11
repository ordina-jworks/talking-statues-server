package be.ordina.talkingstatues.Monument;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@RestController
@RequestMapping("/monuments")
public class MonumentController {

    private final MonumentService monumentService;

    public MonumentController(MonumentService monumentService) {
        this.monumentService = monumentService;
    }

    @GetMapping(value = "/{id}", produces = {"application/vnd.ordina.v1.0+json"})
    Monument getMonument(@PathVariable String id){
        return monumentService.getMonumentById(id);
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity getImage(@PathVariable String id){
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(monumentService.getImageForMonumentId(id));
    }

    @GetMapping(value = "/{id}/information", produces = {"application/vnd.ordina.v1.0+json"})
    Information getMonumentInformation(@PathVariable String id, @RequestParam("lang") String language){
        Information information = monumentService.getMonumentInformationByIdAndLanguage(id,language);
        information.setQuestion(null);
        return information;
    }
    @GetMapping(value = "/{id}/questions", produces = {"application/vnd.ordina.v1.0+json"})
    Question getMonumentQuestions(@PathVariable String id,
                                     @RequestParam("lang") String language,
                                     @RequestParam("question") String question){
        return monumentService.getMonumentQuestionByIdAndLanguageAndQuestion(id,language,question);
    }

    @GetMapping(value = "/selection", produces = {"application/vnd.ordina.v1.0+json"})
    List<SwipeMonument> getTinderSelection(@RequestParam String area, @RequestParam("lang") String language){
        return monumentService.getTinderSelection(area,language);
    }


    //ADMIN

    @GetMapping(value = "")
    List<Monument> getMonuments(){
        return monumentService.findAll();
    }

    @PostMapping(value = "")
    Monument addMonuments(Monument monument){
        return monumentService.addMonument(monument);
    }

    @PutMapping(value = "/{id}")
    void addMonuments(@PathVariable String id, @RequestBody Monument monument){
        monument.setId(id);
        monumentService.putMonument(id,monument);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity uploadProfilePic(@RequestParam("file") MultipartFile file, @PathVariable String id){
        monumentService.saveImage(file,id);
        return ResponseEntity.ok().build();
    }


}
