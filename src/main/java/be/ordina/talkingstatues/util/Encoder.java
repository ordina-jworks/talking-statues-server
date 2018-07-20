package be.ordina.talkingstatues.util;

import org.springframework.core.io.InputStreamResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class Encoder {

    public static String encode(InputStreamResource resource) {
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
}
