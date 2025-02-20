package com.placementserver.placementserver.notification;

import com.placementserver.placementserver.responses.DataResponse;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {

    private final String publicKey;
    private final String privateKey;

    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationService() {
        Dotenv dotenv = Dotenv.load();
        this.publicKey = dotenv.get("VAPID_PUBLIC_KEY");
        this.privateKey = dotenv.get("VAPID_PRIVATE_KEY");
    }

    private PushService pushService;

    @PostConstruct
    public void init() throws GeneralSecurityException {
        try {
            Security.addProvider(new BouncyCastleProvider());
            pushService = new PushService(publicKey, privateKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public DataResponse<String> subscribe(String role, Subscription subscription) {

        if(notificationRepository.findByEndpoint(subscription.endpoint) == null) {
            Notifications notification = new Notifications(role,
                    subscription.keys.p256dh, subscription.keys.auth, subscription.endpoint);
            notificationRepository.save(notification);
            return new DataResponse<>("Success","Subscription Added","");
        }
        else {
            return new DataResponse<>("Failed","Subscription Already Added","");
        }
    }

    public DataResponse<String> unsubscribe(String endpoint) {

        if (endpoint == null || endpoint.isEmpty()) {
            return new DataResponse<>("Failed", "Endpoint is required", "");
        }

        Notifications notification = notificationRepository.findByEndpoint(endpoint);
        if (notification != null) {
            notificationRepository.delete(notification);
            return new DataResponse<>("Success", "Subscription Removed", "");
        } else {
            return new DataResponse<>("Failed", "Subscription Not Found", "");
        }
    }

    public DataResponse<String> checkSubscriptionStatus(Subscription subscription) {
        Notifications storedSubscription = notificationRepository.findByEndpoint(subscription.endpoint);
        if (storedSubscription != null) {
            return new DataResponse<>("Success","Already Subscribed","");
        } else {
            return new DataResponse<>("Success","Have not yet subscribed","");
        }
    }

    public void sendNotification(String title, String message, String role) {
        List<Notifications> notifications= notificationRepository.getAllSubscription(role);
        if (notifications.isEmpty()) {
            //System.out.println("No active subscriptions found.");
            return;
        }
        String msgJSON = "{ \"title\": \""+title+"\", \"body\": \""+message+"\" }";
        for(Notifications subscription: notifications) {
            try {
                if (subscription.getEndpoint() == null || subscription.getAuth() == null || subscription.getP256dh() == null) {
                    //System.err.println("Invalid subscription data, skipping...");
                    continue;
                }

                Notification notification = new Notification(
                        subscription.getEndpoint(),
                        subscription.getP256dh(),
                        subscription.getAuth(),
                        msgJSON
                );


                pushService.send(notification);
                //System.out.println("Notification sent to: " + subscription.getEndpoint());
            } catch (GeneralSecurityException | IOException | JoseException e) {
                //System.err.println("Failed to send notification to: " + subscription.getEndpoint());
                e.printStackTrace();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
            /*PushService pushService = new PushService(publicKey, privateKey);
            Notification notification = new Notification(
                    subscription.getEndpoint(),
                    subscription.getKeys().getAuth(),
                    subscription.getKeys().getP256dh(),
                    message.getBytes()
            );

            pushService.send(notification);
            System.out.println("Notification sent successfully!");*/
    }


}
