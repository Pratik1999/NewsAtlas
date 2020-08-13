package com.example.sunny.maps1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sunny.maps1.R;
import com.example.sunny.maps1.models.ArticlePojo;
import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder> {

    ArrayList<ArticlePojo> articles;
    Context context;
    private CustomOntemClickListener customOntemClickListener;
    public NewsListAdapter(ArrayList<ArticlePojo> articles,Context context,CustomOntemClickListener customOntemClickListener) {
        this.articles=articles;
        this.context=context;
        this.customOntemClickListener=customOntemClickListener;
    }

    @NonNull
    @Override
    public NewsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.news_list_item,parent,false);
        return new NewsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListViewHolder holder, int position) {
        ArticlePojo a=articles.get(position);
        holder.titleView.setText(a.getTitle());
        holder.sourceView.setText(a.getSource().getName());
        holder.dateView.setText(a.getPublishedAt());
        Glide.with(context).load(a.getUrlToImage())
                .error(R.drawable.common_full_open_on_phone)
                .into(holder.imageView);

        ViewCompat.setTransitionName(holder.cardView,String.valueOf(a.getPublishedAt()+"container"));
        ViewCompat.setTransitionName(holder.imageView,String.valueOf(a.getPublishedAt()+"image"));
    }

    @Override
    public int getItemCount() {
        return (articles==null)?0:articles.size();
    }

    public class NewsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titleView,dateView,sourceView;
        ImageView imageView;
        CardView cardView;
        public NewsListViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView=itemView.findViewById(R.id.newsTitle);
            dateView=itemView.findViewById(R.id.publishDate);
            sourceView=itemView.findViewById(R.id.sourceName);
            imageView=itemView.findViewById(R.id.newsItemImage);
            cardView=itemView.findViewById(R.id.newsItemCardView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            customOntemClickListener.onItemClicked(getAdapterPosition(),imageView,cardView);
        }
    }
    public interface CustomOntemClickListener{
        void onItemClicked(int pos, ImageView imageView, View containerView);
    }

}
