package com.ezybooks.collegeonyourterms.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ezybooks.collegeonyourterms.dao.AssessmentDAO;
import com.ezybooks.collegeonyourterms.dao.CourseDAO;
import com.ezybooks.collegeonyourterms.dao.NoteDAO;
import com.ezybooks.collegeonyourterms.dao.TermDAO;
import com.ezybooks.collegeonyourterms.entities.Assessment;
import com.ezybooks.collegeonyourterms.entities.Course;
import com.ezybooks.collegeonyourterms.entities.CourseNote;
import com.ezybooks.collegeonyourterms.entities.Term;

/**This abstract class builds the room database used to hold the data in the app. Extends RoomDatabase.*/
@Database(entities = {Assessment.class, Course.class, Term.class, CourseNote.class}, version = 16,exportSchema = false)
public abstract class CollegeDatabaseBuilder extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();

    public abstract TermDAO termDAO();
    public abstract NoteDAO noteDAO();
    private static volatile CollegeDatabaseBuilder INSTANCE;

    /**This method creates the database instance and returns it.
     * @param context
     * @return INSTANCE */
    static CollegeDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CollegeDatabaseBuilder.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CollegeDatabaseBuilder.class, "MyCollegeDatabase.db")
                        .fallbackToDestructiveMigration()
                        .build();

                }
            }
        }
        return INSTANCE;
    }
}
