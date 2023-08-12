package com.ezybooks.collegeonyourterms.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ezybooks.collegeonyourterms.entities.Assessment;

import java.util.List;
/**This interface sets up the data access objects for the Assessment class and table*/
@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM ASSESSMENTS ORDER BY assessmentId ASC")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM ASSESSMENTS WHERE courseId=:course ORDER BY assessmentId ASC")
    List<Assessment> getAssociatedAssessments(int course);
}
