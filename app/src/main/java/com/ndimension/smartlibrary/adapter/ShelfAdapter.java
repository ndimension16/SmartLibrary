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
                    .placeholder(R.drawable.no_img)
                    .into(holder.imgShelf);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
            Picasso.with(context).load(R.drawable.no_img).into(holder.imgShelf);
        }

        holder.imgShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,BookActivity.class);
                intent.putExtra("flag","normal");
                intent.putExtra("book_id",orderList.get(position).getId());
                intent.putExtra("book_title",orderList.get(position).getName());
                intent.putExtra("book_content",orderList.get(position).getBook_content());
                intent.putExtra("book_author",orderList.get(position).getBook_author());
                intent.putExtra("book_publish_date",orderList.get(position).getBook_publish_date());
                intent.putExtra("book_pdf_link",orderList.get(position).getBook_pdf_link());
                intent.putExtra("book_qr_code",orderList.get(position).getBook_qr_code());
                intent.putExtra("book_img",orderList.get(position).getImg());
                intent.putExtra("category",orderList.get(position).getKey());
                intent.putExtra("book_barcode_img",orderList.get(position).getBarcode_img());
                context.startActivity(intent);
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
