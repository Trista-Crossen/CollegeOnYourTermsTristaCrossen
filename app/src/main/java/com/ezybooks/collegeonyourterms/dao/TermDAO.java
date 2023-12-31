package com.ezybooks.collegeonyourterms.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ezybooks.collegeonyourterms.entities.Term;

import java.util.List;

/**This interface sets up the data access object for the term class and table.*/
@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM TERMS ORDER BY termID ASC")
    List<Term> getAllTerms();
}
