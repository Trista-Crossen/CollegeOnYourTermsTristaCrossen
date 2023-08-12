package com.ezybooks.collegeonyourterms.database;

import android.app.Application;

import com.ezybooks.collegeonyourterms.dao.AssessmentDAO;
import com.ezybooks.collegeonyourterms.dao.CourseDAO;
import com.ezybooks.collegeonyourterms.dao.NoteDAO;
import com.ezybooks.collegeonyourterms.dao.TermDAO;
import com.ezybooks.collegeonyourterms.entities.Assessment;
import com.ezybooks.collegeonyourterms.entities.Course;
import com.ezybooks.collegeonyourterms.entities.CourseNote;
import com.ezybooks.collegeonyourterms.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**This class sets up the repository that executes all the database functions.*/
public class Repository {
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private TermDAO mTermDAO;
    private NoteDAO mNoteDAO;

    private List<Assessment> mAllAssessments;
    private List<Assessment> mAssociatedAssessments;
    private List<Course> mAllCourses;
    private List<Term> mAllTerms;
    private List<CourseNote> mAllNotes;
    private List<CourseNote> mAssociatedNotes;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**This method is the constructor for the repository class
     * @param application */
    public Repository(Application application){
        CollegeDatabaseBuilder db = CollegeDatabaseBuilder.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mCourseDAO = db.courseDAO();
        mTermDAO = db.termDAO();
        mNoteDAO = db.noteDAO();
    }

    /**This method returns a list of terms.
     * @return mAllTerms*/
    public List<Term> getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms = mTermDAO.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllTerms;
    }

    /**This method inserts a new term into the list of terms.
     * @param term */
    public void insert(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**This method updates the term the user is currently reviewing.
     * @param term */
    public void update(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**This method deletes the term the user is currently reviewing.
     * @param term */
    public void delete(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**This method returns a list of associated courses based on the term id.
     * @param termID
     * @return mAllCourses*/
    public List<Course> getAssociatedCourses(int termID){
        databaseExecutor.execute(()->{
            mAllCourses = mCourseDAO.getAssociatedCourses(termID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllCourses;
    }

    /**This method returns a list of all courses.
     * @return mAllCourses*/
    public List<Course> getAllCourses() {
        databaseExecutor.execute(()->{
                mAllCourses = mCourseDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllCourses;
    }

    /**This method inserts a new course in to the list of courses.
     * @param course */
    public void insert(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**This method updates the course the user is currently reviewing.
     * @param course */
    public void update(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**This method deletes the course the user is currently reviewing.
     * @param course */
    public void delete(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**This method returns a list of all assessments
     * @return mAllAssessments*/
    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()->{
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllAssessments;
    }

    /**This method returns a list of associated assessments based on course id.
     * @param courseID
     * @return mAssociatedAssessments*/
    public List<Assessment> getAssociatedAssessments(int courseID){
        databaseExecutor.execute(()->{
            mAssociatedAssessments = mAssessmentDAO.getAssociatedAssessments(courseID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAssociatedAssessments;
    }

    /**This method inserts a new assessment into the list of assessments.
     * @param assessment */
    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**This method updates the assessment the user is currently reviewing.
     * @param assessment */
    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**This method deletes the assessment the user is currently reviewing.
     * @param assessment */
    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**This method inserts a new course note into the list of course notes.
     * @param note */
    public void insert(CourseNote note) {
        databaseExecutor.execute(()->{
            mNoteDAO.insert(note);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    /**This method returns a list of all course notes.
     * @return mAllNotes*/
    public List<CourseNote> getAllNotes(){
        databaseExecutor.execute(()->{
            mAllNotes = mNoteDAO.getAllNotes();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return mAllNotes;
    }

    /**This method returns a list of associated notes based on course id.
     * @param courseId
     * @return mAssociatedNotes*/
    public List<CourseNote> getAssociatedNotes(int courseId){
        databaseExecutor.execute(()->{
            mAssociatedNotes = mNoteDAO.getAssociatedNotes(courseId);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return mAssociatedNotes;
    }

    /**This method deletes course notes.
     * @param note */
    public void delete(CourseNote note){
        databaseExecutor.execute(()->{
            mNoteDAO.delete(note);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
