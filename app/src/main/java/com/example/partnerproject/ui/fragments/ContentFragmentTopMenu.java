package com.example.partnerproject.ui.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.partnerproject.R;
import com.example.partnerproject.activities.ProfileActivity;
import com.example.partnerproject.activities.SelectAuthActivity;
import com.example.partnerproject.utils.UtilsActivity;
import com.google.android.material.button.MaterialButton;

public class ContentFragmentTopMenu extends Fragment {
    private boolean outputPhone = true;
    private boolean outputButtonEnd;
    private String textButton;
    private String headingToolBar;
    private ButtonClicking buttonClicking;

    public ContentFragmentTopMenu() {
        super(R.layout.top_menu_fragment);
    }

    public ContentFragmentTopMenu(boolean outputPhone, String headingToolBar) {
        super(R.layout.top_menu_fragment);
        this.outputPhone = outputPhone;
        this.headingToolBar = headingToolBar;
    }

    public ContentFragmentTopMenu(boolean outputButtonEnd, String textButton, ButtonClicking buttonClicking, String headingToolBar) {
        super(R.layout.top_menu_fragment);
        this.textButton = textButton;
        this.headingToolBar = headingToolBar;
        this.outputButtonEnd = outputButtonEnd;
        this.buttonClicking = buttonClicking;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_top);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        TextView nameToolBarTextView = view.findViewById(R.id.textview_name_top_toolbar);
        nameToolBarTextView.setText(headingToolBar);

        ImageView imageViewProfileIcon = view.findViewById(R.id.imageview_profile_top_toolbar);
        imageViewProfileIcon.setColorFilter(Color.parseColor("#37C12B"));

        TextView textViewPhoneToolBarTop = view.findViewById(R.id.textview_toolbar_phone);
        textViewPhoneToolBarTop.setText(UtilsActivity.getPreferenceByPhone(getContext()));

        MaterialButton buttonTopToolBar = view.findViewById(R.id.button_top_toolbar);
        buttonTopToolBar.setText(textButton);
        if (outputButtonEnd) {
            outputPhone = false;
            buttonTopToolBar.setVisibility(View.VISIBLE);
            buttonTopToolBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonClicking.clickButton();
                }
            });
        }

        LinearLayout profileLinearLayout = view.findViewById(R.id.linearlayout_profile_in_top_tollbar);
        if (outputPhone) {
            profileLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (UtilsActivity.checkUserDataByPhone(getContext())) {
                        UtilsActivity.goToActivity(getContext(),ProfileActivity.class);
                    } else {
                        UtilsActivity.goToActivity(getContext(), SelectAuthActivity.class);
                    }
                }
            });
        } else {
            profileLinearLayout.setVisibility(View.GONE);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }

}
