package com.group11.cmpt276_project.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.model.RestaurantUpdate;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class RestaurantDao {

    @Query("SELECT * FROM Restaurant ORDER BY name")
    public abstract LiveData<List<Restaurant>> getAllRestaurants();

    @Query("SELECT * FROM Restaurant WHERE is_favorite == 1")
    public abstract LiveData<List<Restaurant>> getFavoriteRestaurants();

    @Query("SELECT R.tracking_number, R.physical_city, R.physical_address, R.name, facility_type, latitude, longitude, is_favorite " +
            " FROM Restaurant R" +
            " JOIN InspectionReport I ON  R.tracking_number == I.tracking_number" +
            " JOIN (SELECT tracking_number, SUM(number_critical) FROM InspectionReport A WHERE SUBSTR(A.inspection_date, 1, 4) == :now GROUP BY A.tracking_number HAVING SUM(A.number_critical) >= :greater) G ON R.tracking_number == G.tracking_number " +
            "WHERE I.hazard_rating == :hazardRating AND R.name LIKE :name AND R.is_favorite == 1 ")
    public abstract LiveData<List<Restaurant>> getRestaurantsWithWithRecentHazardLevel(String hazardRating, String name, int greater,int now);


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
