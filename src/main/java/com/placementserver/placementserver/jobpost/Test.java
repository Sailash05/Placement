package com.placementserver.placementserver.jobpost;

import jakarta.persistence.*;

@Entity
@Table(name="Test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="testid")
    private long testid;


    @Column(name="name")
    private String name;
    @Lob
    @Column(name="file")
    private byte[] file;
    @Column(name="filename")
    private String fileName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
