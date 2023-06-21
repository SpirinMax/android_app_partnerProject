package com.example.partnerproject.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.partnerproject.R;
import com.example.partnerproject.objects.OrderLumber;
import com.example.partnerproject.utils.MathUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrdersCustomerAdapter extends RecyclerView.Adapter<OrdersCustomerAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final Context context;
    private final List<OrderLumber> listOrders;

    public OrdersCustomerAdapter(Context context, List<OrderLumber> listOrders) {
        this.inflater = LayoutInflater.from(context);
        ;
        this.context = context;
        this.listOrders = listOrders;
    }

    @NonNull
    @Override
    public OrdersCustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewOrderCustomer = inflater.inflate(R.layout.orders_customer_view_for_adapter, parent, false);
        return new ViewHolder(viewOrderCustomer);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersCustomerAdapter.ViewHolder holder, int position) {
        OrderLumber order = listOrders.get(position);
        byte[] imageL = order.getPriceLumber().getLumber().getParametersLumber().getImageLumber();
        if (imageL != null) {
            Bitmap bitmapImageLumber = BitmapFactory.decodeByteArray(imageL, 0, imageL.length);
            holder.imageViewLumber.setImageBitmap(bitmapImageLumber);
        } else {
            holder.imageViewLumber.setImageDrawable(context.getDrawable(R.drawable.lumber_bez_foto));
        }
        holder.nameLumberTextView.setText(order.getPriceLumber().getLumber().getNameLumber());

        Date date = order.getDateOrder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String dayStr = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String monthStr = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("ru", "RU"));
        String yearStr = String.valueOf(calendar.get(Calendar.YEAR));
        String dateStr = dayStr + " " + monthStr + " " + yearStr + " года";

        holder.dateOrderTextview.setText("Дата заказа: " + dateStr);
        String countStr = MathUtils.getIntegerValue((float) order.getQuantity());
        String categoryStr = order.getPriceLumber().getCategoryPrice().getNameCategoryPrice().split(" ")[1];
        holder.countOrdersTextView.setText("Вы заказали: " + countStr + " " + categoryStr);
        String finalPriceStr = MathUtils.getIntegerValue((float) order.getFinalPrice());
        String priceForOneStr = MathUtils.getIntegerValue(order.getPriceLumber().getPrice());
        String discountStr = "без скидки";
        if (order.getPriceLumber().getCategoryPrice().isDiscount()) {
            priceForOneStr = MathUtils.getIntegerValue(order.getPriceLumber().getDiscountPrice());
            discountStr = "со скидкой " + MathUtils.getIntegerValue(order.getPriceLumber().getCategoryPrice().getDiscountAmount()) + "%";
        }
        String priceStr = "За " + finalPriceStr + " руб." + " \n(" + priceForOneStr + " руб. " + discountStr + " за 1 " + categoryStr+")";
        holder.finalPriceTextView.setText(priceStr);
    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageViewLumber;
        TextView nameLumberTextView, dateOrderTextview, countOrdersTextView, finalPriceTextView;

        ViewHolder(View view) {
            super(view);
            imageViewLumber = view.findViewById(R.id.imageview_image_lumber);
            nameLumberTextView = view.findViewById(R.id.textview_name_lumber);
            dateOrderTextview = view.findViewById(R.id.textview_date_order);
            countOrdersTextView = view.findViewById(R.id.textview_count_orders);
            finalPriceTextView = view.findViewById(R.id.textview_final_price);
        }
    }
}
