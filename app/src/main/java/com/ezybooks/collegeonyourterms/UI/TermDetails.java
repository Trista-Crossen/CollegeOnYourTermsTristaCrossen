package com.ezybooks.collegeonyourterms.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ezybooks.collegeonyourterms.R;
import com.ezybooks.collegeonyourterms.database.Repository;
import com.ezybooks.collegeonyourterms.entities.Assessment;
import com.ezybooks.collegeonyourterms.entities.Course;
import com.ezybooks.collegeonyourterms.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**This class tells the app what to do with the term details screen.*/
public class TermDetails extends AppCompatActivity {
    String title;
    String startDateString;
    String endDateString;
    int termId;
    int numCourses;
    TextView termIdTextView;
    EditText editTitle;
    TextView editStartDate;
    TextView editEndDate;
    Repository repository;
    Term currentTerm;
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    /**This overridden method tells the app which elements on the screen belong to which class variable.
     * @param savedInstanceState */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        termIdTextView = findViewById(R.id.termIdTxtView);
        editTitle = findViewById(R.id.titleEditText);
        editStartDate = findViewById(R.id.termStartDate);
        editEndDate = findViewById(R.id.termEndDate);
        title = getIntent().getStringExtra("title");
        startDateString = getIntent().getStringExtra("startDate");
        endDateString = getIntent().getStringExtra("endDate");
        termId = getIntent().getIntExtra("termId", -1);
        termIdTextView.setText("Term Id " + termId);
        editTitle.setText(title);
        if(termId == -1){
            editStartDate.setText("Click to pick date.");
        }
        else{
            editStartDate.setText(getIntent().getStringExtra("startDate"));
        }
        if(termId == -1){
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
                if(info.equals("Click to pick date.")) info = "08/01/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(TermDetails.this, startDatePicker, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
                new DatePickerDialog(TermDetails.this, endDatePicker, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            /**This overridden method sends the user to the course details screen to make a new course. Sends over
             * information that is needed through an intent.
             * @param v */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                intent.putExtra("termId", termId);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.activeCoursesRecyclerView);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> associatedCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermId() == termId) associatedCourses.add(c);
        }
        courseAdapter.setCourses(associatedCourses);
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

    /**This overridden method inflates the term details menu.
     * @param menu
     * @return true*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    /**This overridden method tells the app what to do given which menu item the user clicks on.
     * @param item
     * @return true*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.termsave) {
            Term term;
            title = editTitle.getText().toString();
            startDateString = editStartDate.getText().toString();
            endDateString = editEndDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date startDate;
            Date endDate;
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
            if (termId == -1) {
                if (repository.getAllTerms().size() == 0) termId = 1;
                else termId = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;

                if(!title.equals("") && !startDateString.equals("Click to pick date.") && !endDateString.equals("Click to pick date.")){
                    if(!startDate.before(endDate)){
                        Toast.makeText(TermDetails.this, "Start date must be before end date.", Toast.LENGTH_LONG).show();
                        this.finish();
                    }
                    else {
                        //Saves new term
                        term = new Term(termId, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                        repository.insert(term);
                        this.finish();
                    }
                }
                else {
                    Toast.makeText(TermDetails.this, "Unable to save term because at least one field was left blank.", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            } else {
                if(!title.equals("") && !startDateString.equals("Click to pick date.") && !endDateString.equals("Click to pick date.")){
                    if(!startDate.before(endDate)){
                        Toast.makeText(TermDetails.this, "Start date must be before end date.", Toast.LENGTH_LONG).show();
                        this.finish();
                    }
                    else{
                        //Updates a term
                        term = new Term(termId, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                        repository.update(term);
                        this.finish();
                    }
                }
                else {
                    Toast.makeText(TermDetails.this, "Unable to save term because at least one field was left blank.", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }
            return true;
        }
        if (item.getItemId() == R.id.termdelete) {
            for(Term term : repository.getAllTerms()){
                if(term.getTermID() == termId) currentTerm = term;
            }
            numCourses = 0;
            for(Course course : repository.getAllCourses()){
                if(course.getTermId() == termId) ++numCourses;
            }
            if(numCourses == 0) {
                repository.delete(currentTerm);
                Toast.makeText(TermDetails.this, currentTerm.getTermTitle() + " was deleted", Toast.LENGTH_LONG).show();
                this.finish();
            }
            else{
                Toast.makeText(TermDetails.this, currentTerm.getTermTitle() + " can not be deleted due to associated courses.", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**This overridden method updates the active courses recycler view on the correct term details screen on resume. */
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.activeCoursesRecyclerView);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> associatedCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermId() == termId) associatedCourses.add(c);
        }
        courseAdapter.setCourses(associatedCourses);
    }
}