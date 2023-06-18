package com.example.partnerproject.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.example.partnerproject.R;
import com.example.partnerproject.objects.Customer;
import com.example.partnerproject.objects.CustomerLogin;
import com.example.partnerproject.retrofit.ApiClient;
import com.example.partnerproject.retrofit.HandlerHttpCode;
import com.example.partnerproject.utils.UtilsActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {
    private List<TextInputLayout> listTextInputLayout;
    private TextInputEditText phoneEditText;
    private MaterialButton buttonAuth;
    private TextInputLayout login_textInputLayout, password_textInputLayout, FIO_textInputLayout, address_textInputLayout, nameLegalCompany, addressLegalCompany, phoneTextInputLayout;
    private MaterialCheckBox checkBoxForFieldLegalCompany;


    private FragmentContainerView bottomMenu;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        context = this;
        buttonAuth = findViewById(R.id.button_auth);
        login_textInputLayout = findViewById(R.id.textinput_login);
        password_textInputLayout = findViewById(R.id.textinput_pasword);
        phoneTextInputLayout = findViewById(R.id.phone_textinputlayout);
        phoneEditText = findViewById(R.id.edittext_phone);
        FIO_textInputLayout = findViewById(R.id.FIO_textinputlayout);
        address_textInputLayout = findViewById(R.id.address_textinputlayout);
        checkBoxForFieldLegalCompany = findViewById(R.id.i_legalcompany_checkbox);
        nameLegalCompany = findViewById(R.id.name_legal_company_textinputlayout);
        addressLegalCompany = findViewById(R.id.address_legal_company_textinputlayout);
        bottomMenu = findViewById(R.id.bottom_menu);

        listTextInputLayout = new ArrayList<>();
        listTextInputLayout.add(login_textInputLayout);
        listTextInputLayout.add(password_textInputLayout);
        listTextInputLayout.add(phoneTextInputLayout);
        listTextInputLayout.add(FIO_textInputLayout);
        listTextInputLayout.add(address_textInputLayout);
        listTextInputLayout.add(nameLegalCompany);
        listTextInputLayout.add(addressLegalCompany);

        setErrorTextWatcher(FIO_textInputLayout);
        setErrorTextWatcher(address_textInputLayout);
        setErrorTextWatcher(nameLegalCompany);
        setErrorTextWatcher(addressLegalCompany);
        setErrorTextWatcher(login_textInputLayout);
        setErrorTextWatcher(password_textInputLayout);

        phoneEditText.setText("+7");
        phoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                phoneTextInputLayout.setError("");
                if (editable.length() == 16) {
                    buttonAuth.setBackgroundColor(getColor(R.color.green));
                } else {
                    buttonAuth.setBackgroundColor(getColor(R.color.inactive_button));
                }
            }
        });

    }

    public void sendAuthData(View view) {
        final Animation animClick = AnimationUtils.loadAnimation(this, R.anim.click_button);
        view.startAnimation(animClick);
        if (phoneEditText.getText().length() != 16) {
            phoneTextInputLayout.setError("Неверный формат номера");
        } else {
            phoneTextInputLayout.setError("");
        }
        setErrorForEmptyTextInputLayout(login_textInputLayout);
        setErrorForEmptyTextInputLayout(password_textInputLayout);
        setErrorForEmptyTextInputLayout(FIO_textInputLayout);
        setErrorForEmptyTextInputLayout(address_textInputLayout);
        setErrorForEmptyTextInputLayout(nameLegalCompany);
        setErrorForEmptyTextInputLayout(addressLegalCompany);
        if (checkEmptyFields() && checkLoginAndPasswordFiled()) {
            String login = login_textInputLayout.getEditText().getText().toString();
            String password = password_textInputLayout.getEditText().getText().toString();
            Call<HandlerHttpCode> customerCall = ApiClient.getApiService().saveCustomer(getFillingCustomer(), login, password);
            customerCall.enqueue(new Callback<HandlerHttpCode>() {
                @Override
                public void onResponse(Call<HandlerHttpCode> call, Response<HandlerHttpCode> response) {
                    if (response.code() == 200) {
                        UtilsActivity.editSharedPreferencesByKey(context, UtilsActivity.PREF_KEY_PHONE, phoneEditText.getText().toString());
                        Toast.makeText(context, "Данные сохранены успешно!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<HandlerHttpCode> call, Throwable t) {
                    Toast.makeText(context, "Не удалось сохранить данные!", Toast.LENGTH_SHORT).show();
                    System.out.println("---------OnFailure report-----------\n" + t);
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Регистрация").setMessage("Поля заполнены неверно!").create().show();
        }
    }

    private boolean checkLoginAndPasswordFiled() {
        boolean resultCheck = false;
        if (login_textInputLayout.getEditText().getText().toString().length() < 6)
            login_textInputLayout.setError("Минимальная длина - 6 символов");
        if (password_textInputLayout.getEditText().getText().toString().length() < 6) {
            password_textInputLayout.setError("Минимальная длина - 6 символов");
        }
        if (login_textInputLayout.getEditText().getText().toString().length() > 6 && password_textInputLayout.getEditText().getText().toString().length() < 6) {
            resultCheck = true;
            login_textInputLayout.setError("");
            password_textInputLayout.setError("");
        }
        return resultCheck;
    }

    private boolean checkEmptyFields() {
        boolean resultCheck = false;
        int amountSuccCheck = 0;
        if (checkBoxForFieldLegalCompany.isChecked()) {
            List<TextInputLayout> listFields = new ArrayList<>(listTextInputLayout);
            listFields.remove(address_textInputLayout);
            for (TextInputLayout field : listFields) {
                if (checkingFieldOnEmpty(field)) {
                    amountSuccCheck++;
                } else {
                    break;
                }
            }
            if (amountSuccCheck == listFields.size()) resultCheck = true;
        } else {
            List<TextInputLayout> listFields = new ArrayList<>(listTextInputLayout);
            listFields.remove(nameLegalCompany);
            listFields.remove(addressLegalCompany);
            for (TextInputLayout field : listFields) {
                if (checkingFieldOnEmpty(field)) {
                    amountSuccCheck++;
                } else {
                    break;
                }
            }
            if (amountSuccCheck == listFields.size()) resultCheck = true;
        }
        return resultCheck;
    }

    private boolean checkingFieldOnEmpty(TextInputLayout textInputLayout) {
        boolean resultCheck = true;
        if (textInputLayout.getEditText().getText().toString().equals("")) resultCheck = false;
        return resultCheck;
    }

    private void setErrorForEmptyTextInputLayout(TextInputLayout textInputLayout) {
        if (!checkingFieldOnEmpty(textInputLayout)) {
            textInputLayout.setError("Пустое поле");
        } else {
            textInputLayout.setError("");
        }
    }

    public void openFieldLegalCompany(View view) {
        if (checkBoxForFieldLegalCompany.isChecked()) {
            nameLegalCompany.setVisibility(View.VISIBLE);
            addressLegalCompany.setVisibility(View.VISIBLE);
            address_textInputLayout.setVisibility(View.GONE);
        } else {
            nameLegalCompany.setVisibility(View.GONE);
            addressLegalCompany.setVisibility(View.GONE);
            address_textInputLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setErrorTextWatcher(TextInputLayout textInputLayout) {
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setErrorForEmptyTextInputLayout(textInputLayout);
            }
        });
    }

    private CustomerLogin getFillingCustomerLogin() {
        String login = login_textInputLayout.getEditText().getText().toString();
        String password = password_textInputLayout.getEditText().getText().toString();
        return new CustomerLogin(login, password);
    }

    private Customer getFillingCustomer() {
        Customer customer = new Customer();
        customer.setPhone(phoneTextInputLayout.getEditText().getText().toString());
        customer.setFIO(FIO_textInputLayout.getEditText().getText().toString());
        customer.setAddress(address_textInputLayout.getEditText().getText().toString());
        customer.setLegalCompany(checkBoxForFieldLegalCompany.isChecked());
        customer.setAddressCompany(addressLegalCompany.getEditText().getText().toString());
        customer.setNameCompany(nameLegalCompany.getEditText().getText().toString());
        return customer;
    }

}