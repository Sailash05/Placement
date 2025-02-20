package com.placementserver.placementserver.notification;

import com.placementserver.placementserver.responses.DataResponse;
import nl.martijndwars.webpush.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/subscribe/{role}")
    public ResponseEntity<DataResponse<String>> subscribe(@PathVariable String role, @RequestBody Subscription subscription) {

        DataResponse<String> response = notificationService.subscribe(role, subscription);
        if(response.getCondition().equals("Success")) {
            return new ResponseEntity<>(response, HttpStatus.valueOf(201));
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(400));
        }
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<DataResponse<String>> unsubscribe(@RequestParam("endpoint") String endpoint) {

        DataResponse<String> response = notificationService.unsubscribe(endpoint);

        if(response.getCondition().equals("Success")) {
            return new ResponseEntity<>(response, HttpStatus.valueOf(201));
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(400));
        }
    }

    @PostMapping("/checksubscription")
    public ResponseEntity<DataResponse<String>> checkSubscriptionStatus(@RequestBody Subscription subscription) {

        DataResponse<String> response = notificationService.checkSubscriptionStatus(subscription);
        if(response.getCondition().equals("Success")) {
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(400));
        }
    }
}
