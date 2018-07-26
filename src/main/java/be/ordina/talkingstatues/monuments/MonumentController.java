package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.appusers.AppUser;
import be.ordina.talkingstatues.appusers.AuthService;
import be.ordina.talkingstatues.visits.Visit;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static java.util.Objects.isNull;


@RestController
@RequestMapping("/monuments")
public class MonumentController {

    private final MonumentService monumentService;
    private final AuthService authService;

    public MonumentController(MonumentService monumentService, AuthService authService) {
        this.monumentService = monumentService;
        this.authService = authService;
    }

    @GetMapping(value = "/{id}", produces = {"application/vnd.ordina.v1.0+json"})
    Monument getMonument(@PathVariable String id) {
        return monumentService.getMonumentById(id);
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity getImage(@PathVariable String id) {
        return ResponseEntity.ok()
                       .header(HttpHeaders.CONTENT_DISPOSITION)
                       .body(monumentService.getImageForMonumentId(id));
    }

    @GetMapping(value = "/{id}/image64")
    ResponseEntity getImageBase64(@PathVariable String id) {
        return ResponseEntity.ok()
                       .header(HttpHeaders.CONTENT_DISPOSITION)
                       .body(monumentService.getImageForMonumentId(id));
    }

    @GetMapping(value = "/{id}/information", produces = {"application/vnd.ordina.v1.0+json"})
    Information getMonumentInformation(@PathVariable String id, @RequestParam("lang") String language) {
        Information information = monumentService.getMonumentInformationByIdAndLanguage(id, language);
        information.setConversations(null);
        return information;
    }

    @GetMapping(value = "/{id}/questions", produces = {"application/vnd.ordina.v1.0+json"})
    String getMonumentAnswer(@PathVariable String id,
                             @RequestParam("lang") String language,
                             @RequestParam("question") String question) {
        return monumentService.findAnswer(id, language, question);
    }

    @GetMapping(value = "/selection", produces = {"application/vnd.ordina.v1.0+json"})
    @JsonView(Aspects.MinimalMonumentAspect.class)
    List<Monument> getRandomSelection(@RequestParam String area, @RequestParam("lang") String language) {
        return monumentService.getRandomSelection(area, language);
    }

    //ADMIN

    @PutMapping(value = "/{id}/information")
    public void addInformationToMonument(@PathVariable String monId, @RequestBody Information info) {
        monumentService.addInformationToMonument(monId, info);
    }

    @GetMapping(value = "/areas")
    List<String> getAllAreas() {
        return monumentService.getAllAreas();
    }

    @PutMapping(value = "/{id}/chat")
    public String chatWithMonument(@PathVariable String id, @RequestBody String userInput) {
        return "Hello, User."; //monumentService.chatWithMonument(id, userInput);
    }

    @GetMapping(value = "")
    List<Monument> getMonuments() {
        return monumentService.getAllMonuments();
    }

    @PutMapping(value = "")
    Monument addMonument(@RequestBody Monument monument) {
        return monumentService.addMonument(monument);
    }

    @PutMapping(value = "/{id}")
    void editMonument(@PathVariable String id, @RequestBody Monument monument) {
        if (isNull(monument.getId())) {
            throw new RuntimeException("invalid Monument");
        }
        if (!id.equals(monument.getId())) {
            throw new RuntimeException("ID's don't match");
        }
        monumentService.editMonument(id, monument);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file, @PathVariable String id) {
        try {
            monumentService.saveImage(file.getInputStream(), id);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable String id) {
        monumentService.deleteMonument(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/visited")
    public void addVisit(@PathVariable String monId, Principal principal) {
        AppUser foundUser = authService.getUserByHandle(principal.getName());

        foundUser.addVisit(new Visit(foundUser.getId(), monId));
    }
}
