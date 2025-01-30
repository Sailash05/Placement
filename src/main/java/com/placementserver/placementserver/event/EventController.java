package com.placementserver.placementserver.event;

import com.placementserver.placementserver.responses.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/addevent")
    public ResponseEntity<DataResponse<String>> addEvent(
            @RequestParam("event-title") String eventTitle,
            @RequestParam("event-description") String eventDescription,
            @RequestParam("event-location") String eventLocation,
            @RequestParam("event-date-from") String eventDateFrom,
            @RequestParam(value = "event-date-to", required = false) String eventDateTo,
            @RequestParam("event-time-from") String eventTimeFrom,
            @RequestParam(value = "event-time-to", required = false) String eventTimeTo,
            @RequestParam("event-content") String eventContent,
            @RequestParam(value = "event-images", required = false) MultipartFile[] eventImages,
            @RequestParam("posted-by") String postedBy
    ) {

        DataResponse<String> response= eventService.addEvent(eventTitle,
                eventDescription,
                eventLocation,
                eventDateFrom,
                eventDateTo,
                eventTimeFrom,
                eventTimeTo,
                eventContent,
                eventImages,
                postedBy);

        if(response.getCondition().equals("Success")) {
            return new ResponseEntity<>(response, HttpStatus.valueOf(201));
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(400));
        }
        /*
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Save uploaded files
           if (eventImages != null) {
                for (MultipartFile file : eventImages) {
                    Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                    Files.write(filePath, file.getBytes());
                }
            }

            // Process other event details (You can save this to a database)
            String responseMessage = "Event '" + eventTitle + "' added successfully!";
            return ResponseEntity.ok(responseMessage);

        */

    }
}
