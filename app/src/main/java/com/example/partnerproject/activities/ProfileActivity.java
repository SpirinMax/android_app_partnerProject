package com.example.partnerproject.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.partnerproject.R;
import com.example.partnerproject.objects.Customer;
import com.example.partnerproject.retrofit.ApiClient;
import com.example.partnerproject.ui.fragments.ContentFragmentTopMenu;
import com.example.partnerproject.ui.fragments.ProfileMTextviewFragment;
import com.example.partnerproject.utils.FieldsClasses;
import com.example.partnerproject.utils.UtilsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private Customer customer;
    private Context context;
    private TextView legalProfileTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = this;
        legalProfileTextView = findViewById(R.id.textview_you_legal);


        if (UtilsActivity.checkUserDataByPhone(this)) {
            Call<Customer> customerCall = ApiClient.getApiService().receiveCustomerByPhone(UtilsActivity.getPreferenceByPhone(this));
            customerCall.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    if (response.code() == 200) {
                        customer = response.body();
                        if (savedInstanceState == null) {
                            loadFragmentsMTextview();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {

                }
            });
        } else {
            UtilsActivity.goToActivity(context, SelectAuthActivity.class);
        }

    }

    private void loadFragmentsMTextview() {
        String fieldFIO = FieldsClasses.FIO;
        String fieldPhone = FieldsClasses.PHONE;
        String fieldAddress = FieldsClasses.ADDRESS;
        String fieldLegalAddress = FieldsClasses.LEGAL_ADDRESS;
        String fieldLegalName = FieldsClasses.LEGAL_NAME;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.top_menu, new ContentFragmentTopMenu(false, getString(R.string.profile)), null)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.profile_m_textview1, new ProfileMTextviewFragment(fieldFIO, getString(R.string.FIO), customer.getFIO(), true), null)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.profile_m_textview2, new ProfileMTextviewFragment(fieldPhone, getString(R.string.phone), customer.getPhone(), false), null)
                .commit();
        if (customer.isLegalCompany()) {
            legalProfileTextView.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.profile_m_textview3,
                            new ProfileMTextviewFragment(fieldLegalAddress, getString(R.string.address_legal), customer.getAddressCompany(),
                                    true), null)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.profile_m_textview4, new ProfileMTextviewFragment(fieldLegalName, getString(R.string.name_legal), customer.getNameCompany(), true), null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.profile_m_textview5, new ProfileMTextviewFragment(fieldAddress, getString(R.string.address), customer.getAddress(), true), null)
                    .commit();
        }
    }

    public void exitFromProfile(View view) {
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
        mDialogBuilder
                .setTitle("Выход из профиля")
                .setMessage("Вы желаете выйти из профиля")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                UtilsActivity.removePhoneSharedPreferences(context);
                                finish();
                            }
                        })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();
    }
}