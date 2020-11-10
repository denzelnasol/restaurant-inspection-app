package com.group11.cmpt276_project.service.network.endpoints;

import com.fasterxml.jackson.databind.JsonNode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataSetService {

    @GET("api/3/action/package_show")
    Call<JsonNode> getDataSet(@Query("id") String id);
}
