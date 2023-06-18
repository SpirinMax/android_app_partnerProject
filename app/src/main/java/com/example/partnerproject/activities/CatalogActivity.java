package com.example.partnerproject.activities;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.partnerproject.R;
import com.example.partnerproject.objects.CategoryLumber;
import com.example.partnerproject.objects.FilterParameters;
import com.example.partnerproject.objects.PriceLumber;
import com.example.partnerproject.retrofit.ApiClient;
import com.example.partnerproject.ui.adapters.CategoryLumberClickListener;
import com.example.partnerproject.ui.adapters.CategoryLumberViewAdapter;
import com.example.partnerproject.ui.adapters.GalleryLumberViewAdapter;
import com.example.partnerproject.ui.fragments.ContentFragmentTopMenu;
import com.example.partnerproject.utils.UtilsActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogActivity extends AppCompatActivity {
    private TextInputLayout searchTextview;
    private RecyclerView categoriesLumbersRecyclerView;
    private RecyclerView galleryLumbersRecyclerView;
    private ImageView filterImageView;
    private Context context;
    private FilterParameters filterParameters;
    private CategoryLumberViewAdapter categoryLumberViewAdapter;
    private GalleryLumberViewAdapter galleryLumberAdapter;
    private List<CategoryLumber> listCategoriesLumbers = new ArrayList<>();
    private List<PriceLumber> listPrices = new ArrayList<>();
    private int currentPageGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        context = this;
        searchTextview = findViewById(R.id.textinputlayout_search);
        categoriesLumbersRecyclerView = findViewById(R.id.categories_lumbers_recyclerView);
        galleryLumbersRecyclerView = findViewById(R.id.list_lumbers_recyclerView);
        filterImageView = findViewById(R.id.imageview_filter);


        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            if (arguments.containsKey(FilterParameters.class.getSimpleName())) {
                filterParameters = (FilterParameters) arguments.getSerializable(FilterParameters.class.getSimpleName());
                currentPageGallery = filterParameters.getNumberPage();
            }
        }
        System.out.println(filterParameters.toString());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.top_menu, new ContentFragmentTopMenu(true, getString(R.string.catalog)), null)
                    .commit();
        }


        CategoryLumberClickListener listener = new CategoryLumberClickListener() {
            @Override
            public void onCategoryLumberClick(CategoryLumber category) {
                String nameCategory = category.getNameCategory();
                FilterParameters newFilter = new FilterParameters();
                newFilter.setSortingByDiscountDesc(true);
                newFilter.setCategoryLumber(nameCategory);
                setFilter(newFilter);
                System.out.println(filterParameters.toString());
                updateCategoriesLumbersRecyclerView(nameCategory);
                updateRecyclerViewGalleryLumber();
            }
        };
        Call<List<CategoryLumber>> callCategoryLumber = ApiClient.getApiService().receiveListCategoriesLumbers();
        callCategoryLumber.enqueue(new Callback<List<CategoryLumber>>() {
            @Override
            public void onResponse(Call<List<CategoryLumber>> call, Response<List<CategoryLumber>> response) {
                if (response.isSuccessful()) {
                    listCategoriesLumbers = response.body();
                    categoryLumberViewAdapter = new CategoryLumberViewAdapter(context, listCategoriesLumbers, listener);
                    categoryLumberViewAdapter.moveToStartPosition(filterParameters.getCategoryLumber());
                    categoriesLumbersRecyclerView.setAdapter(categoryLumberViewAdapter);
                } else {
                    Toast.makeText(context, "Данные не были загружены!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryLumber>> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(context, "  Нет подключения к сети!", Toast.LENGTH_LONG).show();
            }
        });

        updateRecyclerViewGalleryLumber();

        filterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterImageView.setBackground(context.getDrawable(R.drawable.round_anim));
                TransitionDrawable transition = (TransitionDrawable)  filterImageView.getBackground();
                transition.startTransition(2000);
                transition.reverseTransition(2000);
                UtilsActivity.goToActivityWithPutExtra(context, FilterActivity.class, filterParameters);
            }
        });
    }

    public void setFilter(FilterParameters filter) {
        this.filterParameters = filter;
    }

    public FilterParameters getFilter() {
        return filterParameters;
    }

    private void updateRecyclerViewGalleryLumber() {
        Call<List<PriceLumber>> callPrices = ApiClient.getApiService().receivePagePrices(filterParameters);
        callPrices.enqueue(new Callback<List<PriceLumber>>() {
            @Override
            public void onResponse(Call<List<PriceLumber>> call, Response<List<PriceLumber>> response) {
                if (response.isSuccessful()) {
                    listPrices = response.body();
                    galleryLumberAdapter = new GalleryLumberViewAdapter(context, listPrices, filterParameters);
                    galleryLumbersRecyclerView.setAdapter(galleryLumberAdapter);
                } else if (response.code() == 404) {
                    Toast.makeText(context, "Данные не найдены!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PriceLumber>> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(context, "  Нет подключения к сети!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateCategoriesLumbersRecyclerView(String nameCategory) {
        categoryLumberViewAdapter.moveToStartPosition(nameCategory);
        categoriesLumbersRecyclerView.setAdapter(categoryLumberViewAdapter);
    }

    public void addLumbersInAdapter(View view) {
        currentPageGallery++;
        filterParameters.setNumberPage(currentPageGallery);
        Call<List<PriceLumber>> callPrices = ApiClient.getApiService().receivePagePrices(filterParameters);
        callPrices.enqueue(new Callback<List<PriceLumber>>() {
            @Override
            public void onResponse(Call<List<PriceLumber>> call, Response<List<PriceLumber>> response) {
                if (response.isSuccessful()) {
                    List<PriceLumber> newPage = response.body();
                    if (newPage != null && newPage.size() != 0) {
                        int pos = listPrices.size();
                        int countNewItem = newPage.size();
                        listPrices.addAll(newPage);
                        galleryLumberAdapter.notifyItemRangeInserted(pos, countNewItem);
                    }

                } else if (response.code() == 404) {
                    Toast.makeText(context, "Данные не найдены!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PriceLumber>> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(context, "  Нет подключения к сети!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void searchClick(View view) {
        String nameLumber = searchTextview.getEditText().getText().toString();
        if (!nameLumber.isEmpty()) {
            FilterParameters newFilter = new FilterParameters();
            newFilter.setSortingByDiscountDesc(true);
            newFilter.setNameLumber(nameLumber);
            setFilter(newFilter);
            updateRecyclerViewGalleryLumber();
        }
    }
}