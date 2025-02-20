package com.placementserver.placementserver.jobpost;

import com.placementserver.placementserver.event.Event;
import com.placementserver.placementserver.responses.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/jobpost")
public class JobPostController {

    @Autowired
    JobPostService jobPostService;

    private static final String UPLOAD_DIR = "uploads/jobpost/";

    @PostMapping("/addjobpost")
    public ResponseEntity<DataResponse<String>> addJobPost(
            @RequestParam("job-post-title") String jobPostTitle,
            @RequestParam("job-post-description") String jobPostDescription,
            @RequestParam("job-post-apply-link") String applyLink,
            @RequestParam("job-post-content") String jobPostContent,
            @RequestParam(value = "job-post-images", required = false) MultipartFile[] jobPostImages,
            @RequestParam("posted-by") String postedBy
    ) {

        DataResponse<String> response = jobPostService.addJobPost(
                jobPostTitle, jobPostDescription,
                applyLink, jobPostContent,
                jobPostImages, postedBy);
        if(response.getCondition().equals("Success")) {
            return new ResponseEntity<>(response, HttpStatus.valueOf(201));
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(400));
        }
    }

    @GetMapping("/getjobpost")
    public ResponseEntity<DataResponse<List<JobPost>>> getJobPost(@RequestParam("offset") long offset) {

        DataResponse<List<JobPost>> response = jobPostService.getJobPost(offset);

        return new ResponseEntity<>(response, HttpStatus.valueOf(200));
    }

    @GetMapping("/getimage/{imgname}")
    public ResponseEntity<Resource> getImage(@PathVariable("imgname") String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new UrlResource(filePath.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @DeleteMapping("/deletejobpost/{jobpostid}")
    public ResponseEntity<DataResponse<String>> deleteEvent(@PathVariable long jobpostid) {

        DataResponse<String> response = jobPostService.deleteEvent(jobpostid);

        if(response.getCondition().equals("Success")) {
            return new ResponseEntity<DataResponse<String>>(response, HttpStatus.valueOf(200));
        }
        else {
            return new ResponseEntity<DataResponse<String>>(response, HttpStatus.valueOf(404));
        }
    }

}
