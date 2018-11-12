package com.ndimension.smartlibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.activity.BookActivity;
import com.ndimension.smartlibrary.module.ShelfModule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ItemViewHolder> {
    private ArrayList<ShelfModule> orderList;
    Context context;




    public ShelfAdapter(Context context, ArrayList<ShelfModule> orderList) {
        this.context = context;
        this.orderList=orderList;


    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shelf_row, parent, false);
        return new ShelfAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        holder.imgShelf.setImageResource(orderList.get(position).getImage());

        holder.imgShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, BookActivity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

      ImageView imgShelf ;

        public ItemViewHolder(View itemView) {
            super(itemView);


            imgShelf = (ImageView) itemView.findViewById(R.id.imgShelf);




        }
    }


}
