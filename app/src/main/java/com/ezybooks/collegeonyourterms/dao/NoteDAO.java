package com.ezybooks.collegeonyourterms.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ezybooks.collegeonyourterms.entities.CourseNote;

import java.util.List;

/**This interface sets up the data access object for the course note class and table.*/
@Dao
public interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CourseNote note);

    @Delete
    void delete(CourseNote note);

    @Query("SELECT * FROM notes ORDER BY noteId ASC")
    List<CourseNote> getAllNotes();
    @Query("SELECT * FROM notes WHERE courseId=:note ORDER BY noteId ASC")
    List<CourseNote> getAssociatedNotes(int note);
}
