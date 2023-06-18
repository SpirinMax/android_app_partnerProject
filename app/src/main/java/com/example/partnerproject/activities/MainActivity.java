package com.example.partnerproject.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.partnerproject.R;
import com.example.partnerproject.ui.fragments.ContentFragmentBottomMenu;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.bottom_menu, ContentFragmentBottomMenu.class, null)
                    .commit();
        }

        ImageView contactMap = findViewById(R.id.contact_imageview);
        contactMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSelectMapsActivity();
            }
        });

        TextView contactTextView = findViewById(R.id.contact_textview);
        contactTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSelectMapsActivity();
            }
        });



    }


    private void startSelectMapsActivity() {
        Uri uri = Uri.parse("geo:57.163480,43.172646?z=15");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
