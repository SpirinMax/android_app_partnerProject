package com.example.partnerproject.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClient {
    private static Retrofit getRetrofit() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.174:8081/restApi/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public static ApiService getApiService(){
        ApiService apiService = getRetrofit().create(ApiService.class);
        return apiService;
    }
}
