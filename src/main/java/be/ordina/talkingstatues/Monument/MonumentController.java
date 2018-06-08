package be.ordina.talkingstatues.Monument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.MediaType;


import java.io.*;
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

    @GetMapping(value = "/{language}/monuments/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String language, @PathVariable String id) throws IOException {
        Monument m = monumentService.getStatueByIdAndLanguage(id,language);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/images/" + m.getPicture());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int read;
        while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
            baos.write(buffer, 0, read);
        }
        baos.flush();
        return  baos.toByteArray();
    }
}
