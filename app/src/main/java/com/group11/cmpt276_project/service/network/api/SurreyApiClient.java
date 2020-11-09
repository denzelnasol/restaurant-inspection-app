package com.group11.cmpt276_project.service.network.api;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SurreyApiClient {

    private static final String BASE_URL = "https://data.surrey.ca/";

    private static class SurreyApiClientHolder {
        private static Retrofit INSTANCE = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {
        return SurreyApiClientHolder.INSTANCE;
    }
}
