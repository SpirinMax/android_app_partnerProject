package com.example.partnerproject.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.partnerproject.R;
import com.example.partnerproject.objects.Customer;
import com.example.partnerproject.retrofit.ApiClient;
import com.example.partnerproject.retrofit.HandlerHttpCode;
import com.example.partnerproject.utils.FieldsClasses;
import com.example.partnerproject.utils.UtilsActivity;
import com.example.partnerproject.utils.UtilsTextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileMTextviewFragment extends Fragment {
    private String topTip;
    private String textInMTextView;

    private String fieldForUpdate;

    private boolean presentButtonForEditing = true;

    private TextView topTipTextView;
    private MaterialTextView mTextView;
    private ImageButton profileImageButton;

    public ProfileMTextviewFragment(String fieldForUpdate, String topTip, String textInMTextView, boolean presentButtonForEditing) {
        super(R.layout.profile_mtextview_fragment);
        this.topTip = topTip;
        this.textInMTextView = textInMTextView;
        this.fieldForUpdate = fieldForUpdate;
        this.presentButtonForEditing = presentButtonForEditing;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topTipTextView = view.findViewById(R.id.textview_top_tip);
        mTextView = view.findViewById(R.id.m_textview_profile);
        profileImageButton = view.findViewById(R.id.imagebutton_profile);
        topTipTextView.setText(topTip);
        mTextView.setText(textInMTextView);
        if (!presentButtonForEditing) {
            profileImageButton.setVisibility(View.GONE);
        }
        profileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation animClickIcon = AnimationUtils.loadAnimation(getActivity(), R.anim.click_button);
                view.startAnimation(animClickIcon);
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View alertDialogView = inflater.inflate(R.layout.editing_alert_dialog, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
                mDialogBuilder.setView(alertDialogView);
                //Настраиваем отображение поля для ввода текста в открытом диалоге:
                final TextInputLayout alertTextInput = (TextInputLayout) alertDialogView.findViewById(R.id.textinput_alertdialog);
                final TextInputEditText alertEditText = (TextInputEditText) alertDialogView.findViewById(R.id.edittext_alertdialog);
                if (topTip.equals(getString(R.string.phone))) {
                    UtilsTextInputLayout utilsTextInputLayout = new UtilsTextInputLayout(alertTextInput, UtilsTextInputLayout.PARAM_INPUT_PHONE);
                } else {
                    UtilsTextInputLayout utilsTextInputLayout = new UtilsTextInputLayout(alertTextInput, UtilsTextInputLayout.PARAM_INPUT_TEXT);
                }
                alertEditText.setHint(topTip);
                alertEditText.setHintTextColor(Color.GRAY);
                //Настраиваем сообщение в диалоговом окне:
                mDialogBuilder
                        .setTitle("Изменить параметр")
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Customer customer = new Customer();
                                        customer.setPhone(UtilsActivity.getPreferenceByPhone(getContext()));
                                        addFieldForEditing(customer, fieldForUpdate, alertEditText.getText().toString());
                                        Call<HandlerHttpCode> customerEditCall = ApiClient.getApiService().editCustomer(customer);
                                        customerEditCall.enqueue(new Callback<HandlerHttpCode>() {
                                            @Override
                                            public void onResponse(Call<HandlerHttpCode> call, Response<HandlerHttpCode> response) {
                                                if (response.isSuccessful()) {
                                                    getActivity().finish();
                                                    getActivity().startActivity(getActivity().getIntent());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<HandlerHttpCode> call, Throwable t) {

                                            }
                                        });
                                    }
                                })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                //Создаем AlertDialog:
                AlertDialog alertDialog = mDialogBuilder.create();

                //и отображаем его:
                alertDialog.show();
            }
        });

    }

    private void addFieldForEditing(Customer customer, String field, String value) {
        switch (field) {
            case (FieldsClasses.FIO):
                customer.setFIO(value);
                break;
            case (FieldsClasses.ADDRESS):
                customer.setAddress(value);
                break;
            case (FieldsClasses.LEGAL_ADDRESS):
                customer.setAddressCompany(value);
                break;
            case (FieldsClasses.LEGAL_NAME):
                customer.setNameCompany(value);
                break;
        }
    }
}
