package com.example.partnerproject.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.partnerproject.R;
import com.example.partnerproject.utils.UtilsActivity;

public class SelectAuthActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_auth);
        context = this;
    }

    public void goToLoginActivity(View view) {
        Intent loginActivity = new Intent(context, LoginActivity.class);
        loginActivity.putExtra(UtilsActivity.NAME_KEY_PREVIOUS_ACTIVITY, SelectAuthActivity.class.getName());
        startActivity(loginActivity);
    }

    public void goToRegisterActivity(View view) {
        Intent regActivity = new Intent(context, AuthActivity.class);
        regActivity.putExtra(UtilsActivity.NAME_KEY_PREVIOUS_ACTIVITY, SelectAuthActivity.class.getName());
        startActivity(regActivity);
    }
}