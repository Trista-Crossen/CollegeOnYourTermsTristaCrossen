package com.ezybooks.collegeonyourterms.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ezybooks.collegeonyourterms.R;
import com.ezybooks.collegeonyourterms.database.Repository;
import com.ezybooks.collegeonyourterms.entities.Assessment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**This class programs what takes place on the assessment details screen. Extends AppCompatActivity.*/
public class AssessmentDetails extends AppCompatActivity {
    Repository repository;
    Assessment currentAssessment;
    int assessmentId;
    int courseId;
    String title;
    String startDateString;
    String endDateString;
    String type = "";
    TextView courseIdTextView;
    EditText editTitle;
    TextView editStartDate;
    TextView editEndDate;
    RadioGroup examType;
    RadioButton objectiveRB;
    RadioButton performanceRB;
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    /**This overridden method is used to populate data into appropriate fields from existing assessment items.
     * @param savedInstanceState */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        repository = new Repository(getApplication());

        assessmentId = getIntent().getIntExtra("id", -1);
        courseId = getIntent().getIntExtra("courseId", -1);
        courseIdTextView = findViewById(R.id.courseIdTextView);
        editTitle = findViewById(R.id.assessmentTitleText);
        editStartDate = findViewById(R.id.assessmentStartDate);
        editEndDate = findViewById(R.id.assessmentEndDate);
        objectiveRB = findViewById(R.id.objectiveRB);
        performanceRB = findViewById(R.id.performanceRB);
        courseIdTextView.setText("Course ID associated with this assessment is " + courseId);
        title = getIntent().getStringExtra("title");
        startDateString = getIntent().getStringExtra("startDate");
        endDateString = getIntent().getStringExtra("endDate");
        type = getIntent().getStringExtra("type");
        editTitle.setText(title);
        editStartDate.setText(startDateString);
        editEndDate.setText(endDateString);
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

        endDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }
        };

        editStartDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Date date;

                String info = editStartDate.getText().toString();
                if(info.equals("")) info = "08/01/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(AssessmentDetails.this, startDatePicker, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Date date;

                String info = editEndDate.getText().toString();
                if(info.equals("")) info = "08/01/23";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(AssessmentDetails.this, endDatePicker, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        if(type != null){
            if(type.equals("Objective")){
                objectiveRB.setChecked(true);
            }
            else if(type.equals("Performance")){
                performanceRB.setChecked(true);
            }
        }
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

    /**This overridden method inflates the menu on the assessment details screen and returns true.
     * @param menu
     * @return true*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    /**This overridden method tells the program what to do depending on what menu item is clicked.
     * @param item */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.saveAssessment){
            Assessment assessment;
            title = editTitle.getText().toString();
            startDateString = editStartDate.getText().toString();
            endDateString = editEndDate.getText().toString();
            Date startDate;
            Date endDate;
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {
                startDate = sdf.parse(startDateString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            try {
                endDate = sdf.parse(endDateString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (objectiveRB.isChecked()) {
                type = "Objective";
            } else if (performanceRB.isChecked()) {
                type = "Performance";
            }
            if(assessmentId == -1) {
                if (repository.getAllAssessments().size() == 0) assessmentId = 1;
                else assessmentId = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentId() + 1;

                if(courseId == -1){
                    Toast.makeText(AssessmentDetails.this, "Unable to save assessment because there is no course associated with it.", Toast.LENGTH_LONG).show();
                    this.finish();
                }
                else {
                    if(!title.equals("") && !startDateString.equals("") && !endDateString.equals("") && !type.equals("")){
                        if (!startDate.before(endDate)) {
                            Toast.makeText(AssessmentDetails.this, "Start date must be before end date.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            assessment = new Assessment(assessmentId, courseId, title, startDateString, endDateString, type);
                            repository.insert(assessment);
                            this.finish();
                        }
                    }
                    else{
                        Toast.makeText(AssessmentDetails.this, "Unable to save assessment because at least one field was left blank.", Toast.LENGTH_LONG).show();
                        this.finish();
                    }
                }
            }
            else{
                if(!title.equals("") && !startDateString.equals("") && !endDateString.equals("") && !type.equals("")){
                    if (!startDate.before(endDate)) {
                        Toast.makeText(AssessmentDetails.this, "Start date must be before end date.", Toast.LENGTH_LONG).show();
                        this.finish();
                    }
                    else {
                        assessment = new Assessment(assessmentId, courseId, title, startDateString, endDateString, type);
                        repository.update(assessment);
                        this.finish();
                    }
                }
                else{
                    Toast.makeText(AssessmentDetails.this, "Unable to save assessment because at least one field was left blank.", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }
        }
        if(item.getItemId() == R.id.notifyAssessmentStart){
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

            Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
            intent.putExtra("key", "Assessment " + title + " starts today on " + startDateString);
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, sender);

            return true;
        }
        if(item.getItemId() == R.id.notifyAssessmentEnd){
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

            Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
            intent.putExtra("key", "Assessment " + title + " ends today on " + endDateString);
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, sender);

            return true;
        }
        if(item.getItemId() == R.id.deleteAssessment){
            for(Assessment assess : repository.getAllAssessments()){
                if(assess.getAssessmentId() == assessmentId) currentAssessment = assess;
            }
            repository.delete(currentAssessment);
            Toast.makeText(AssessmentDetails.this, currentAssessment.getTitle() + " has been deleted", Toast.LENGTH_LONG).show();
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}