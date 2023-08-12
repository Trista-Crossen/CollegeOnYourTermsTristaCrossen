package com.ezybooks.collegeonyourterms.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ezybooks.collegeonyourterms.R;
import com.ezybooks.collegeonyourterms.database.Repository;
import com.ezybooks.collegeonyourterms.entities.Assessment;
import com.ezybooks.collegeonyourterms.entities.Course;
import com.ezybooks.collegeonyourterms.entities.CourseNote;
import com.ezybooks.collegeonyourterms.entities.Term;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**This class tells the app what certain screen elements do and where to put that information on the course details screen.*/
public class CourseDetails extends AppCompatActivity {
    Repository repository;
    Course currentCourse;
    int courseId;
    int termId;
    int noteId;
    String title;
    String startDateString;
    String endDateString;
    String status = "";
    String instructorName;
    String instructorEmail;
    String instructorPhone;
    String courseNote;
    TextView termIdTextView;
    EditText editTitle;
    TextView editStartDate;
    TextView editEndDate;
    Spinner statusSpinner;
    EditText editInstructorName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    EditText editNoteText;
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();


    /**This overridden method tells the app what information goes to what screen element, and populates those fields
     * if the item has been clicked on from the list on the previous screen.
     * @param savedInstanceState */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton3);

        courseId = getIntent().getIntExtra("courseId", -1);
        termId = getIntent().getIntExtra("termId", -1);
        noteId = getIntent().getIntExtra("noteId", -1);
        termIdTextView = findViewById(R.id.termIdTextView);
        editTitle = findViewById(R.id.titletext);
        editStartDate = findViewById(R.id.courseStartDate);
        editEndDate = findViewById(R.id.courseEndDate);
        statusSpinner = findViewById(R.id.statusSpinner);
        editInstructorName = findViewById(R.id.editInstructorName);
        editInstructorEmail = findViewById(R.id.editTextEmailAddress);
        editInstructorPhone = findViewById(R.id.editTextPhone);
        editNoteText = findViewById(R.id.editTextTextMultiLine2);
        title = getIntent().getStringExtra("title");
        startDateString = getIntent().getStringExtra("startDate");
        endDateString = getIntent().getStringExtra("endDate");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        termIdTextView.setText("Term Id " + termId);
        editTitle.setText(title);
        if(courseId == -1){
            editStartDate.setText("Click to pick date.");
        }
        else{
            editStartDate.setText(getIntent().getStringExtra("startDate"));
        }
        if(courseId == -1){
            editEndDate.setText("Click to pick date.");
        }
        else{
            editEndDate.setText(getIntent().getStringExtra("endDate"));
        }
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;

                String info = editStartDate.getText().toString();
                if(info.equals("Click to pick date.")) info = "08/01/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(CourseDetails.this, startDatePicker, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }
        };

        editEndDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Date date;

                String info = editEndDate.getText().toString();
                if(info.equals("Click to pick date.")) info = "08/01/23";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(CourseDetails.this, endDatePicker, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ArrayList<String> statusList = new ArrayList<>();
        statusList.add("in progress");
        statusList.add("complete");
        statusList.add("dropped");
        statusList.add("plan to take");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, statusList);
        statusSpinner.setAdapter(statusAdapter);
        if(status != null){
            if(status.equals("in progress")){
                statusSpinner.setSelection(0);
            } else if (status.equals("complete")) {
                statusSpinner.setSelection(1);
            } else if (status.equals("dropped")) {
                statusSpinner.setSelection(2);
            } else if(status.equals("plan to take")){
                statusSpinner.setSelection(3);
            }
        }
        editInstructorName.setText(instructorName);
        editInstructorPhone.setText(instructorPhone);
        editInstructorEmail.setText(instructorEmail);

        /**This method sets the click listening for the floating action button.*/
        fab.setOnClickListener(new View.OnClickListener() {
            /**This method sends the user from the course details screen to the assessment details screen to make a new assessment item.
             * @param v */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> associatedAssessments = new ArrayList<>();
        for(Assessment a : repository.getAllAssessments()){
            if(a.getCourseId() == courseId){
                associatedAssessments.add(a);
            }
        }
        assessmentAdapter.setAssessments(associatedAssessments);

        RecyclerView notesRecyclerView = findViewById(R.id.recyclerViewCourseNotes);
        repository = new Repository(getApplication());
        final NoteAdapter noteAdapter = new NoteAdapter(this);
        notesRecyclerView.setAdapter(noteAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CourseNote> associatedNotes = new ArrayList<>();
        for(CourseNote c : repository.getAllNotes()){
            if(c.getCourseId() == courseId){
                associatedNotes.add(c);
            }
        }
        noteAdapter.setNotes(associatedNotes);
    }

    private void updateLabelStart(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    /**This method inflates the courses options menu.
     * @param menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    /**This method tells the program what to do given which menu item is selected by the user.
     * @param item */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.saveCourse){
            title = editTitle.getText().toString();
            startDateString = editStartDate.getText().toString();
            endDateString = editEndDate.getText().toString();
            if(statusSpinner.getSelectedItem().equals("in progress")){
                status = "in progress";
            } else if (statusSpinner.getSelectedItem().equals("complete")) {
                status = "complete";
            } else if (statusSpinner.getSelectedItem().equals("dropped")) {
                status = "dropped";
            } else if (statusSpinner.getSelectedItem().equals("plan to take")){
                status = "plan to take";
            }
            instructorName = editInstructorName.getText().toString();
            instructorEmail = editInstructorEmail.getText().toString();
            instructorPhone = editInstructorPhone.getText().toString();
            Date courseStartDate;
            Date courseEndDate;
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {
                courseStartDate = sdf.parse(startDateString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            try {
                courseEndDate = sdf.parse(endDateString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Course course;

            if(courseId == -1){
                if(repository.getAllCourses().size() == 0) courseId = 1;
                else courseId = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseId() + 1;

                if(termId == -1) {
                    Toast.makeText(CourseDetails.this, "Unable to save course because there is no term associated with it.", Toast.LENGTH_LONG).show();
                    this.finish();
                }
                else{
                    if(title.equals("") && startDateString.equals("Click to pick date.") && endDateString.equals("Click to pick date.") && status.equals("") && instructorName.equals("") && !instructorEmail.equals("") && instructorPhone.equals("")){
                        Toast.makeText(CourseDetails.this, "Unable to save course because at lease one field was left blank.", Toast.LENGTH_LONG).show();
                        this.finish();
                    }
                    else {
                        if(!courseStartDate.before(courseEndDate)){
                            Toast.makeText(CourseDetails.this, "Start date must be before end date.", Toast.LENGTH_LONG).show();
                            this.finish();
                        }
                        else{
                            course = new Course(courseId, title, startDateString, endDateString, status, instructorName, instructorEmail, instructorPhone, termId);
                            repository.insert(course);
                            this.finish();
                        }
                    }
                }
            } else{
                if(!title.equals("") && !startDateString.equals("Click to pick date.") && !endDateString.equals("Click to pick date.") && !status.equals("") && !instructorName.equals("") && !instructorEmail.equals("") && !instructorPhone.equals("")){
                    if(!courseStartDate.before(courseEndDate)){
                        Toast.makeText(CourseDetails.this, "Start date must be before end date.", Toast.LENGTH_LONG).show();
                        this.finish();
                    }else{
                        course = new Course(courseId, title, startDateString, endDateString, status, instructorName, instructorEmail, instructorPhone, termId);
                        repository.update(course);
                        this.finish();
                    }

                }
                else {
                    Toast.makeText(CourseDetails.this, "Unable to save course because at lease one field was left blank.", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }
            return true;
        }
        if(item.getItemId() == R.id.shareNote){
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TEXT, editNoteText.getText().toString() + "EXTRA_TEXT");
            sentIntent.putExtra(Intent.EXTRA_TITLE, editTitle.getText().toString() + "EXTRA_TITLE");
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);

            courseNote = editNoteText.getText().toString();
            CourseNote note;
            if(noteId == -1){
                if(repository.getAllNotes().size() == 0) noteId = 1;
                else noteId = repository.getAllNotes().get(repository.getAllNotes().size() - 1).getNoteId() + 1;

                note = new CourseNote(noteId, courseId, courseNote);
                repository.insert(note);
                this.finish();
            }


            return true;
        }
        if(item.getItemId() == R.id.notifyCourseStart){
            String fromStartDate = editStartDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myStartDate = null;
            try{
                myStartDate = sdf.parse(fromStartDate);
            } catch (ParseException e){
                e.printStackTrace();
            }

            Long startTrigger = myStartDate.getTime();

            Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
            intent.putExtra("key", "Course " + title + " starts today on " + startDateString);
            PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, sender);

            return true;
        }
        if(item.getItemId() == R.id.notifyCourseEnd){
            String fromEndDate = editEndDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            Date myEndDate = null;
            try {
                myEndDate = sdf.parse(fromEndDate);
            }catch (ParseException e) {
                e.printStackTrace();
            }
            Long endTrigger = myEndDate.getTime();

            Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
            intent.putExtra("key", "Course " + title + " ends today on " + endDateString);
            PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, sender);

            return true;
        }
        if(item.getItemId() == R.id.deleteCourse){
            for(Course c : repository.getAllCourses()){
                if(c.getCourseId() == courseId) currentCourse = c;
            }
            repository.delete(currentCourse);
            Toast.makeText(CourseDetails.this, currentCourse.getCourseTitle() + " has been deleted", Toast.LENGTH_LONG).show();
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**This overridden method renews the assessment list when coming back to the course details screen from the assessment details screen.*/
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> associatedAssessments = new ArrayList<>();
        for(Assessment a : repository.getAllAssessments()){
            if(a.getCourseId() == courseId){
                associatedAssessments.add(a);
            }
        }
        assessmentAdapter.setAssessments(associatedAssessments);
    }
}