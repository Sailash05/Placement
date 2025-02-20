package com.placementserver.placementserver.jobpost;


import com.placementserver.placementserver.event.Event;
import com.placementserver.placementserver.event.EventFile;
import com.placementserver.placementserver.notification.NotificationService;
import com.placementserver.placementserver.responses.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class JobPostService {

    private static final String UPLOAD_DIR = "uploads/jobpost/";

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private JobPostFileRepository jobPostFileRepository;

    @Autowired
    private NotificationService notificationService;

    public DataResponse<String> addJobPost(String jobPostTitle, String jobPostDescription, String applyLink, String jobPostContent, MultipartFile[] jobPostImages, String postedBy) {

        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            JobPost jobPost = new JobPost(jobPostTitle, jobPostDescription, jobPostContent, postedBy, applyLink);


            jobPostRepository.save(jobPost);

            if (jobPostImages != null) {
                for (MultipartFile file : jobPostImages) {
                    String fileName = UUID.randomUUID().toString()+ "_"+ file.getOriginalFilename();
                    if(fileName.charAt(fileName.length()-1) == '_') {
                        continue;
                    }
                    File directory = new File(UPLOAD_DIR);
                    if(!directory.exists()) {
                        directory.mkdirs();
                    }
                    Path filePath = Paths.get(UPLOAD_DIR+fileName);
                    Files.write(filePath, file.getBytes());

                    JobPostFile jobPostFile = new JobPostFile("uploads/jobpost/"+fileName, fileName, jobPost);
                    jobPostFileRepository.save(jobPostFile);
                }
            }
            notificationService.sendNotification("New Job Post!", jobPostTitle,"STUDENT");
            return new DataResponse<String>("Success","Job Post Added Successfully","");
        }
        catch (IOException e) {
            return new DataResponse<String>("Failed","Error Saving Files: "+e.getMessage(),"");
        }

    }

    public DataResponse<List<JobPost>> getJobPost(long offset) {
        List<JobPost> jobPosts = jobPostRepository.getJobPost(offset);
        for(JobPost i: jobPosts) {
            //List<String> fileName = eventFileRepository.getFiles(i.getEventid());
            List<String> fileName = jobPostFileRepository.getFiles(i.getJobPostid());
            //i.setEventFiles(fileName);
            i.setJobPostFiles(fileName);
        }
        return new DataResponse<List<JobPost>>("Success","Job Post received",jobPosts);

    }

    public DataResponse<String> deleteEvent(long jobpostid) {

        List<String> jobPostFiles = jobPostFileRepository.getFileName(jobpostid);
        for(String jobPostFile: jobPostFiles) {
            String filePath = "uploads/jobpost/" + jobPostFile;
            File file = new File(filePath);
            if(file.exists()) {
                file.delete();
            }
        }
        int deleteEventFileStatus = jobPostFileRepository.deleteJobPostFile(jobpostid);
        int deleteEventStatus = jobPostRepository.deleteJobPost(jobpostid);
        if(deleteEventStatus >= 1) {
            return new DataResponse<String>("Success","Job Post Deleted Successfully", "");
        }
        return new DataResponse<>("Failed", "Job Post can't be Deleted", "");

    }
}
