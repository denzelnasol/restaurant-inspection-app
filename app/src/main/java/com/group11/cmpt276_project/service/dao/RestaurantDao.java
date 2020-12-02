package com.group11.cmpt276_project.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.model.RestaurantUpdate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the restaurant data access object
 */
@Dao
public abstract class RestaurantDao {

    @Query("SELECT * FROM Restaurant ORDER BY name")
    public abstract LiveData<List<Restaurant>> getAllRestaurants();

    @Query("SELECT * FROM Restaurant WHERE is_favorite == 1 ORDER BY name")
    public abstract LiveData<List<Restaurant>> getFavouriteRestaurants();

    @RawQuery(observedEntities = Restaurant.class)
    public abstract  LiveData<List<Restaurant>> getRestaurantByQuery(SupportSQLiteQuery query);

    @Query("DELETE FROM Restaurant")
    public  abstract void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract Long insert(Restaurant obj);

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Restaurant.class)
    abstract List<Long> insert(List<RestaurantUpdate> objList);

    @Update
    abstract void update(Restaurant obj);

    @Update(entity = Restaurant.class)
    abstract void update(List<RestaurantUpdate> objList);

    @Delete
    abstract void delete(Restaurant obj);

    @Transaction
    public void insertOrUpdate(Restaurant obj) {
        long rowId = insert(obj);
        if(rowId == -1L) {
            update(obj);
        }
    }

    @Transaction
    public void insertOrUpdate(List<RestaurantUpdate> objList) {
        List<Long> insertResults = insert(objList);
        List<RestaurantUpdate> updateList = new ArrayList<>();

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
