package com.ezybooks.collegeonyourterms.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ezybooks.collegeonyourterms.entities.Course;

import java.util.List;

/**This interface sets up the data access object for the Course class and table.*/
@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM COURSES ORDER BY courseId ASC")
    List<Course> getAllCourses();

    @Query("SELECT * FROM COURSES WHERE termId=:term ORDER BY courseId ASC")
    List<Course> getAssociatedCourses(int term);
}
