package com.example.partnerproject.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.partnerproject.activities.AuthActivity;
import com.example.partnerproject.activities.CatalogShortActivity;
import com.example.partnerproject.activities.MainActivity;
import com.example.partnerproject.objects.FilterParameters;
import com.example.partnerproject.objects.OrderLumber;
import com.example.partnerproject.objects.PriceLumber;

import java.io.Serializable;
import java.util.List;

public class UtilsActivity {
    public static final String NAME_KEY_PREVIOUS_ACTIVITY = "keyPreviousActivity";
    public static final String APP_PREFERENCES = "mainSettings";
    public static final String PREF_KEY_PHONE = "phone";
    public static final String PREF_KEY_FIO = "fio";
    public static final String PREF_KEY_ADDRESS = "address";
    public static final String PREF_KEY_LEGAL = "legal";
    public static final String PREF_KEY_LEGAL_ADDRESS = "legal_address";
    public static final String PREF_KEY_LEGAL_NAME_COMPANY = "legal_name_company";


    public static final String KEY_MAIN_ACTIVITY = MainActivity.class.getName();
    public static final String KEY_AUTH_ACTIVITY = AuthActivity.class.getName();
    public static final String KEY_CATALOG_SHORT_ACTIVITY = CatalogShortActivity.class.getName();

    private static final Class<?>[] CLASSES_FOR_PUT_EXTRA = {FilterParameters.class, PriceLumber.class, OrderLumber.class};

    public UtilsActivity() {

    }

    public static boolean checkUserDataByPhone(Context context) {
        boolean result = false;
        SharedPreferences settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (settings.contains(PREF_KEY_PHONE)) result = true;
        return result;
    }

    public static String getPreferenceByPhone(Context context) {
        SharedPreferences settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String value = "Вход";
        if (settings.contains(PREF_KEY_PHONE)) {
            value = settings.getString(PREF_KEY_PHONE, value);
        }
        return value;
    }


    public static void editSharedPreferencesByKey(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        settings.edit().putString(key, value).commit();
    }

    public static void editPhoneSharedPreferences(Context context, String value) {
        SharedPreferences settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        settings.edit().putString(PREF_KEY_PHONE, value).commit();
    }

    public static void removePhoneSharedPreferences(Context context) {
        SharedPreferences settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        settings.edit().remove(PREF_KEY_PHONE).commit();
    }

    public static void goToActivityWithPutExtra(Context context, Class<? extends AppCompatActivity> whereToGoActivity, List<Object> classesForPutExtra) {
        Intent intent = new Intent(context, whereToGoActivity);
        for (Object obj : classesForPutExtra) {
            if (obj instanceof Serializable) {
                if (obj.getClass().equals(CLASSES_FOR_PUT_EXTRA[0])) {
                    intent.putExtra(CLASSES_FOR_PUT_EXTRA[0].getSimpleName(), (FilterParameters) obj);
                } else if (obj.getClass().equals(CLASSES_FOR_PUT_EXTRA[1])) {
                    intent.putExtra(CLASSES_FOR_PUT_EXTRA[1].getSimpleName(), (PriceLumber) obj);
                } else if (obj.getClass().equals(CLASSES_FOR_PUT_EXTRA[2])) {
                    intent.putExtra(CLASSES_FOR_PUT_EXTRA[2].getSimpleName(), (OrderLumber) obj);
                }
            }
        }
        context.startActivity(intent);
    }

    public static void goToActivityWithPutExtra(Context context, Class<? extends AppCompatActivity> whereToGoActivity, Object classForPutExtra) {
        Intent intent = new Intent(context, whereToGoActivity);
        if (classForPutExtra instanceof Serializable) {
            if (classForPutExtra.getClass().equals(CLASSES_FOR_PUT_EXTRA[0])) {
                intent.putExtra(CLASSES_FOR_PUT_EXTRA[0].getSimpleName(), (FilterParameters) classForPutExtra);
            } else if (classForPutExtra.getClass().equals(CLASSES_FOR_PUT_EXTRA[1])) {
                intent.putExtra(CLASSES_FOR_PUT_EXTRA[1].getSimpleName(), (PriceLumber) classForPutExtra);
            } else if (classForPutExtra.getClass().equals(CLASSES_FOR_PUT_EXTRA[2])) {
                intent.putExtra(CLASSES_FOR_PUT_EXTRA[2].getSimpleName(), (OrderLumber) classForPutExtra);
            }
        }
        context.startActivity(intent);
    }

    public static void goToActivity(Context context, Class<? extends AppCompatActivity> whereToGoActivity) {
        Intent intent = new Intent(context, whereToGoActivity);
        context.startActivity(intent);
    }


}
