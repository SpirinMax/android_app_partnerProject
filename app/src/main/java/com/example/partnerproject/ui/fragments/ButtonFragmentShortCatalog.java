package com.example.partnerproject.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.partnerproject.R;
import com.example.partnerproject.activities.CatalogActivity;
import com.example.partnerproject.objects.FilterParameters;
import com.example.partnerproject.utils.UtilsActivity;

public class ButtonFragmentShortCatalog extends Fragment {
    private String textButton;
    private Button button;

    public ButtonFragmentShortCatalog(String textButton) {
        super(R.layout.button_shortcatalog_fragment);
        this.textButton = textButton;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        button = view.findViewById(R.id.button_short_catalog_breed);
        button.setText(textButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterParameters filter = new FilterParameters();
                if (textButton.equals(getString(R.string.category_firewoods))){
                    filter.setCategoryLumber(getString(R.string.category_firewoods));
                } else {
                    filter.setNameBreed(button.getText().toString());
                }
                filter.setSortingByDiscountDesc(true);
                UtilsActivity.goToActivityWithPutExtra(getContext(), CatalogActivity.class, filter);
            }
        });
    }
}
