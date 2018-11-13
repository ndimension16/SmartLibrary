package com.ndimension.smartlibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.activity.BookActivity;
import com.ndimension.smartlibrary.module.BookModule;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ItemViewHolder> {
    private ArrayList<BookModule> orderList;
    Context context;




    public ShelfAdapter(Context context, ArrayList<BookModule> orderList) {
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

        try {
            Picasso.with(context)
                    .load(orderList.get(position).getImg())
                    .placeholder(R.drawable.nouser)
                    .into(holder.imgShelf);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
            Picasso.with(context).load(R.drawable.nouser).into(holder.imgShelf);
        }

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
