package com.ezybooks.collegeonyourterms.entities;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**This class sets up the Course entity to be used in the courses table.*/
@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String courseTitle;
    private String  startDate;
    private String endDate;
    private String status;
    private String instructorName;
    private String instructorEmail;
    private String instructorPhone;
    private String note;
    private int termId;

    /**This is the constructor of the Course class.
     * @param courseId
     * @param courseTitle
     * @param startDate
     * @param endDate
     * @param status
     * @param instructorName
     * @param instructorEmail
     * @param instructorPhone
     * @param termId */
    public Course(int courseId, String courseTitle, String startDate, String endDate, String status, String instructorName, String instructorEmail, String instructorPhone, String note, int termId) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
        this.note = note;
        this.termId = termId;
    }


    /**This method returns the course id.
     * @return courseId*/
    public int getCourseId() {
        return courseId;
    }

    /**This method sets the course id.
     * @param courseId */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**This method returns the course title.
     * @return courseTitle*/
    public String getCourseTitle() {
        return courseTitle;
    }

    /**This method sets the course title.
     * @param courseTitle */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**This method returns the course start date.
     * @return startDate*/
    public String getStartDate() {
        return startDate;
    }

    /**This method sets the course start date.
     * @param startDate */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**This method returns the course end date.
     * @return endDate*/
    public String getEndDate() {
        return endDate;
    }

    /**This method sets the course end date.
     * @param endDate */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**This method returns the course status.
     * @return status*/
    public String getStatus() {
        return status;
    }

    /**This method sets the course status.
     * @param status */
    public void setStatus(String status) {
        this.status = status;
    }

    /**This method returns the course's instructor name.
     * @return instructorName*/
    public String getInstructorName() {
        return instructorName;
    }

    /**This method sets the course's instructor name.
     * @param instructorName */
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    /**This method returns the instructor's email.
     * @return instructorEmail*/
    public String getInstructorEmail() {
        return instructorEmail;
    }

    /**This method sets the instructor's email.
     * @param instructorEmail */
    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    /**This method returns the instructor's phone number.
     * @return instructorPhone*/
    public String getInstructorPhone() {
        return instructorPhone;
    }

    /**This method sets the instructor's phone number.
     * @param instructorPhone */
    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**This method returns the term id associated with the course being viewed or made.
     * @return termId*/
    public int getTermId() {
        return termId;
    }

    /**This method sets the term id associated with the course being viewed or made.
     * @param termId */
    public void setTermId(int termId) {
        this.termId = termId;
    }
}
