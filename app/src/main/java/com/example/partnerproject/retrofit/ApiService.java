package com.example.partnerproject.retrofit;

import com.example.partnerproject.objects.CategoryLumber;
import com.example.partnerproject.objects.Customer;
import com.example.partnerproject.objects.CustomerLogin;
import com.example.partnerproject.objects.FilterParameters;
import com.example.partnerproject.objects.OrderLumber;
import com.example.partnerproject.objects.PriceLumber;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("customerPhone")
    Call<Customer> receiveCustomerByPhone(@Query("phone") String phone);

    @GET("lumber/category/getAll")
    Call<List<CategoryLumber>> receiveListCategoriesLumbers();

    @POST("customer/auth")
    Call<Customer> authCustomer(@Body CustomerLogin customerLogin);

    @POST("customer/create")
    Call<HandlerHttpCode> saveCustomer(@Body Customer customer, @Query("login") String login, @Query("password") String password);

    @POST("customer/upd")
    Call<HandlerHttpCode> editCustomer(@Body Customer customer);

    @POST("/restApi/price")
    Call<List<PriceLumber>> receivePagePrices(@Body FilterParameters filter);
    @POST("order/create")
    Call<HandlerHttpCode> createOrder (@Body OrderLumber order, @Query("phone") String phone);
}
