package com.ezybooks.collegeonyourterms.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**This class sets up term entities and populates the terms table with that information.*/
@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termTitle;
    private String startDate;
    private String endDate;

    /**The constructor for the term class.
     * @param termID
     * @param termTitle
     * @param startDate
     * @param endDate */
    public Term(int termID, String termTitle, String startDate, String endDate) {
        this.termID = termID;
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**This method returns the term id.
     * @return termId*/
    public int getTermID() {
        return termID;
    }

    /**This method sets the term id.
     * @param termID */
    public void setTermID(int termID) {
        this.termID = termID;
    }

    /**This method returns the term title.
     * @return termTitle*/
    public String getTermTitle() {
        return termTitle;
    }

    /**This method sets the term title.
     * @param termTitle */
    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    /**This method returns the term start date.
     * @return startDate*/
    public String getStartDate() {
        return startDate;
    }

    /**This method sets the term start date.
     * @param startDate */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**This method returns the term end date.
     * @return endDate*/
    public String getEndDate() {
        return endDate;
    }

    /**This method sets the term end date.
     * @param endDate */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}
