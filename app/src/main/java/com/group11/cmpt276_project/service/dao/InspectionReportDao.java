package com.group11.cmpt276_project.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.group11.cmpt276_project.service.model.InspectionReport;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class InspectionReportDao {

    @Query("SELECT * FROM InspectionReport ORDER BY inspection_date DESC")
    public abstract LiveData<List<InspectionReport>> getAllInspectionReports();

    @Query("SELECT * FROM inspectionreport WHERE tracking_number == :trackingNumber")
    public abstract List<InspectionReport> getInspectionReportsByTrackingNumber(String trackingNumber);

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
    public void insertOrUpdate(List<InspectionReport> objList) {
        List<Long> insertResults = insert(objList);

        System.out.println("Insert results " + insertResults.size());
        System.out.println("Insert objList " + objList.size());
        List<InspectionReport> updateList = new ArrayList<>();

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
