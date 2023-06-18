package com.example.partnerproject.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.partnerproject.R;

import java.util.List;

public class FilterParametersAdapter extends RecyclerView.Adapter<FilterParametersAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final Context context;
    private List<String> parametersFilter;
    private ParameterFilterClickListener clickListener;

    public FilterParametersAdapter(Context context, List<String> parametersFilter, ParameterFilterClickListener clickListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.parametersFilter = parametersFilter;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.radio_button_parameter_filter_vew, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String valueParameter = parametersFilter.get(position);
        holder.radioButton.setText(valueParameter);
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onRadioButtonFilterClick(valueParameter);
                holder.radioButton.setChecked(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parametersFilter.size();
    }

    public void markFirstRadioButton (@NonNull ViewHolder holder) {
        holder.radioButton.setChecked(true);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final RadioButton radioButton;
        ViewHolder(View view) {
            super(view);
            radioButton = view.findViewById(R.id.radiobutton_parameter);
        }
    }
}
