package com.group11.cmpt276_project.service.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group11.cmpt276_project.service.converters.Converters;
import com.group11.cmpt276_project.service.dao.InspectionReportDao;
import com.group11.cmpt276_project.service.dao.RestaurantDao;
import com.group11.cmpt276_project.service.dao.ViolationDao;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.model.RestaurantUpdate;
import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.utils.Constants;
import com.group11.cmpt276_project.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents the restaurant database
 */
@Database(entities = {InspectionReport.class, Restaurant.class, Violation.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RestaurantDatabase extends RoomDatabase {

    private static final String FRA = "fra";
    private static final String ENG = "eng";

    public abstract RestaurantDao restaurantDao();

    public abstract InspectionReportDao inspectionReportDao();

    public abstract ViolationDao violationDao();

    private static volatile RestaurantDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    private static final String DB_NAME = "restaurant_database";

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static List<RestaurantUpdate> defaultRestaurants;
    private static List<InspectionReport> defaultInspections;
    private static List<Violation> defaultViolationsEng;
    private static List<Violation> defaultViolationsFra;

    private static ObjectMapper objectMapper;

    public static RestaurantDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RestaurantDatabase.class) {
                if (INSTANCE == null) {
                    objectMapper = new ObjectMapper();

                    defaultRestaurants = getDefaultRestaurants(context);
                    defaultInspections = getDefaultInspections(context);
                    defaultViolationsEng = getDefaultViolations(context, ENG);
                    defaultViolationsFra = getDefaultViolations(context, FRA);

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RestaurantDatabase.class, DB_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static List<RestaurantUpdate> getDefaultRestaurants(final Context context) {
        try {
            String jsonString = Utils.readJsonFromAssets(context, Constants.RESTAURANT_FILE);

            TypeReference<List<RestaurantUpdate>> typeReference = new TypeReference<List<RestaurantUpdate>>() {
            };

            List<RestaurantUpdate> restaurants = objectMapper.readValue(jsonString, typeReference);

            return restaurants;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private static List<InspectionReport> getDefaultInspections(final Context context) {
        try {
            String jsonString = Utils.readJsonFromAssets(context, Constants.INSPECTION_REPORT_FILE);
            TypeReference<List<InspectionReport>> typeReference =
                    new TypeReference<List<InspectionReport>>() {
                    };

             List<InspectionReport> reports = objectMapper.readValue(jsonString, typeReference);

            return reports;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private static List<Violation> getDefaultViolations(final Context context, String lang) {

        String file = Constants.VIOLATION_FILE_ENG;

        if(FRA.equals(lang)) {
            file = Constants.VIOLATION_FILE_FRA;
        }

        try {
            String jsonString = Utils.readJsonFromAssets(context, file);
            TypeReference<List<Violation>> typeReference =
                    new TypeReference<List<Violation>>() {
                    };

            List<Violation> violations = objectMapper.readValue(jsonString, typeReference);

            return violations;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {

                RestaurantDao restaurantDao = INSTANCE.restaurantDao();
                restaurantDao.insertOrUpdate(defaultRestaurants);

                InspectionReportDao inspectionReportDao = INSTANCE.inspectionReportDao();
                inspectionReportDao.insertOrUpdate(defaultInspections);

                ViolationDao violationDao = INSTANCE.violationDao();
                violationDao.insertOrUpdate(defaultViolationsEng);
                violationDao.insertOrUpdate(defaultViolationsFra);
            });
        }
    };

}
