package com.example.partnerproject.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.partnerproject.R;
import com.example.partnerproject.objects.FilterParameters;
import com.example.partnerproject.objects.OrderLumber;
import com.example.partnerproject.objects.PriceLumber;
import com.example.partnerproject.retrofit.ApiClient;
import com.example.partnerproject.retrofit.HandlerHttpCode;
import com.example.partnerproject.ui.fragments.ContentFragmentTopMenu;
import com.example.partnerproject.utils.MathUtils;
import com.example.partnerproject.utils.UtilsActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LumberCardActivity extends AppCompatActivity {
    private Context context;
    private PriceLumber currentPriceLumber;
    private FilterParameters filterParameters;
    private int currentPageGallery;
    private boolean textInputCountChangeValueSeekBar = true;
    private boolean seekBarChangeValueFieldCount = true;
    private float price, finalPrice;
    private int countLumber;

    private TextView nameLumberTextView, amountOrdersTextView, categoryLumberTextView, nameBreedTextView,
            diameterTextView, lengthTextView, widthTextView, thicknessTextView, categoryPriceTextView1,
            categoryPriceTextView2, priceForOneTextView, amountDiscountTextView, diameterLabelTextVIew;
    private TextInputLayout countLumberTextInput, finalPriceTextInput;
    private SeekBar countLumberSeekbar;
    private ImageView imageLumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lumber_card);

        context = this;

        initViews();

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            if (arguments.containsKey(PriceLumber.class.getSimpleName())) {
                currentPriceLumber = (PriceLumber) arguments.getSerializable(PriceLumber.class.getSimpleName());
            }
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.top_menu, new ContentFragmentTopMenu(true, getString(R.string.lumber)), null)
                    .commit();
        }

        fillFieldsView();

    }

    public void confirmOrderClick(View view) {
        if (UtilsActivity.checkUserDataByPhone(context)) {
            DialogInterface.OnClickListener confirmOk = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    OrderLumber order = new OrderLumber();
                    order.setPriceLumber(currentPriceLumber);
                    order.setDateOrder(Calendar.getInstance().getTime());
                    order.setQuantity(countLumber);
                    order.setFinalPrice(finalPrice);
                    String phone = UtilsActivity.getPreferenceByPhone(context);
                    Call<HandlerHttpCode> orderCall = ApiClient.getApiService().createOrder(order, phone);
                    orderCall.enqueue(new Callback<HandlerHttpCode>() {
                        @Override
                        public void onResponse(Call<HandlerHttpCode> call, Response<HandlerHttpCode> response) {
                            if (response.code() == 200) {
                                Toast.makeText(context, "Заказ успешно создан!", Toast.LENGTH_LONG).show();
                                finish();
                            } else if (response.code() == 500) {
                                new AlertDialog.Builder(context).setTitle("Ошибка").setMessage("Возникла ошибка при создании заказа").create().show();
                            }
                        }

                        @Override
                        public void onFailure(Call<HandlerHttpCode> call, Throwable t) {
                            System.out.println(t);
                            new AlertDialog.Builder(context).setTitle("Ошибка").setMessage("Нет подключения к сети!").create().show();
                        }
                    });
                }
            };

            if (!finalPriceTextInput.getEditText().getText().toString().isEmpty()) {
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                mDialogBuilder
                        .setTitle("Подтверждение")
                        .setMessage("Вы действительно хотите сделать заказ на " + finalPrice + " рублей")
                        .setCancelable(false)
                        .setPositiveButton("ОК", confirmOk)
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.show();
            } else {
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                mDialogBuilder
                        .setTitle("Ошибка")
                        .setMessage("Заполните количество пиломатериала с помощью ввода или ползунка!")
                        .setCancelable(false)
                        .setNegativeButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.show();
            }
        } else {
            UtilsActivity.goToActivity(context, SelectAuthActivity.class);
        }
    }

    private void initViews() {
        nameLumberTextView = findViewById(R.id.textview_name_lumber);
        imageLumber = findViewById(R.id.imageview_image_lumber);
        amountOrdersTextView = findViewById(R.id.textview_amount_orders_label);
        categoryLumberTextView = findViewById(R.id.textview_name_category_lumber);
        nameBreedTextView = findViewById(R.id.textview_name_breed);
        diameterTextView = findViewById(R.id.textview_diameter);
        lengthTextView = findViewById(R.id.textview_len);
        widthTextView = findViewById(R.id.textview_width);
        thicknessTextView = findViewById(R.id.textview_thickhess);
        categoryPriceTextView1 = findViewById(R.id.textview_category_price_label_top);
        priceForOneTextView = findViewById(R.id.textview_price_for_one);
        categoryPriceTextView2 = findViewById(R.id.textview_category_price_label);
        countLumberTextInput = findViewById(R.id.textinput_count_lumber);
        countLumberSeekbar = findViewById(R.id.seekBar_count_lumber);
        finalPriceTextInput = findViewById(R.id.textinput_final_price);
        amountDiscountTextView = findViewById(R.id.textview_amount_discount);
        diameterLabelTextVIew = findViewById(R.id.textview_diameter_label);
    }

    private void fillFieldsView() {
        nameLumberTextView.setText(currentPriceLumber.getLumber().getNameLumber());
        byte[] imageL = currentPriceLumber.getLumber().getParametersLumber().getImageLumber();
        if (imageL != null) {
            Bitmap bitmapImageLumber = BitmapFactory.decodeByteArray(imageL, 0, imageL.length);
            imageLumber.setImageBitmap(bitmapImageLumber);
        } else {
            imageLumber.setImageDrawable(context.getDrawable(R.drawable.lumber_bez_foto));
        }
        amountOrdersTextView.setText("Купили " + String.valueOf(currentPriceLumber.getAmountOrders()) + " раз");
        String nameCategory = currentPriceLumber.getLumber().getParametersLumber().getCategoryLumber().getNameCategory();
        String varietyLumber = currentPriceLumber.getLumber().getParametersLumber().getVarietyLumber();
        categoryLumberTextView.setText(nameCategory + ", Сорт " + varietyLumber);
        String nameBreed = currentPriceLumber.getLumber().getParametersLumber().getBreedWood().getNameBreed();
        String varietyBreed = currentPriceLumber.getLumber().getParametersLumber().getBreedWood().getVarietyBreed();
        nameBreedTextView.setText(nameBreed + ", Сорт " + varietyBreed);
        int diameter = currentPriceLumber.getLumber().getParametersLumber().getDiameter();
        if (diameter != 0) {
            diameterTextView.setVisibility(View.VISIBLE);
            diameterLabelTextVIew.setVisibility(View.VISIBLE);
            diameterTextView.setText(String.valueOf(diameter) + " мм");
        } else {
            diameterTextView.setVisibility(View.GONE);
            diameterLabelTextVIew.setVisibility(View.GONE);
        }
        lengthTextView.setText(String.valueOf(currentPriceLumber.getLumber().getParametersLumber().getLenght()) + " мм");
        widthTextView.setText(String.valueOf(currentPriceLumber.getLumber().getParametersLumber().getWidth()) + " мм");
        thicknessTextView.setText(String.valueOf(currentPriceLumber.getLumber().getParametersLumber().getThickness()) + " мм");
        String[] categoryPriceWithSpace = currentPriceLumber.getCategoryPrice().getNameCategoryPrice().split(" ");
        TextInputEditText editTextCount = findViewById(R.id.edittext_count_label_hint);
        countLumberTextInput.setHint(categoryPriceWithSpace[1]);
        if (categoryPriceWithSpace.length == 2) {
            categoryPriceTextView1.setText(categoryPriceWithSpace[1]);
            categoryPriceTextView2.setText(categoryPriceWithSpace[1]);
        } else {
            categoryPriceTextView1.setText(currentPriceLumber.getCategoryPrice().getNameCategoryPrice());
            categoryPriceTextView2.setText(currentPriceLumber.getCategoryPrice().getNameCategoryPrice());
        }

        if (currentPriceLumber.getCategoryPrice().isDiscount()) {
            price = currentPriceLumber.getDiscountPrice();
            priceForOneTextView.setText(MathUtils.getIntegerValue(currentPriceLumber.getDiscountPrice()));
            amountDiscountTextView.setVisibility(View.VISIBLE);
            amountDiscountTextView.setText("-" + MathUtils.getIntegerValue(currentPriceLumber.getCategoryPrice().getDiscountAmount()) + "%");
        } else {
            price = currentPriceLumber.getPrice();
            priceForOneTextView.setText(MathUtils.getIntegerValue(price));
            amountDiscountTextView.setVisibility(View.GONE);
        }

        countLumberSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (seekBarChangeValueFieldCount && textInputCountChangeValueSeekBar) {
                    countLumberTextInput.getEditText().setText(String.valueOf(seekBar.getProgress()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        countLumberTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    countLumber = Integer.parseInt(editable.toString());
                    if (countLumber < countLumberSeekbar.getMin()) {
                        seekBarChangeValueFieldCount = false;
                        countLumberSeekbar.setProgress(countLumberSeekbar.getMin());
                    } else if (countLumber > countLumberSeekbar.getMax()) {
                        seekBarChangeValueFieldCount = false;
                        countLumberSeekbar.setProgress(countLumberSeekbar.getMax());
                    } else {
                        seekBarChangeValueFieldCount = true;
                        textInputCountChangeValueSeekBar = false;
                        countLumberSeekbar.setProgress(countLumber);
                        //Чтобы курсор не перемещался вначало EditText при изменении прогресса SeekBar
                        new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        textInputCountChangeValueSeekBar = true;
                                    }
                                }, 100);
                    }

                    finalPrice = countLumber * price;
                    finalPriceTextInput.getEditText().setText(MathUtils.roundValueAndGetString(finalPrice));
                }
            }
        });
    }
}