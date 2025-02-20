package com.placementserver.placementserver.test;

import nl.martijndwars.webpush.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/push")
public class PushNotificationControllerr {

    @Autowired
    private PushNotificationService pushNotificationService;

    @PostMapping("/subscribe")
    public String subscribe(@RequestBody Subscription subscription) {
        pushNotificationService.subscribe(subscription);
        return "Subscribed successfully";
    }

    @PostMapping("/send")
    public String sendNotification(@RequestParam String message) {
        //System.out.println("Notification send");
        pushNotificationService.sendPushNotification(message);
        return "Notification send!";
    }
}
