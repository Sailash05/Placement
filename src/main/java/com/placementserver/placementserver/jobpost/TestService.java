package com.placementserver.placementserver.jobpost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class TestService {

    @Autowired
    private TestRepository repo;

    public String fileStore(String name, MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        String fileName = file.getOriginalFilename();
        Test test = new Test();
        test.setName(name);
        test.setFile(fileContent);
        test.setFileName(fileName);
        repo.save(test);
        return "Success";
    }

    // Get file by id
    public Test getPost(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
    }
}