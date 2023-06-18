package com.example.partnerproject.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.partnerproject.R;
import com.example.partnerproject.objects.Customer;
import com.example.partnerproject.objects.OrderLumber;
import com.example.partnerproject.retrofit.ApiClient;
import com.example.partnerproject.ui.adapters.OrdersCustomerAdapter;
import com.example.partnerproject.ui.fragments.ContentFragmentTopMenu;
import com.example.partnerproject.utils.UtilsActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerOrdersActivity extends AppCompatActivity {
    private RecyclerView orderRecyclerView;
    private TextView countOrdersTextView, labelOrdersTextView;

    private Context context;
    private Customer customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);

        context = this;
        orderRecyclerView = findViewById(R.id.list_orders_recyclerView);
        countOrdersTextView = findViewById(R.id.textview_count_orders_label);
        labelOrdersTextView = findViewById(R.id.textview_orders_customer_label);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.top_menu, new ContentFragmentTopMenu(true, getString(R.string.you_orders)), null)
                    .commit();
        }

        if (UtilsActivity.checkUserDataByPhone(context)) {
            Call<Customer> customerCall = ApiClient.getApiService().receiveCustomerByPhone(UtilsActivity.getPreferenceByPhone(this));
            customerCall.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    if (response.code() == 200) {
                        customer = response.body();
                        if (customer.getOrdersLumbers() !=null || !customer.getOrdersLumbers().isEmpty()){
                            labelOrdersTextView.setVisibility(View.GONE);
                            countOrdersTextView.setText("Всего заказов - " + customer.getOrdersLumbers().size() + " шт" );
                            List<OrderLumber> orders = new ArrayList<>(customer.getOrdersLumbers());
                            orders.sort(Comparator.comparing(OrderLumber::getDateOrder).reversed());
                            OrdersCustomerAdapter orderAdapter = new OrdersCustomerAdapter(context,orders);
                            orderRecyclerView.setAdapter(orderAdapter);
                        } else {
                            labelOrdersTextView.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    Toast.makeText(context, "Не удалось загрузить данные! Проверьте соединение!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            UtilsActivity.goToActivity(context, SelectAuthActivity.class);
        }
    }
}