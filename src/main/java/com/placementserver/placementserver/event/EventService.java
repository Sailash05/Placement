package com.placementserver.placementserver.event;

import com.placementserver.placementserver.responses.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class EventService {

    private static final String UPLOAD_DIR = "uploads/events/";

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventFileRepository eventFileRepository;

    public DataResponse<String> addEvent(String eventTitle,
                                         String eventDescription,
                                         String eventLocation,
                                         String eventDateFrom,
                                         String eventDateTo,
                                         String eventTimeFrom,
                                         String eventTimeTo,
                                         String eventContent,
                                         MultipartFile[] eventImages,
                                         String postedBy) {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            Event event = new Event(postedBy,
                    eventContent,
                    eventTimeTo,
                    eventTimeFrom,
                    eventDateTo,
                    eventDateFrom,
                    eventDescription,
                    eventTitle);

            eventRepository.save(event);

            if (eventImages != null) {
                for (MultipartFile file : eventImages) {
                    String fileName = UUID.randomUUID().toString()+ "_"+ file.getOriginalFilename();
                    Path filePath = Paths.get(UPLOAD_DIR + fileName);
                    Files.write(filePath, file.getBytes());

                    EventFile eventFile = new EventFile(filePath.toString(), fileName, event);
                    eventFileRepository.save(eventFile);
                }
            }
            return new DataResponse<String>("Success","Event added successfully","");
        }
        catch (IOException e) {
            return new DataResponse<String>("Failed","Error saving files: "+e.getMessage(),"");
        }
    }
}
