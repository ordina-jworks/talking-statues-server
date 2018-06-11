package be.ordina.talkingstatues.Monument;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



@RestController
@RequestMapping("/monuments")
public class MonumentController {

    private final MonumentService monumentService;
    private final Random random = new Random();

    public MonumentController(MonumentService monumentService) {
        this.monumentService = monumentService;
    }
    @GetMapping(value = "/{language}/{id}", produces = {"application/vnd.ordina.v1.0+json"})
    Monument getMonument(@PathVariable String id, @PathVariable String language){
        return monumentService.getMonumentByIdAndLanguage(id,language);
    }

    @GetMapping(value = "/{language}", produces = {"application/vnd.ordina.v1.0+json"})
    List<Monument> getTinderOptions(@RequestParam String area, @PathVariable String language){
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

    @GetMapping(value = "/{id}")
    Monument getMonument(@PathVariable String id){
        return monumentService.getMonumentById(id);
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

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity getImage(@PathVariable String id){
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(monumentService.getImageForMonumentId(id));
    }
}
