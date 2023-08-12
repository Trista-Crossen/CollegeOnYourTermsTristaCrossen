package com.ezybooks.collegeonyourterms.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**This class creates course note entities and uses them to populate the notes table.*/
@Entity(tableName = "notes")
public class CourseNote {
    @PrimaryKey(autoGenerate = true)
    int noteId;
    int courseId;
    String noteString;

    /**The constructor for the course notes class.
     * @param noteId
     * @param courseId
     * @param noteString */
    public CourseNote(int noteId, int courseId, String noteString) {
        this.noteId = noteId;
        this.courseId = courseId;
        this.noteString = noteString;
    }

    /**This method returns the note id.
     * @return noteId*/
    public int getNoteId() {
        return noteId;
    }

    /**This method sets the note id.
     * @param noteId */
    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    /**This method returns the course id associated with the note.
     * @return courseId*/
    public int getCourseId() {
        return courseId;
    }

    /**This method sets the course id associated with the note.
     * @param courseId */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**This method returns the note string.
     * @return noteString*/
    public String getNoteString() {
        return noteString;
    }

    /**This method sets the note string.
     * @param noteString */
    public void setNoteString(String noteString) {
        this.noteString = noteString;
    }
}
