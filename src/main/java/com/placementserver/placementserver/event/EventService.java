package com.placementserver.placementserver.event;

import com.placementserver.placementserver.notification.NotificationService;
import com.placementserver.placementserver.responses.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private static final String UPLOAD_DIR = "uploads/events/";

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventFileRepository eventFileRepository;

    @Autowired
    private NotificationService notificationService;

    public DataResponse<String> addEvent(String eventTitle,
                                         String eventDescription,
                                         String eventLocation,
                                         String eventDateFrom,
                                         String eventDateTo,
                                         String eventTimeFrom,
                                         String eventTimeTo,
                                         String applyLink,
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
                    eventTitle,
                    applyLink,
                    eventLocation);

            eventRepository.save(event);

            if (eventImages != null) {
                for (MultipartFile file : eventImages) {
                    String fileName = UUID.randomUUID().toString()+ "_"+ file.getOriginalFilename();
                    if(fileName.charAt(fileName.length()-1) == '_') {
                        continue;
                    }
                    //Path filePath = Paths.get(UPLOAD_DIR + fileName);
                    //Files.write(filePath, file.getBytes());
                    File directory = new File(UPLOAD_DIR);
                    if(!directory.exists()) {
                        directory.mkdirs();
                    }
                    Path filePath = Paths.get(UPLOAD_DIR+fileName);
                    Files.write(filePath, file.getBytes());
                    //System.out.println(filePath.toString());

                    EventFile eventFile = new EventFile("uploads/events/"+fileName, fileName, event);
                    eventFileRepository.save(eventFile);
                }
            }
            notificationService.sendNotification("New Event!", eventTitle,"STUDENT");
            return new DataResponse<String>("Success","Event Added Successfully","");
        }
        catch (IOException e) {
            return new DataResponse<String>("Failed","Error Saving Files: "+e.getMessage(),"");
        }
    }

    public DataResponse<List<Event>> getEvent(long offset) {
        List<Event> events = eventRepository.getEvent(offset);
        for(Event i: events) {
            List<String> fileName = eventFileRepository.getFiles(i.getEventid());
            i.setEventFiles(fileName);
        }
        return new DataResponse<List<Event>>("Success","Events received",events);
    }

    public DataResponse<String> deleteEvent(long eventid) {

        List<String> eventFiles = eventFileRepository.getFileName(eventid);
        for(String eventFile: eventFiles) {
            String filePath = "uploads/events/"+eventFile;
            File file = new File(filePath);
            if(file.exists()) {
                file.delete();
            }
        }
        int deleteEventFileStatus = eventFileRepository.deleteEventFile(eventid);
        int deleteEventStatus = eventRepository.deleteEvent(eventid);
        if(deleteEventStatus >= 1) {
            return new DataResponse<String>("Success","Event Deleted Successfully", "");
        }
        return new DataResponse<>("Failed", "Event can't be Deleted", "");
    }
}
