package com.example.email.Server.emailContent;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.*;
import java.util.List;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileResource {

    /*
    * upload files, it accepts post request
    * we're trying to create some resource on the server
    * we return a list of those file names so that we can download them again
    * */
    public void uploadFiles(List<MultipartFile> multipartFiles, String DIRECTORY){
        try {
            for(MultipartFile file : multipartFiles) {
                String filename = StringUtils.cleanPath(file.getOriginalFilename());
                Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
                System.out.println(fileStorage);
                Files.copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // Define a method to download files
//    @GetMapping("download/{filename}")
////    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
////        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
////        if(!Files.exists(filePath)) {
////            throw new FileNotFoundException(filename + " was not found on the server");
////        }
////        Resource resource = new UrlResource(filePath.toUri());
////        HttpHeaders httpHeaders = new HttpHeaders();
////        httpHeaders.add("File-Name", filename);
////        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
////        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
////                .headers(httpHeaders).body(resource);
////    }
}
