package be.ordina.talkingstatues.Images;

import be.ordina.talkingstatues.Monument.MonumentRepository;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageRepository imageRepository;
    private final GridFsTemplate gridFsTemplate;
    private final MonumentRepository monumentRepository;

    public ImageController(ImageRepository imageRepository, GridFsTemplate gridFsTemplate, MonumentRepository monumentRepository) {
        this.imageRepository = imageRepository;
        this.gridFsTemplate = gridFsTemplate;
        this.monumentRepository = monumentRepository;
    }

    @PostMapping("/{id}")
    public ResponseEntity uploadProfilePic(@RequestParam("file") MultipartFile file, @PathVariable String id){
        //String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        try {
            gridFsTemplate.store(file.getInputStream(),id,file.getContentType());
            Optional<Image> existing = imageRepository.findByMonumentId(id);
            existing.ifPresent(imageRepository::delete);
            imageRepository.save(new Image(id,null));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity getImage(@PathVariable String id){
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION)
                    .body(gridFsTemplate.getResource(id)

                           /* imageRepository.findByMonumentId(id)
                            .orElseThrow(() ->new RuntimeException("no image for monument with id:" + id))
                            .getImage()*/
                    );
    }

}
