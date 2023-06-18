package com.example.partnerproject.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.partnerproject.R;
import com.example.partnerproject.activities.AuthActivity;
import com.example.partnerproject.activities.CatalogShortActivity;
import com.example.partnerproject.activities.CustomerOrdersActivity;
import com.example.partnerproject.activities.LoginActivity;
import com.example.partnerproject.activities.MainActivity;
import com.example.partnerproject.activities.ProfileActivity;
import com.example.partnerproject.activities.SelectAuthActivity;
import com.example.partnerproject.utils.UtilsActivity;

public class ContentFragmentBottomMenu extends Fragment {
    public ContentFragmentBottomMenu() {
        super(R.layout.bottom_menu_fragment);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity().getClass().equals(MainActivity.class)) {
            ImageView imageView = view.findViewById(R.id.icon_main_imageview);
            imageView.setBackground(getContext().getDrawable(R.drawable.rounded_background_for_icon_bottom_menu));
        }

        if (getActivity().getClass().equals(CatalogShortActivity.class)) {
            ImageView imageView = view.findViewById(R.id.icon_catalog_imageview);
            imageView.setBackground(getContext().getDrawable(R.drawable.rounded_background_for_icon_bottom_menu));
        }

        if (getActivity().getClass().equals(CustomerOrdersActivity.class)) {
            ImageView imageView = view.findViewById(R.id.icon_orders_imageview);
            imageView.setBackground(getContext().getDrawable(R.drawable.rounded_background_for_icon_bottom_menu));
        }

        if (getActivity().getClass().equals(ProfileActivity.class)) {
            ImageView imageView = view.findViewById(R.id.icon_profile_imageview);
            imageView.setBackground(getContext().getDrawable(R.drawable.rounded_background_for_icon_bottom_menu));
        }


        RelativeLayout mainPage = view.findViewById(R.id.main_page_nav);
        mainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getActivity().getClass().equals(MainActivity.class))
                    UtilsActivity.goToActivity(getContext(), MainActivity.class);
            }
        });

        RelativeLayout catalogPage = view.findViewById(R.id.catalog_page_nav);
        catalogPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getActivity().getClass().equals(CatalogShortActivity.class)) {
                    UtilsActivity.goToActivity(getContext(), CatalogShortActivity.class);
                }
            }
        });

        RelativeLayout ordersCustomerIcon = view.findViewById(R.id.orders_customer_page_nav);
        ordersCustomerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getActivity().getClass().equals(CustomerOrdersActivity.class)) {
                    if (UtilsActivity.checkUserDataByPhone(getContext())) {
                        UtilsActivity.goToActivity(getContext(), CustomerOrdersActivity.class);
                        Toast.makeText(getContext(), "Вы не вошли в профиль!", Toast.LENGTH_SHORT).show();
                    } else {
                        UtilsActivity.goToActivity(getContext(), SelectAuthActivity.class);
                    }
                }
            }
        });

        RelativeLayout iconProfilePage = view.findViewById(R.id.profile_page_icon);
        iconProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation animClickIcon = AnimationUtils.loadAnimation(getActivity(), R.anim.click_icon);
                view.startAnimation(animClickIcon);
                if (!getActivity().getClass().equals(AuthActivity.class) || !getActivity().getClass().equals(SelectAuthActivity.class) || !getActivity().getClass().equals(LoginActivity.class)) {
                    if (UtilsActivity.checkUserDataByPhone(getContext())) {
                        UtilsActivity.goToActivity(getContext(), ProfileActivity.class);
                    } else {
                        UtilsActivity.goToActivity(getContext(), SelectAuthActivity.class);
                    }
                } else {
                    UtilsActivity.goToActivity(getContext(), SelectAuthActivity.class);
                }
            }
        });
    }

}
