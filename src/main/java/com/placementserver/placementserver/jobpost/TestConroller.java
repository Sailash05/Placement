package com.placementserver.placementserver.jobpost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class TestConroller {

    @Autowired
    private TestService testService;

    @PostMapping("/postfile")
    public String postFile(@RequestParam("name") String name,@RequestParam("file") MultipartFile file) throws IOException {
        return testService.fileStore(name, file);
    }

    @GetMapping("/getpost")
    public ResponseEntity<ByteArrayResource> getPost(@RequestParam("id") Long id) {
        Test test = testService.getPost(id);
        ByteArrayResource resource = new ByteArrayResource(test.getFile());
        String fileName = test.getFileName();
        String contentType = "application/octet-stream";

        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            contentType = "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            contentType = "image/png";
        } else if (fileName.endsWith(".pdf")) {
            contentType = "application/pdf";
        } else if (fileName.endsWith(".xls")) {
            contentType = "application/vnd.ms-excel";  // For .xls files
        } else if (fileName.endsWith(".xlsx")) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";  // For .xlsx files
        }


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, contentType)  // Adjust if you know the content type (e.g., "image/jpeg")
                .contentLength(test.getFile().length)
                .body(resource);
    }

}
