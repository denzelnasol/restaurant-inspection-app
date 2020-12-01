package com.group11.cmpt276_project.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.group11.cmpt276_project.service.model.Violation;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the violation data access object
 */
@Dao
public abstract class ViolationDao  {

    @Query("SELECT * FROM Violation WHERE lang_code == :langCode")
    public abstract LiveData<List<Violation>> getAllViolations(String langCode);

    @Query("SELECT * FROM Violation WHERE id == :id")
    public abstract Violation getViolationById(String id);

    @Query("DELETE FROM Violation")
    public  abstract void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract Long insert(Violation obj);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract List<Long> insert(List<Violation> objList);

    @Update
    abstract void update(Violation obj);

    @Update
    abstract void update(List<Violation> objList);

    @Delete
    abstract void delete(Violation obj);

    @Transaction
    public void insertOrUpdate(Violation obj) {
        long rowId = insert(obj);
        if(rowId == -1L) {
            update(obj);
        }
    }

    @Transaction
    public void insertOrUpdate(List<Violation> objList) {
        List<Long> insertResults = insert(objList);
        List<Violation> updateList = new ArrayList<>();

        for(int i = 0; i < insertResults.size(); i++) {

            long index = insertResults.get(i);

            if(index == -1L) {
                updateList.add(objList.get(i));
            }
        }

        if(!updateList.isEmpty()) {
            update(updateList);
        }
    }
}
