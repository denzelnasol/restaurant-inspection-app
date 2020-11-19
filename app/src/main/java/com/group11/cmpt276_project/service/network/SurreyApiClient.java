package com.group11.cmpt276_project.service.network;

import com.group11.cmpt276_project.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

//Singleton that holds the Retrofit instance for surrey api
public class SurreyApiClient {

    private static class SurreyApiClientHolder {
        private static Retrofit INSTANCE = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {
        return SurreyApiClientHolder.INSTANCE;
    }
}
