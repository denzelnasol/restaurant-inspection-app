package com.group11.cmpt276_project.service.repository.impl.db;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.dao.RestaurantDao;
import com.group11.cmpt276_project.service.db.RestaurantDatabase;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.model.RestaurantUpdate;
import com.group11.cmpt276_project.service.repository.IRestaurantRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Room database to handle restaurants
 */
public class RoomRestaurantRepository implements IRestaurantRepository {

    private RestaurantDao restaurantDao;
    private LiveData<List<Restaurant>> restaurants;

    public RoomRestaurantRepository(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
        this.restaurants = this.restaurantDao.getAllRestaurants();
    }

    @Override
    public LiveData<List<Restaurant>> getRestaurants() throws RepositoryReadError {
        return this.restaurants;
    }

    @Override
    public LiveData<List<Restaurant>> getRestaurantsBySearch(String name, boolean isFavorite, int numberCritical, String hazardLevel) throws RepositoryReadError {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT R.tracking_number, R.physical_city, R.physical_address, R.name, R.facility_type, R.latitude, R.longitude, R.is_favorite FROM Restaurant R " );

        List<Object> queryParameters = new ArrayList<>();

        if(numberCritical != 0) {
            queryBuilder.append(" JOIN (SELECT tracking_number FROM InspectionReport WHERE SUBSTR(inspection_date, 1, 4) == ? GROUP BY tracking_number HAVING SUM(number_critical) ");
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();
            queryParameters.add(String.valueOf(currentYear));
            queryParameters.add(Math.abs(numberCritical));
            queryBuilder.append((numberCritical > 0 ? ">= ?" : "<= ?" ) + ") G ON R.tracking_number == G.tracking_number ");
        }

        boolean shouldFilterByName = name != null && !name.isEmpty();
        boolean shouldFilterByHazardLevel = hazardLevel != null && !hazardLevel.isEmpty();

        boolean appendWhere = shouldFilterByName || isFavorite;

        if(shouldFilterByHazardLevel) {
            queryBuilder.append(" JOIN (SELECT tracking_number, hazard_rating, MAX(inspection_date) FROM InspectionReport GROUP BY tracking_number) I ON  R.tracking_number == I.tracking_number WHERE I.hazard_rating == ? ");
            queryParameters.add(hazardLevel);
        }

        if(appendWhere && !shouldFilterByHazardLevel) {
            queryBuilder.append("WHERE ");
        }

        if(shouldFilterByName) {
            if(shouldFilterByHazardLevel) {
                queryBuilder.append("AND ");
            }
            queryBuilder.append("R.name LIKE ? ");
            queryParameters.add("%" + name + "%");
        }

        if(isFavorite) {
            if(shouldFilterByName || shouldFilterByHazardLevel) {
                queryBuilder.append("AND ");
            }
            queryBuilder.append("R.is_favorite == 1 ");
        }

        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryBuilder.toString(), queryParameters.toArray());

        return this.restaurantDao.getRestaurantByQuery(query);
    }

    @Override
    public void saveRestaurants(List<RestaurantUpdate> restaurants) throws RepositoryWriteError {
        this.restaurantDao.insertOrUpdate(restaurants);
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) throws RepositoryWriteError {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            this.restaurantDao.insertOrUpdate(restaurant);
        });
    }
}

