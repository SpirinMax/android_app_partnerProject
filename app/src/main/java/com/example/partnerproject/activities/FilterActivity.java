package com.example.partnerproject.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.partnerproject.R;
import com.example.partnerproject.objects.FilterParameters;
import com.example.partnerproject.ui.fragments.ButtonClicking;
import com.example.partnerproject.ui.fragments.ContentFragmentTopMenu;
import com.example.partnerproject.ui.fragments.FiledFilterFragment;
import com.example.partnerproject.utils.KeysFilter;
import com.example.partnerproject.utils.MathUtils;
import com.example.partnerproject.utils.UtilsActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    private TextInputLayout startPriceTextInput;
    private TextInputLayout finalPriceTextInput;
    private TextView sortResetTextView;
    private FilterParameters filterParameters;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        context = this;
        startPriceTextInput = findViewById(R.id.textinput_start_price_filter);
        finalPriceTextInput = findViewById(R.id.textinput_final_price_filter);
        sortResetTextView = findViewById(R.id.textview_sort_filter_reset);
        sortResetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetSortFilter();
            }
        });
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            filterParameters = (FilterParameters) arguments.getSerializable(FilterParameters.class.getSimpleName());
        }
        System.out.println(filterParameters.toString());
        fillFilterFieldsPricesAndSort ();
        String[] nameBreed = {"Сосна", "Ель", "Лиственница", "Сосна+Ель", "Береза"};
        String[] categoriesLumber = {"Доски", "Брус", "Кругляк", "Дрова"};
        String[] categoriesPrices = {"По м³", "По шт"};
        String[] diameters = {String.valueOf(200)};
        String[] widths = {String.valueOf(200), String.valueOf(150)};
        String[] lengths = {String.valueOf(6000)};
        String[] thicknesses = {String.valueOf(50), String.valueOf(200)};
        String[] sorts = {getString(R.string.sort_amount_order), getString(R.string.sort_max_price), getString(R.string.sort_min_price)};
        List<String> listCategoriesLumber = Arrays.asList(categoriesLumber);
        List<String> listBreed = Arrays.asList(nameBreed);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.top_menu, new ContentFragmentTopMenu(true, getString(R.string.filter_apply), getButtonClicking(), getString(R.string.filter)), null)
                    .commit();
            getSupportFragmentManager().beginTransaction().add(R.id.filter_field1, new FiledFilterFragment(listBreed, getString(R.string.breed_wood), KeysFilter.BREED), null).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.filter_field2, new FiledFilterFragment(listCategoriesLumber, getString(R.string.category_lumber), KeysFilter.CATEGORY_LUMBER), null).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.filter_field3, new FiledFilterFragment(Arrays.asList(categoriesPrices), getString(R.string.category_price), KeysFilter.CATEGORY_PRICE), null).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.filter_field4, new FiledFilterFragment(Arrays.asList(diameters), getString(R.string.diameter), KeysFilter.DIAMETER), null).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.filter_field5, new FiledFilterFragment(Arrays.asList(widths), getString(R.string.width), KeysFilter.WIDTH), null).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.filter_field6, new FiledFilterFragment(Arrays.asList(lengths), getString(R.string.length), KeysFilter.LENGTH), null).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.filter_field7, new FiledFilterFragment(Arrays.asList(thicknesses), getString(R.string.thickness), KeysFilter.THICKNESS), null).commit();
        }
    }

    public void goToGalleryLumberActivity() {
        UtilsActivity.goToActivityWithPutExtra(context,CatalogActivity.class, filterParameters);
    }

    public FilterParameters getFilter() {
        return filterParameters;
    }

    public void onSortRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked){
            switch(view.getId()) {
                case R.id.radiobutton_sort_amount_order:
                    filterParameters.setSortingByAmountOrders(true);
                    filterParameters.setSortingByMaxPrice(false);
                    filterParameters.setSortingByMinPrice(false);
                    filterParameters.setAvailDiscount(false);
                    break;
                case R.id.radiobutton_sort_max_price:
                    filterParameters.setSortingByAmountOrders(false);
                    filterParameters.setSortingByMaxPrice(true);
                    filterParameters.setSortingByMinPrice(false);
                    filterParameters.setAvailDiscount(false);
                    break;
                case R.id.radiobutton_sort_min_price:
                    filterParameters.setSortingByAmountOrders(false);
                    filterParameters.setSortingByMaxPrice(false);
                    filterParameters.setSortingByMinPrice(true);
                    filterParameters.setAvailDiscount(false);
                    break;
                case R.id.radiobutton_sort_only_discount:
                    filterParameters.setSortingByAmountOrders(false);
                    filterParameters.setSortingByMaxPrice(false);
                    filterParameters.setSortingByMinPrice(false);
                    filterParameters.setAvailDiscount(true);
                    break;
            }
        }
    }
    private ButtonClicking getButtonClicking (){
        return new ButtonClicking() {
            @Override
            public void clickButton() {
                String startPrice = startPriceTextInput.getEditText().getText().toString();
                String finalPrice =  finalPriceTextInput.getEditText().getText().toString();
                if (!startPrice.equals("")){
                   filterParameters.setStartPrice(Float.parseFloat(startPrice));
                } else {
                    filterParameters.setStartPrice(0);
                }
                if (!finalPrice.equals("")){
                    filterParameters.setFinalPrice(Float.parseFloat(finalPrice));
                } else {
                    filterParameters.setFinalPrice(0);
                }
                goToGalleryLumberActivity();
            }
        };
    }

    private void resetSortFilter (){
        filterParameters.setSortingByAmountOrders(false);
        filterParameters.setSortingByMaxPrice(false);
        filterParameters.setSortingByMinPrice(false);
    }

    private void fillFilterFieldsPricesAndSort (){
        RadioButton sortByOrderButton = findViewById(R.id.radiobutton_sort_amount_order);
        RadioButton sortByMaxPriceButton = findViewById(R.id.radiobutton_sort_max_price);
        RadioButton sortByMinButton = findViewById(R.id.radiobutton_sort_min_price);
        if (filterParameters.isSortingByAmountOrders()){
            sortByOrderButton.setChecked(true);
            sortByMaxPriceButton.setChecked(false);
            sortByMinButton.setChecked(false);
        } else if (filterParameters.isSortingByMaxPrice()) {
            sortByOrderButton.setChecked(false);
            sortByMaxPriceButton.setChecked(true);
            sortByMinButton.setChecked(false);
        } else if (filterParameters.isSortingByMinPrice()){
            sortByOrderButton.setChecked(false);
            sortByMaxPriceButton.setChecked(false);
            sortByMinButton.setChecked(true);
        }

        if (filterParameters.getStartPrice() !=0){
            startPriceTextInput.getEditText().setText(MathUtils.roundValueAndGetString(filterParameters.getStartPrice()));
        }
        if (filterParameters.getFinalPrice() !=0){
            finalPriceTextInput.getEditText().setText(MathUtils.roundValueAndGetString(filterParameters.getFinalPrice()));
        }
    }
}