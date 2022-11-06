package ua.org.smit.galleryweb.utilites;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileConverter {

    public static List<File> toFiles(List<MultipartFile> multiPartFiles) {
        List<File> files = new ArrayList<>();
        for (MultipartFile multi : multiPartFiles) {
            try {
                files.add(toFile(multi));
            } catch (IllegalStateException ex) {
                throw new RuntimeException("Cant convert file!" + ex);
            }
        }
        return files;
    }

    public static File toFile(MultipartFile multipart) {
        try {
            File convFile = new File(multipart.getOriginalFilename());
            multipart.transferTo(convFile);
            return convFile;
        } catch (IOException | IllegalStateException ex) {
            throw new IllegalStateException();
        }
    }
}
