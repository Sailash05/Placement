package com.placementserver.placementserver.test;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

@Service
public class PushNotificationService {

    private final String publicKey;
    private final String privateKey;

    public PushNotificationService() {
        Dotenv dotenv = Dotenv.load();
        this.publicKey = dotenv.get("VAPID_PUBLIC_KEY");
        this.privateKey = dotenv.get("VAPID_PRIVATE_KEY");
    }

    private PushService pushService;
    private final List<Subscription> subscriptions = new ArrayList<>();

    @PostConstruct
    public void init() throws GeneralSecurityException {
        try {
            Security.addProvider(new BouncyCastleProvider());
            pushService = new PushService(publicKey, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public String getPublicKey() {
        return publicKey;
    }*/

    public void subscribe(Subscription subscription) {
        if (!subscriptions.contains(subscription)) {
            subscriptions.add(subscription);

        } else {

        }
    }

    /*public void sendPushNotification(String message) {
        for (Subscription subscription : subscriptions) {
            try {
                Notification notification = new Notification(subscription, message);
                pushService.send(notification);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    public void sendPushNotification(String message) {
        System.out.println(subscriptions.size());
        for (Subscription subscription : subscriptions) {
            try {
                // Wrap the message into a JSON object if needed
                String jsonMessage = "{\"message\": \"" + "Total : "+subscriptions.size() + "\", \"title\": \"New Notification\"}";

                // Create notification with subscription and JSON payload
                Notification notification = new Notification(subscription, jsonMessage);

                // Send the push notification
                pushService.send(notification);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error sending push notification: " + e.getMessage());
            }
        }
    }
}