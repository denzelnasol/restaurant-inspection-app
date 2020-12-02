package com.group11.cmpt276_project.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents the inspection data access object
 */
@Dao
public abstract class InspectionReportDao {

    @Query("SELECT * FROM InspectionReport ORDER BY inspection_date DESC")
    public abstract LiveData<List<InspectionReport>> getAllInspectionReports();

    @RawQuery(observedEntities = InspectionReport.class)
    public abstract LiveData<List<InspectionReport>> getInspectionsByQuery(SupportSQLiteQuery query);

    @Query("DELETE FROM InspectionReport")
    public  abstract void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract Long insert(InspectionReport obj);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract List<Long> insert(List<InspectionReport> objList);

    @Update
    abstract void update(InspectionReport obj);

    @Update
    abstract void update(List<InspectionReport> objList);

    @Delete
    abstract void delete(InspectionReport obj);

    @Transaction
    public void insertOrUpdate(InspectionReport obj) {
        long rowId = insert(obj);
        if(rowId == -1L) {
            update(obj);
        }
    }

    @Transaction
    public Set<String> insertOrUpdate(List<InspectionReport> objList) {
        List<Long> insertResults = insert(objList);

        List<InspectionReport> updateList = new ArrayList<>();
        Set<String> newInsertList = new HashSet<>();

        for(int i = 0; i < insertResults.size(); i++) {

            long index = insertResults.get(i);

            if(index == -1L) {
                updateList.add(objList.get(i));
                continue;
            }

            newInsertList.add(objList.get(i).getTrackingNumber());
        }

        if(!updateList.isEmpty()) {
            update(updateList);
        }

        return newInsertList;
    }
}
