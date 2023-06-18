package com.example.partnerproject.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.partnerproject.R;
import com.example.partnerproject.activities.FilterActivity;
import com.example.partnerproject.objects.FilterParameters;
import com.example.partnerproject.ui.adapters.FilterParametersAdapter;
import com.example.partnerproject.ui.adapters.ParameterFilterClickListener;
import com.example.partnerproject.utils.KeysFilter;

import java.util.List;

public class FiledFilterFragment extends Fragment {
    private String labelParameter;
    private List<String> data;
    private FilterParameters filter;
    private String keyField;
    private String selectingParameter;
    private RelativeLayout fieldFilter;
    private RecyclerView recyclerViewParametersFilter;
    private FilterParametersAdapter adapter;
    private ParameterFilterClickListener clickListener;
    private TextView selectingParameterTextView;
    private TextView nameParameterTextView;
    private TextView resetFilterTextView;

    private boolean clickPermission = true;

    public FiledFilterFragment(List<String> data, String labelParameter, String keyField) {
        super(R.layout.filter_field_fragment);
        this.data = data;
        this.labelParameter = labelParameter;
        this.keyField = keyField;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        filter = ((FilterActivity) getActivity()).getFilter();
        fieldFilter = view.findViewById(R.id.rellayout_filter_filed);
        nameParameterTextView = view.findViewById(R.id.textveiw_parameter_filter);
        selectingParameterTextView = view.findViewById(R.id.textview_selecting_filter_param);
        recyclerViewParametersFilter = view.findViewById(R.id.recyclerview_parameters_filter);
        resetFilterTextView = view.findViewById(R.id.textveiw_reset_filter);

        nameParameterTextView.setText(labelParameter);
        selectingParameterTextView.setVisibility(View.GONE);

        fieldFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickPermission) {
                    recyclerViewParametersFilter.setVisibility(View.VISIBLE);
                }
            }
        });
        View.OnClickListener clickResetListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewParametersFilter.setVisibility(View.GONE);
                selectingParameterTextView.setVisibility(View.GONE);
                clickPermission = true;
                nameParameterTextView.setTextColor(getContext().getColor(R.color.grey2));
                selectingParameter = null;
                fillFilterInActivity(null);
            }
        };
        resetFilterTextView.setOnClickListener(clickResetListener);
        clickListener = new ParameterFilterClickListener() {
            @Override
            public void onRadioButtonFilterClick(String valueParameter) {
                setSelectedTextView(valueParameter);
                fillFilterInActivity(valueParameter);
            }
        };
        fillFieldsAccordingFilter();
        setAdapter();
    }

    private void fillFieldsAccordingFilter() {
        String nameBreed = filter.getNameBreed();
        String categoryLumber = filter.getCategoryLumber();
        String categoryPrice = filter.getCategoryPrice();
        String diameter = null;
        if (filter.getDiameter() !=0){
            diameter = String.valueOf(filter.getDiameter());
        }
        String width = null;
        if (filter.getWidth() !=0){
            width = String.valueOf(filter.getWidth());
        }
        String length = null;
        if (filter.getLenght() !=0){
            length = String.valueOf(filter.getLenght());
        }
        String thickness = null;
        if (filter.getThickness() !=0){
            thickness = String.valueOf(filter.getThickness());
        }

        if (keyField.equals(KeysFilter.BREED) && nameBreed != null && data.contains(nameBreed)) {
            setSelectedTextView(nameBreed);
        } else if (keyField.equals(KeysFilter.CATEGORY_LUMBER) && categoryLumber != null && data.contains(categoryLumber)) {
           setSelectedTextView(categoryLumber);
        } else if (keyField.equals(KeysFilter.CATEGORY_PRICE) && categoryPrice!=null && data.contains(categoryPrice)) {
            setSelectedTextView(categoryPrice);
        } else if (keyField.equals(KeysFilter.DIAMETER) && diameter!=null && data.contains(diameter)) {
            setSelectedTextView(diameter);
        } else if (keyField.equals(KeysFilter.WIDTH) && width!=null && data.contains(width)) {
            setSelectedTextView(width);
        } else if (keyField.equals(KeysFilter.LENGTH) && length!=null && data.contains(length)) {
            setSelectedTextView(length);
        } else if (keyField.equals(KeysFilter.THICKNESS) && thickness!=null && data.contains(thickness)) {
            setSelectedTextView(thickness);
        }
    }

    private void setSelectedTextView (String text){
        recyclerViewParametersFilter.setVisibility(View.GONE);
        selectingParameterTextView.setVisibility(View.VISIBLE);
        resetFilterTextView.setVisibility(View.VISIBLE);
        selectingParameterTextView.setText(text);
        nameParameterTextView.setTextColor(getContext().getColor(R.color.black));
        selectingParameterTextView.setTextColor(getContext().getColor(R.color.black));
        clickPermission = false;
        fillFilterInActivity(text);
    }

    private void setAdapter() {
        recyclerViewParametersFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        FilterParametersAdapter adapter = new FilterParametersAdapter(getContext(), data, clickListener);
        recyclerViewParametersFilter.setAdapter(adapter);
    }

    private void fillFilterInActivity(String value) {
        filter.setNumberPage(0);
        switch (keyField) {
            case (KeysFilter.BREED):
                filter.setNameBreed(value);
                break;
            case(KeysFilter.CATEGORY_LUMBER):
                filter.setCategoryLumber(value);
                break;
            case(KeysFilter.CATEGORY_PRICE):
                if (value == null){
                    filter.setStandardCategoryPrice();
                } else {
                    filter.setCategoryPrice(value);
                }
                break;
            case(KeysFilter.DIAMETER):
                filter.setDiameter(Integer.parseInt(value));
                break;
            case(KeysFilter.WIDTH):
                filter.setWidth(Integer.parseInt(value));
                break;
            case(KeysFilter.LENGTH):
                filter.setLenght(Integer.parseInt(value));
                break;
            case(KeysFilter.THICKNESS):
                filter.setThickness(Integer.parseInt(value));
                break;
        }
    }

}
