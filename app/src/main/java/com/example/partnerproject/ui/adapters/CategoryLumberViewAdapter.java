package com.example.partnerproject.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.partnerproject.R;
import com.example.partnerproject.objects.CategoryLumber;

import java.util.Collections;
import java.util.List;

public class CategoryLumberViewAdapter extends RecyclerView.Adapter<CategoryLumberViewAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final Context context;
    private final List<CategoryLumber> listCategoriesLumbers;
    private final int widthDisplay;
    private boolean pointStartPosition = false;

    private final CategoryLumberClickListener onCardLumberClickListener;

    public CategoryLumberViewAdapter(Context context, List<CategoryLumber> listCategoriesLumbers, CategoryLumberClickListener clickListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listCategoriesLumbers = listCategoriesLumbers;
        this.onCardLumberClickListener = clickListener;
        widthDisplay = context.getResources().getDisplayMetrics().widthPixels;
    }

    @NonNull
    @Override
    public CategoryLumberViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCategoryLumber = inflater.inflate(R.layout.category_lumber_for_adapter, parent, false);
        return new ViewHolder(viewCategoryLumber);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryLumberViewAdapter.ViewHolder holder, int position) {
        CategoryLumber categoryLumber = listCategoriesLumbers.get(position);
        String nameCategory = categoryLumber.getNameCategory();
        holder.categoryLumberTextView.setText(nameCategory);
        if (pointStartPosition && position == 0) {
            holder.categoryLumberTextView.setBackground(context.getDrawable(R.drawable.rounded_corner2_green));
        }
        holder.categoryLumberTextView.setPadding(widthDisplay / 10 - 10, 5, widthDisplay / 10 - 10, 5);
        holder.categoryLumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardLumberClickListener.onCategoryLumberClick(categoryLumber);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategoriesLumbers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView categoryLumberTextView;

        ViewHolder(View view) {
            super(view);
            categoryLumberTextView = view.findViewById(R.id.textview_name_category_lumber);
        }
    }

    public void moveToStartPosition(String value) {
        for (CategoryLumber category : listCategoriesLumbers) {
            String nameCategory = category.getNameCategory();
            if (nameCategory.equals(value)) {
                pointStartPosition = true;
                Collections.swap(listCategoriesLumbers, listCategoriesLumbers.indexOf(category), 0);
            }
        }
    }

}
