package com.placementserver.placementserver.event;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="event")
public class Event {

    @Id
    @Column(name="eventid")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long eventid;

    @Column(name="event_title")
    private String eventTitle;

    @Column(name="event_description")
    private String eventDescription;

    @Column(name="event_date_from")
    private String eventDateFrom;

    @Column(name="event_date_to")
    private String eventDateTo;

    @Column(name="event_time_from")
    private String eventTimeFrom;

    @Column(name="event_time_to")
    private String eventTimeTo;

    @Column(name="event_content", columnDefinition = "LONGTEXT")
    private String eventContent;

    @Column(name="posted_by")
    private String postedBy;

    @Column(name="apply_link")
    private String applyLink;

    @Column(name="event_location")
    private String eventLocation;

    @Transient
    private List<String> eventFiles;


    public long getEventid() {
        return eventid;
    }

    public void setEventid(long eventid) {
        this.eventid = eventid;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDateFrom() {
        return eventDateFrom;
    }

    public void setEventDateFrom(String eventDateFrom) {
        this.eventDateFrom = eventDateFrom;
    }

    public String getEventDateTo() {
        return eventDateTo;
    }

    public void setEventDateTo(String eventDateTo) {
        this.eventDateTo = eventDateTo;
    }

    public String getEventTimeFrom() {
        return eventTimeFrom;
    }

    public void setEventTimeFrom(String eventTimeFrom) {
        this.eventTimeFrom = eventTimeFrom;
    }

    public String getEventTimeTo() {
        return eventTimeTo;
    }

    public void setEventTimeTo(String eventTimeTo) {
        this.eventTimeTo = eventTimeTo;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public List<String> getEventFiles() {
        return eventFiles;
    }

    public void setEventFiles(List<String> eventFiles) {
        this.eventFiles = eventFiles;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public void setApplyLink(String applyLink) {
        this.applyLink = applyLink;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public Event() {
        super();
    }

    public Event(String postedBy,
                 String eventContent,
                 String eventTimeTo,
                 String eventTimeFrom,
                 String eventDateTo,
                 String eventDateFrom,
                 String eventDescription,
                 String eventTitle,
                 String applyLink,
                 String eventLocation) {
        super();
        this.postedBy = postedBy;
        this.eventContent = eventContent;
        this.eventTimeTo = eventTimeTo;
        this.eventTimeFrom = eventTimeFrom;
        this.eventDateTo = eventDateTo;
        this.eventDateFrom = eventDateFrom;
        this.eventDescription = eventDescription;
        this.eventTitle = eventTitle;
        this.applyLink = applyLink;
        this.eventLocation = eventLocation;
    }

}
