package be.ordina.talkingstatues.monuments;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

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

    @PutMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    void uploadImage(@RequestBody InputStream file, @PathVariable String id){
        this.monumentService.saveImage(file, id);
    }

}
