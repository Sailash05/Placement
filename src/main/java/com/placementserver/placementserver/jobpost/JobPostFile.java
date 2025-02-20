package com.placementserver.placementserver.jobpost;

import jakarta.persistence.*;

@Entity
@Table(name = "jobpost_file")
public class JobPostFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobpostfileid")
    private long fileid;

    @ManyToOne
    @JoinColumn(name = "jobpostid", referencedColumnName = "jobpostid")
    private JobPost jobPost;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "filePath")
    private String filePath;

    public long getFileid() {
        return fileid;
    }

    public void setFileid(long fileid) {
        this.fileid = fileid;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
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

    public JobPostFile() {
        super();
    }

    public JobPostFile(String filePath, String fileName, JobPost jobPost) {
        super();
        this.filePath = filePath;
        this.fileName = fileName;
        this.jobPost = jobPost;
    }
}
