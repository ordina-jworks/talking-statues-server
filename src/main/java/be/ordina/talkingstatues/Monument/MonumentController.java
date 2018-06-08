package be.ordina.talkingstatues.Monument;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
        List<Monument> monuments = monumentService.getMonumentsByAreaAndLanguage(area,language);
        Collections.shuffle(monuments);
        if(monuments.size() >=10){
            return IntStream.range(0,10)
                    .mapToObj(monuments::get)
                    .collect(Collectors.toList());
        }else {
            return monuments;
        }
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
    @GetMapping(value = "/{id}")
    Monument getMonument(@PathVariable String id){
        return monumentService.getMonumentById(id);
    }
    @PutMapping(value = "/{id}")
    void addMonuments(@PathVariable String id, @RequestBody Monument monument){
        monument.setId(id);
        monumentService.putMonument(id,monument);
    }
}
