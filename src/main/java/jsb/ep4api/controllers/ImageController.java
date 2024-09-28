package jsb.ep4api.controllers;

import io.jsonwebtoken.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static jsb.ep4api.constants.Constants.DEFAULT_UPLOAD_IMAGE_DIR;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final Path imageUploadPath = Paths.get(DEFAULT_UPLOAD_IMAGE_DIR);

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException, MalformedURLException {
        Path imagePath = imageUploadPath.resolve(filename);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
