package com.ezybooks.collegeonyourterms.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**This class sets up the entity Assessment, which is used to populate table assessments.*/
@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private int courseId;
    private String title;
    private String startDate;
    private String endDate;
    private String type;

    /**This is the constructor for the class.
     * @param assessmentId
     * @param courseId
     * @param title
     * @param startDate
     * @param endDate
     * @param type */
    public Assessment(int assessmentId, int courseId, String title, String startDate, String endDate, String type) {
        this.assessmentId = assessmentId;
        this.courseId = courseId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    /**This method returns the assessment id.
     * @return assessmentId*/
    public int getAssessmentId() {
        return assessmentId;
    }

    /**This method sets the assessment id.
     * @param assessmentId */
    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    /**This method returns the course id associated with the assessment.
     * @return courseId*/
    public int getCourseId() {
        return courseId;
    }

    /**This method sets the course id associated with the assessment.
     * @param courseId */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**This method returns the assessment title.
     * @return title*/
    public String getTitle() {
        return title;
    }

    /**This method sets the assessment title.
     * @param title */
    public void setTitle(String title) {
        this.title = title;
    }

    /**This method returns the assessment start date.
     * @return startDate*/
    public String  getStartDate() {return startDate;}

    /**This method sets the assessment start date.
     * @param startDate */
    public void setStartDate(String startDate) {this.startDate = startDate;}

    /**This method returns the assessment end date.
     * @return endDate*/
    public String getEndDate() {return endDate;}

    /**This method sets the assessment end date.
     * @param endDate */
    public void setEndDate(String endDate) {this.endDate = endDate;}

    /**This method returns the assessment type.
     * @return type*/
    public String getType() {
        return type;
    }

    /**This method sets the assessment type.
     * @param type */
    public void setType(String type) {
        this.type = type;
    }
}
