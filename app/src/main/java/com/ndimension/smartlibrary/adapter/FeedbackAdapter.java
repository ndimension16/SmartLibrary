package com.ndimension.smartlibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.activity.BookActivity;
import com.ndimension.smartlibrary.module.BookModule;
import com.ndimension.smartlibrary.module.FeedbackModule;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ItemViewHolder> {
    private ArrayList<FeedbackModule> orderList;
    Context context;




    public FeedbackAdapter(Context context, ArrayList<FeedbackModule> orderList) {
        this.context = context;
        this.orderList=orderList;


    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_row, parent, false);
        return new FeedbackAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        try {
            Picasso.with(context)
                    .load(orderList.get(position).getBook_img())
                    .placeholder(R.drawable.no_img)
                    .into(holder.imgBook);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
            Picasso.with(context).load(R.drawable.no_img).into(holder.imgBook);
        }

        holder.tvAuthor.setText("Author : "+orderList.get(position).getBook_author());
        holder.tvBookTitle.setText(orderList.get(position).getBook_title());
        holder.tvDate.setText(orderList.get(position).getFeedback_created_date());
        holder.tvFeedbackContent.setText(orderList.get(position).getFeedback_content());




    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

      ImageView imgBook;
      TextView tvBookTitle,tvAuthor,tvDate,tvFeedbackContent;

        public ItemViewHolder(View itemView) {
            super(itemView);


            imgBook = (ImageView) itemView.findViewById(R.id.imgBook);
            tvBookTitle = (TextView)itemView.findViewById(R.id.tvBookTitle);
            tvAuthor = (TextView)itemView.findViewById(R.id.tvAuthor);
            tvDate = (TextView)itemView.findViewById(R.id.tvDate);
            tvFeedbackContent = (TextView)itemView.findViewById(R.id.tvFeedbackContent);




        }
    }


}
