package com.placementserver.placementserver.notification;

import jakarta.persistence.*;

@Entity
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private long subscriptionid;

    @Column(columnDefinition = "TEXT")
    private String endpoint;

    @Column(columnDefinition = "TEXT")
    private String p256dh;

    @Column(columnDefinition = "TEXT")
    private String auth;

    @Column(name="role")
    private String role;

    public long getSubscriptionid() {
        return subscriptionid;
    }

    public void setSubscriptionid(long subscriptionid) {
        this.subscriptionid = subscriptionid;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getP256dh() {
        return p256dh;
    }

    public void setP256dh(String p256dh) {
        this.p256dh = p256dh;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Notifications() {
        super();
    }

    public Notifications(String role, String p256dh, String auth, String endpoint) {
        super();
        this.role = role;
        this.p256dh = p256dh;
        this.auth = auth;
        this.endpoint = endpoint;
    }
}
