package com.example.partnerproject.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.partnerproject.R;
import com.example.partnerproject.objects.FilterParameters;
import com.example.partnerproject.ui.fragments.ButtonFragmentShortCatalog;
import com.example.partnerproject.ui.fragments.ContentFragmentTopMenu;
import com.example.partnerproject.utils.UtilsActivity;

public class CatalogShortActivity extends AppCompatActivity {
    private FilterParameters filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_short);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            String name = arguments.get(UtilsActivity.NAME_KEY_PREVIOUS_ACTIVITY).toString();
            System.out.println("\n\n" + name + "\n\n");
            if (arguments.containsKey(FilterParameters.class.getSimpleName())) {
                filter = (FilterParameters) arguments.getSerializable(FilterParameters.class.getSimpleName());
            }
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.top_menu, new ContentFragmentTopMenu(true, ""), null)
                    .commit();
        }

        loadFragmentsButtons();

    }

    private void loadFragmentsButtons() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.button_fragment_shortcatalog1, new ButtonFragmentShortCatalog( getString(R.string.pine)), null)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.button_fragment_shortcatalog2, new ButtonFragmentShortCatalog( getString(R.string.spruce)), null)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.button_fragment_shortcatalog3, new ButtonFragmentShortCatalog( getString(R.string.larch)), null)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.button_fragment_shortcatalog4, new ButtonFragmentShortCatalog( getString(R.string.pine_spruce)), null)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.button_fragment_shortcatalog5, new ButtonFragmentShortCatalog( getString(R.string.birch)), null)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.button_fragment_shortcatalog6, new ButtonFragmentShortCatalog( getString(R.string.aspen)), null)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.button_fragment_shortcatalog7, new ButtonFragmentShortCatalog( getString(R.string.category_firewoods)), null)
                .commit();
    }
}