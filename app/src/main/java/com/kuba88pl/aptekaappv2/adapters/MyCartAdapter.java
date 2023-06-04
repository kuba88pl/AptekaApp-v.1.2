package com.kuba88pl.aptekaappv2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kuba88pl.aptekaappv2.R;
import com.kuba88pl.aptekaappv2.models.MyCartModel;

import org.w3c.dom.Text;

import java.util.List;

import io.grpc.Context;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> list;

    public MyCartAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.price.setText(list.get(position).getProductPrice() + " PLN");
        holder.name.setText(list.get(position).getProductName());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));
        holder.totalQuantity.setText(list.get(position).getTotalQuantity());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, date, time, totalQuantity, totalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           name = itemView.findViewById(R.id.product_name);
           price = itemView.findViewById(R.id.product_price);
           date = itemView.findViewById(R.id.current_date);
           time = itemView.findViewById(R.id.current_time);
           totalQuantity = itemView.findViewById(R.id.total_quantity);
           totalPrice = itemView.findViewById(R.id.total_price);
        }
    }
}
