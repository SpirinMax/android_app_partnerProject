package com.example.partnerproject.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.partnerproject.R;
import com.example.partnerproject.objects.Customer;
import com.example.partnerproject.objects.CustomerLogin;
import com.example.partnerproject.retrofit.ApiClient;
import com.example.partnerproject.utils.UtilsActivity;
import com.example.partnerproject.utils.UtilsTextInputLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Context context;
    private TextInputLayout loginTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private List<TextInputLayout> fields = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

        context = this;
        loginTextInputLayout = findViewById(R.id.textinput_login);
        passwordTextInputLayout = findViewById(R.id.textinput_pasword);
        UtilsTextInputLayout utilsTextInputLayout1 = new UtilsTextInputLayout(loginTextInputLayout, "text");
        UtilsTextInputLayout utilsTextInputLayout2 = new UtilsTextInputLayout(passwordTextInputLayout, "text");
        fields.add(loginTextInputLayout);
        fields.add(passwordTextInputLayout);

    }

    public void sendLoginData(View view) {
        UtilsTextInputLayout.setErrorForEmptyFields(fields);
        if (UtilsTextInputLayout.checkingFieldOnEmpty(loginTextInputLayout) && UtilsTextInputLayout.checkingFieldOnEmpty(passwordTextInputLayout)) {
            String login = loginTextInputLayout.getEditText().getText().toString();
            String password = passwordTextInputLayout.getEditText().getText().toString();
            CustomerLogin customerLogin = new CustomerLogin(login, password);
            Call<Customer> customerCall = ApiClient.getApiService().authCustomer(customerLogin);
            customerCall.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    if (response.code() == 200) {
                        Customer customer = response.body();
                        UtilsActivity.editPhoneSharedPreferences(context, customer.getPhone());
                        Intent profileActivity = new Intent(context, ProfileActivity.class);
                        profileActivity.putExtra(UtilsActivity.NAME_KEY_PREVIOUS_ACTIVITY,LoginActivity.class.getName());
                        startActivity(profileActivity);
                    } else if (response.code() == 401) {
                        passwordTextInputLayout.getEditText().setText("");
                        loginTextInputLayout.getEditText().setText("");
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Авторизация").setMessage("Неверный логин или пароль!").create().show();
                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {

                }
            });
        }
    }
}