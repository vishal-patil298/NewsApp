package com.example.newsappjava;

import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private NewsItemClicked listener;
    private ArrayList<News> items=new ArrayList<News>();
    public NewsListAdapter(MainActivity mainActivity)
    {
        this.listener=mainActivity;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        NewsViewHolder viewHolder= new NewsViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(items.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        News currentItem= items.get(position);
//        Log.d("title",currentItem.title);
        holder.titleView.setText(currentItem.title);
        holder.author.setText(currentItem.author);
        Glide.with(holder.itemView.getContext()).load(currentItem.imageURL).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateNews(ArrayList<News> updatedNews)
    {
        items.clear();
        items.addAll(updatedNews);
        notifyDataSetChanged();
    }
}
class NewsViewHolder extends RecyclerView.ViewHolder
{
    public NewsViewHolder(View itemView)
    {
        super(itemView);
    }
    protected TextView titleView=(TextView) itemView.findViewById(R.id.title);
    protected ImageView imageView=(ImageView) itemView.findViewById(R.id.image);
    protected TextView author=(TextView) itemView.findViewById(R.id.author);
}
interface NewsItemClicked
{
    void onItemClicked(News item);
}