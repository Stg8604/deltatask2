package com.example.deltaproject2;

import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {
    @GET("/tip")
    public Call<Data> getService();
    @GET("/scores")
    public Call<Data> getScores();
    @GET("/word")
    public Call<Data> getWord();
    //@POST("/characters")
    //public Call<Data> getCharacters(@Body RequestBody requestBody);
    @POST("/character")
    Call<Data> getrandomcharacter(@Body CharReq request);

    @POST("/characters")
    Call<Data> getallcharacters(@Body CharReq request);
}
