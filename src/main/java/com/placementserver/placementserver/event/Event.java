package com.placementserver.placementserver.event;

import jakarta.persistence.*;

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

    @Column(name="event_content")
    private String eventContent;

    @Column(name="posted_by")
    private String postedBy;

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
                 String eventTitle) {
        super();
        this.postedBy = postedBy;
        this.eventContent = eventContent;
        this.eventTimeTo = eventTimeTo;
        this.eventTimeFrom = eventTimeFrom;
        this.eventDateTo = eventDateTo;
        this.eventDateFrom = eventDateFrom;
        this.eventDescription = eventDescription;
        this.eventTitle = eventTitle;
    }
}
