package com.example.partnerproject.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.TransitionDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.partnerproject.R;
import com.example.partnerproject.activities.LumberCardActivity;
import com.example.partnerproject.objects.FilterParameters;
import com.example.partnerproject.objects.PriceLumber;
import com.example.partnerproject.utils.MathUtils;
import com.example.partnerproject.utils.UtilsActivity;

import java.util.List;

public class GalleryLumberViewAdapter extends RecyclerView.Adapter<GalleryLumberViewAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final Context context;
    private final List<PriceLumber> listPricesLumbers;
    private final FilterParameters filter;

    public GalleryLumberViewAdapter(Context context, List<PriceLumber> priceLumber, FilterParameters filter) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listPricesLumbers = priceLumber;
        this.filter = filter;
    }

    @NonNull
    @Override
    public GalleryLumberViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewGalleryLumber = inflater.inflate(R.layout.gallery_lumber_view, parent, false);
        return new ViewHolder(viewGalleryLumber);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryLumberViewAdapter.ViewHolder holder, int position) {
        PriceLumber priceLumber = listPricesLumbers.get(position);
        holder.cardLumberRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cardLumberRelativeLayout.setBackground(context.getDrawable(R.drawable.card_anim));
                TransitionDrawable transition = (TransitionDrawable) holder.cardLumberRelativeLayout.getBackground();
                transition.startTransition(2000);
                transition.reverseTransition(2000);
                UtilsActivity.goToActivityWithPutExtra(context, LumberCardActivity.class, priceLumber);
            }
        });
        displayAllParametersView(holder, priceLumber);
    }

    @Override
    public int getItemCount() {
        return listPricesLumbers.size();
    }

    private void displayAllParametersView(@NonNull GalleryLumberViewAdapter.ViewHolder holder, PriceLumber priceLumber) {
        setDiscount(holder, priceLumber);
        holder.textViewNameLumber.setText(priceLumber.getLumber().getNameLumber());
        if (priceLumber.getLumber().isAvailability()) {
            holder.imageViewIconAvailLumber.setImageDrawable(context.getDrawable(R.drawable.check_mark_icon));
            holder.textViewAvailLumber.setText(context.getString(R.string.avail_lumber));
            holder.textViewAvailLumber.setTextColor(context.getColor(R.color.green_theme));
        } else {
            holder.imageViewIconAvailLumber.setImageDrawable(context.getDrawable(R.drawable.close_icon));
            holder.textViewAvailLumber.setText(context.getString(R.string.not_avail_lumber));
            holder.textViewAvailLumber.setTextColor(context.getColor(R.color.red));
        }

        byte[] imageL = priceLumber.getLumber().getParametersLumber().getImageLumber();
        if (imageL != null) {
            Bitmap bitmapImageLumber = BitmapFactory.decodeByteArray(imageL, 0, imageL.length);
            holder.imageViewLumber.setImageBitmap(bitmapImageLumber);
        } else {
            holder.imageViewLumber.setImageDrawable(context.getDrawable(R.drawable.lumber_bez_foto));
            System.out.println("\n-----------Image Lumber is not available------------\n");
        }

        if (filter.getCategoryPrice() == null) {
            holder.textViewCategoryPriceLabel.setText(context.getString(R.string.m3));
        } else {
            holder.textViewCategoryPriceLabel.setText("рублей " + filter.getCategoryPrice());
        }

    }

    private void setDiscount(@NonNull GalleryLumberViewAdapter.ViewHolder holder, PriceLumber priceLumber) {
        String fullPrice = MathUtils.getIntegerValue(priceLumber.getPrice());
        String discountPrice = MathUtils.getIntegerValue(priceLumber.getDiscountPrice());
        String discountAmount = MathUtils.getIntegerValue(priceLumber.getCategoryPrice().getDiscountAmount());
        if (priceLumber.getCategoryPrice().isDiscount()) {
            holder.textViewAmountDiscount.setVisibility(View.VISIBLE);
            holder.textViewLabelDiscount.setVisibility(View.VISIBLE);
            holder.textViewDiscountPrice.setVisibility(View.VISIBLE);
            holder.textViewLabelDiscount.setText(context.getString(R.string.discount));
            holder.textViewAmountDiscount.setText(discountAmount + "%");
            holder.textViewDiscountPrice.setText(discountPrice);
            holder.textViewDiscountPrice.setTextColor(context.getColor(R.color.black));
            holder.textViewFullPrice.setText(fullPrice);
            holder.textViewFullPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.textViewFullPrice.setPaintFlags(holder.textViewFullPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textViewFullPrice.setTextColor(context.getColor(R.color.grey2));
        } else {
            holder.textViewAmountDiscount.setVisibility(View.GONE);
            holder.textViewLabelDiscount.setVisibility(View.GONE);
            holder.textViewDiscountPrice.setVisibility(View.GONE);
            holder.textViewFullPrice.setText(String.valueOf(fullPrice));
            holder.textViewFullPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.textViewFullPrice.setTextColor(context.getColor(R.color.black));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final RelativeLayout cardLumberRelativeLayout;
        final ImageView imageViewLumber, imageViewIconAvailLumber;
        final TextView textViewAmountDiscount, textViewLabelDiscount, textViewAvailLumber, textViewNameLumber,
                textViewSortLumber, textViewDiscountPrice, textViewFullPrice, textViewCategoryPriceLabel;

        ViewHolder(View view) {
            super(view);
            cardLumberRelativeLayout = view.findViewById(R.id.rellayout_card_in_gallery_lumbers);
            imageViewLumber = view.findViewById(R.id.imageview_image_lumber);
            textViewAmountDiscount = view.findViewById(R.id.textview_amount_discount);
            textViewLabelDiscount = view.findViewById(R.id.textview_discount_label);
            imageViewIconAvailLumber = view.findViewById(R.id.imageview_icon_avail_lumber);
            textViewAvailLumber = view.findViewById(R.id.textview_avail_lumber);
            textViewNameLumber = view.findViewById(R.id.textview_name_lumber);
            textViewSortLumber = view.findViewById(R.id.textview_sort_lumber);
            textViewDiscountPrice = view.findViewById(R.id.textview_price_discount);
            textViewFullPrice = view.findViewById(R.id.textview_full_price);
            textViewCategoryPriceLabel = view.findViewById(R.id.textview_category_price_label);
            textViewAmountDiscount.setVisibility(View.GONE);
            textViewLabelDiscount.setVisibility(View.GONE);
            textViewDiscountPrice.setVisibility(View.GONE);
        }
    }
}
