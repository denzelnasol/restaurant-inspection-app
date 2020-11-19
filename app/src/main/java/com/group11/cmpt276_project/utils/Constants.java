package com.group11.cmpt276_project.utils;

import com.group11.cmpt276_project.R;

import java.util.HashMap;
import java.util.Map;

/*
Constants used in various places
 */
public class Constants {

    public static String ENCODING = "UTF-8";
    public static String VIOLATION_FILE = "violations.json";
    public static final String INSPECTION_REPORT_FILE = "inspectionReports.json";
    public static final String RESTAURANT_FILE = "restaurants.json";
    public static final String LOW = "Low";
    public static final String MODERATE = "Moderate";
    public static final String HIGH = "High";
    public static final String CRITICAL = "Critical";
    public static final String NOT_CRITICAL = "Not Critical";
    public static final String TRACKING_NUMBER = "trackingNumber";
    public static final String INDEX = "index";
    public static final String PARENT = "parent";
    public static final String LAST_UPDATE_INSPECTION = "lastUpdateInspection";
    public static final String LAST_UPDATE_RESTAURANT = "lastUpdateRestaurant";
    public static final String LAST_UPDATE = "lastUpdate";
    public static final String BASE_URL = "https://data.surrey.ca/";
    public static final String SHARE_PREFERENCES_URL = "restaurantInspectionPreferences";
    public static final String CONNECTION_TEST_URL = "https://www.google.com";
    public static final String SEVEN_ELEVEN = "7-Eleven";
    public static final String AW = "A&W";
    public static final String BOOSTER_JUICE = "Booster Juice";
    public static final String BROWNS = "Browns Socialhouse";
    public static final String BUBBLE_TEA = "Bubble";
    public static final String CHURCHS_CHICKEN = "Church's Chicken";
    public static final String COFFEE = "Coffee";
    public static final String DOMINOS = "Domino's";
    public static final String DAIRY_QUEEN = "Dairy Queen";
    public static final String GRILL = "Grill";
    public static final String MCDONALDS = "Mcdonald's";
    public static final String PIZZAHUT = "Pizza Hut";
    public static final String REAL_CANADIAN = "Real Canadian Superstore";
    public static final String STARBUCKS = "Starbucks";
    public static final String SUBWAY = "Subway";
    public static final String SUSHI = "Sushi";
    public static final String TIM_HORTONS = "Tim Hortons";
    public static final String WALMART = "Wal-mart";
    public static final String WENDYS = "Wendy's";
    public static final Map<String, Integer> RESTAURANT_ICON_MAP = new HashMap<String, Integer>(){{
        put(Constants.SEVEN_ELEVEN, R.drawable.res_ic_7eleven);
        put(Constants.AW, R.drawable.res_ic_aw);
        put(Constants.BOOSTER_JUICE, R.drawable.res_ic_booster_juice);
        put(Constants.BROWNS, R.drawable.res_ic_browns);
        put(Constants.BUBBLE_TEA, R.drawable.res_ic_bubble_tea);
        put(Constants.CHURCHS_CHICKEN, R.drawable.res_ic_churchs_chicken);
        put(Constants.COFFEE, R.drawable.res_ic_coffee);
        put(Constants.DAIRY_QUEEN, R.drawable.res_ic_dairy_queen);
        put(Constants.DOMINOS, R.drawable.res_ic_dominos);
        put(Constants.GRILL, R.drawable.res_ic_grill);
        put(Constants.MCDONALDS, R.drawable.res_ic_mcdonalds);
        put(Constants.PIZZAHUT, R.drawable.res_ic_pizzahut);
        put(Constants.REAL_CANADIAN, R.drawable.res_ic_real_canadian);
        put(Constants.STARBUCKS, R.drawable.res_ic_starbucks);
        put(Constants.SUBWAY, R.drawable.res_ic_subway);
        put(Constants.SUSHI, R.drawable.res_ic_sushi);
        put(Constants.TIM_HORTONS, R.drawable.res_ic_tim_hortons);
        put(Constants.WALMART, R.drawable.res_ic_walmart);
        put(Constants.WENDYS, R.drawable.res_ic_wendys);
    }};
}
