package com.placementserver.placementserver.event;

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
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    private static final String UPLOAD_DIR = "uploads/events/";

    @PostMapping("/addevent")
    public ResponseEntity<DataResponse<String>> addEvent(
            @RequestParam("event-title") String eventTitle,
            @RequestParam("event-description") String eventDescription,
            @RequestParam("event-location") String eventLocation,
            @RequestParam("event-date-from") String eventDateFrom,
            @RequestParam(value = "event-date-to", required = false) String eventDateTo,
            @RequestParam("event-time-from") String eventTimeFrom,
            @RequestParam(value = "event-time-to", required = false) String eventTimeTo,
            @RequestParam("event-apply-link") String applyLink,
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
                applyLink,
                eventContent,
                eventImages,
                postedBy);

        if(response.getCondition().equals("Success")) {
            return new ResponseEntity<>(response, HttpStatus.valueOf(201));
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(400));
        }
    }

    @GetMapping("/getevent")
    public ResponseEntity<DataResponse<List<Event>>> getEvent(@RequestParam("offset") long offset) {

        DataResponse<List<Event>> response = eventService.getEvent(offset);

        return new ResponseEntity<>(response, HttpStatus.valueOf(200));
    }

    @GetMapping("/getimage/{imgname}")
    public ResponseEntity<Resource> getImage(@PathVariable("imgname") String filename) throws IOException {
        String UPLOAD_DIR = "uploads/events/";
        Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }


        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @DeleteMapping("/deleteevent/{eventid}")
    public ResponseEntity<DataResponse<String>> deleteEvent(@PathVariable long eventid) {

        DataResponse<String> response = eventService.deleteEvent(eventid);

        if(response.getCondition().equals("Success")) {
            return new ResponseEntity<DataResponse<String>>(response, HttpStatus.valueOf(200));
        }
        else {
            return new ResponseEntity<DataResponse<String>>(response, HttpStatus.valueOf(404));
        }
    }
}
