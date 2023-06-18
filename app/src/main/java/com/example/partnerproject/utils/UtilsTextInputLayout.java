package com.example.partnerproject.utils;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class UtilsTextInputLayout {

    public static final String PARAM_INPUT_PHONE = "phone";
    public static final String PARAM_INPUT_TEXT = "text";
    private TextInputLayout textInputLayout;
    private String paramInput;

    public UtilsTextInputLayout(TextInputLayout textInputLayout, String paramInput) {
        this.textInputLayout = textInputLayout;
        this.paramInput = paramInput;
        setErrorTextWatcher();
        if (paramInput.equals(PARAM_INPUT_PHONE)){
            setTextWatcher(new PhoneNumberFormattingTextWatcher());
            this.textInputLayout.getEditText().setText("+7");
        }
    }

    public UtilsTextInputLayout() {

    }

    public static void setErrorForEmptyFields (List<TextInputLayout> fields){
        for (TextInputLayout t:fields){
            setErrorForEmptyTextInputLayout(t);
        }
    }

    public boolean checkingFieldOnEmpty() {
        boolean resultCheck = true;
        if (textInputLayout.getEditText().getText().toString().equals("")) resultCheck = false;
        return resultCheck;
    }

    public static boolean checkingFieldOnEmpty(TextInputLayout field) {
        boolean resultCheck = true;
        if (field.getEditText().getText().toString().equals("")) resultCheck = false;
        return resultCheck;
    }

    private static void setErrorForEmptyTextInputLayout(TextInputLayout field) {
        if (!checkingFieldOnEmpty(field)) {
            field.setError("Пустое поле");
        } else {
            field.setError("");
        }
    }
    private void setErrorForEmptyTextInputLayout() {
        if (!checkingFieldOnEmpty()) {
            textInputLayout.setError("Пустое поле");
        } else {
            textInputLayout.setError("");
        }
    }

    private void setErrorTextWatcher() {
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setErrorForEmptyTextInputLayout();
            }
        });
    }

    public boolean checkCorrectInputPhone (){
        if (paramInput.equals(PARAM_INPUT_PHONE) && textInputLayout.getEditText().getText().length() != 16){
            textInputLayout.setError("");
            return true;
        } else {
            textInputLayout.setError("Неверный формат номера");
            return false;
        }
    }

    private void setTextWatcher (TextWatcher textWatcher) {
        textInputLayout.getEditText().addTextChangedListener(textWatcher);
    }

    private void setErrorForPhoneFormatTextInputLayout () {
        if (paramInput.equals(PARAM_INPUT_PHONE)){
            if (textInputLayout.getEditText().getText().length() != 16) {
                textInputLayout.setError("Неверный формат номера");
            } else {
                textInputLayout.setError("");
            }
        }
    }
}
