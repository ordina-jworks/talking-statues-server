package be.ordina.talkingstatues.monuments;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final MonumentService monumentService;

    public ImageController(MonumentService monumentService) {
        this.monumentService = monumentService;
    }

    /**
     * Proxy Service to expose image fetch to an URL without security requirements
     * @param id
     * @return Image of a Monument
     */
    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity getImage(@PathVariable String id){
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(monumentService.getImageForMonumentId(id));
    }

}
