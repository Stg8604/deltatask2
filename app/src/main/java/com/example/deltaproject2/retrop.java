package com.example.deltaproject2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrop {
    private static Retrofit retrofit;
    private static final String url="https://api-obstacle-dodge.vercel.app";
    public static Retrofit getint() {
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    // Create an instance of the ApiService interface
    Service service = retrofit.create(Service.class);
}
