package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.appusers.AppUser;
import be.ordina.talkingstatues.appusers.AppUserRepository;
import be.ordina.talkingstatues.visits.Visit;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;


@RestController
@RequestMapping("/monuments")
public class MonumentController {

    private final MonumentService monumentService;
    private final AppUserRepository appUserRepository;

    public MonumentController(MonumentService monumentService, AppUserRepository appUserRepository) {
        this.monumentService = monumentService;
        this.appUserRepository = appUserRepository;
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

    @GetMapping(value = "/{id}/image64")
    ResponseEntity getImageBase64(@PathVariable String id){
        GridFsResource res = monumentService.getImageForMonumentId(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(new Image(encoder(res)));
    }

    private String encoder(GridFsResource resource) {
        String base64Image = "";
        try {
            InputStream imageInFile = resource.getInputStream();
            byte imageData[] = new byte[1024 * 1024];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
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
    List<SwipeMonument> getRandomSelection(@RequestParam String area, @RequestParam("lang") String language){
        return monumentService.getRandomSelection(area,language);
    }


    //ADMIN

    @GetMapping(value = "")
    List<Monument> getMonuments(){
        return monumentService.findAll();
    }

    @GetMapping(value = "/short") @JsonView(Aspects.MinimalMonumentAspect.class)
    List<Monument> getMonumentsShort(){
        return monumentService.findAll();
    }

    @PostMapping(value = "")
    Monument addMonuments(@RequestBody Monument monument){
        return monumentService.addMonument(monument);
    }

    @PutMapping(value = "/{id}")
    void addMonuments(@PathVariable String id, @RequestBody Monument monument){
        monument.setId(id);
        monumentService.putMonument(id,monument);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file, @PathVariable String id){
        try {
            monumentService.saveImage(file.getInputStream(),id);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable String id){
        monumentService.deleteMonument(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/visited")
    public void addVisit(@PathVariable String monId, Authentication auth){

        AppUser foundUser = appUserRepository.findByHandle(auth.name()).orElseThrow(RuntimeException::new);

        Visit newVisit = new Visit(foundUser.getId(), monId);

        foundUser.addVisit(newVisit);
    }
}
