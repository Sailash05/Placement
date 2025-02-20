package com.placementserver.placementserver.placement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "placement")
public class Placement {

    @Id
    @Column(name="rollno")
    private long rollno;
    @Column(name="name")
    private String name;
    @Column(name="department")
    private String department;
    @Column(name = "passed_out_year")
    private short passedOutYear;
    @Column(name = "company_name")
    private String companyName;
    @Column(name="lpa")
    private float lpa;

    public long getRollno() {
        return rollno;
    }
    public void setRollno(long rollno) {
        this.rollno = rollno;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public short getPassedOutYear() {
        return passedOutYear;
    }
    public void setPassedOutYear(short passedOutYear) {
        this.passedOutYear = passedOutYear;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public float getLpa() {
        return lpa;
    }
    public void setLpa(float lpa) {
        this.lpa = lpa;
    }

    public Placement() {
        super();
    }

    public Placement(long rollno, String name, String department, short passedOutYear, String companyName, float lpa) {
        this.rollno = rollno;
        this.name = name;
        this.department = department;
        this.passedOutYear = passedOutYear;
        this.companyName = companyName;
        this.lpa = lpa;
    }

}
