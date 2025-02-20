package com.placementserver.placementserver.jobpost;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="jobpost")
public class JobPost {

    @Id
    @Column(name="jobpostid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long jobPostid;

    @Column(name="job_title")
    private String jobPostTitle;

    @Column(name="job_description")
    private String jobPostDescription;

    @Column(name="job_content", columnDefinition = "LONGTEXT")
    private String jobPostContent;

    @Column(name="posted_by")
    private String postedBy;

    @Column(name="apply_link")
    private String applyLink;

    @Transient
    private List<String> jobPostFiles;

    public long getJobPostid() {
        return jobPostid;
    }

    public void setJobPostid(long jobPostid) {
        this.jobPostid = jobPostid;
    }

    public String getJobPostTitle() {
        return jobPostTitle;
    }

    public void setJobPostTitle(String jobPostTitle) {
        this.jobPostTitle = jobPostTitle;
    }

    public String getJobPostDescription() {
        return jobPostDescription;
    }

    public void setJobPostDescription(String jobPostDescription) {
        this.jobPostDescription = jobPostDescription;
    }

    public String getJobPostContent() {
        return jobPostContent;
    }

    public void setJobPostContent(String jobPostContent) {
        this.jobPostContent = jobPostContent;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public void setApplyLink(String applyLink) {
        this.applyLink = applyLink;
    }

    public List<String> getJobPostFiles() {
        return jobPostFiles;
    }

    public void setJobPostFiles(List<String> jobPostFiles) {
        this.jobPostFiles = jobPostFiles;
    }

    public JobPost() {
        super();
    }

    public JobPost(String jobPostTitle,
                   String jobPostDescription,
                   String jobPostContent,
                   String postedBy,
                   String applyLink) {
        super();
        this.jobPostTitle = jobPostTitle;
        this.jobPostDescription = jobPostDescription;
        this.jobPostContent = jobPostContent;
        this.postedBy = postedBy;
        this.applyLink = applyLink;
    }
}
