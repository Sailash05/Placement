package com.placementserver.placementserver.event;

import jakarta.persistence.*;

import java.nio.file.Path;

@Entity
@Table(name = "event_file")
public class EventFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventfileid")
    private long fileId;

    @ManyToOne
    @JoinColumn(name = "eventid", referencedColumnName = "eventid")
    private Event event;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "filepath")
    private String filePath;


    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public EventFile() {
        super();
    }

    public EventFile(String filePath, String fileName, Event event) {
        super();
        this.filePath = filePath;
        this.fileName = fileName;
        this.event = event;
    }
}
