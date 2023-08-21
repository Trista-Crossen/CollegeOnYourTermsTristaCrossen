package com.ezybooks.collegeonyourterms.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ezybooks.collegeonyourterms.R;
import com.ezybooks.collegeonyourterms.database.Repository;
import com.ezybooks.collegeonyourterms.entities.Assessment;
import com.ezybooks.collegeonyourterms.entities.Course;
import com.ezybooks.collegeonyourterms.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**This class tells the app what to do with the term list screen. Extends AppCompatActivity.*/
public class TermList extends AppCompatActivity {
    private Repository repository;
    /**This overridden method creates the elements on the screen.
     * @param savedInstanceState */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            /**This overridden method sends the user to a new term details screen when clicked.
             * The on click listener for the floating action button.
             * @param v */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermList.this, TermDetails.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

        //System.out.println(getIntent().getStringExtra("test"));
    }

    /**This overridden method inflates the term list menu.
     * @param menu
     * @return true*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;
    }

    /**This overridden method tells the app what to do given which menu item the user clicks on.
     * @param item */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.sample) {
            repository = new Repository(getApplication());
            //Toast.makeText(TermList.this, "put in sample data", Toast.LENGTH_LONG).show();

            Term term = new Term(0, "Spring 2023", "03/01/23", "08/31/23");
            repository.insert(term);



            term = new Term(0, "Fall 2023", "09/01/23", "02/29/24");
            repository.insert(term);


            Course course = new Course(0, "Stats and Prob", "03/01/23", "04/15/23", "complete", "Dr. Dolittle", "probAndstats@college.edu", "555-555-5555", "", 1);
            repository.insert(course);


            Assessment assessment = new Assessment(0, 1, "Quiz on Stats", "03/12/23", "03/14/23","Objective");
            repository.insert(assessment);


            return true;
        }
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**This overridden method renews the term list recycler view after saving a term.*/
    @Override
    protected void onResume() {
        super.onResume();
        List<Term> allTerms = repository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }
}