package com.example.lnthe54.miniproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lnthe54.miniproject.R;
import com.example.lnthe54.miniproject.model.News;

import java.util.List;

/**
 * @author lnthe54 on 9/4/2018
 * @project MiniProject
 */
public class NewspaperAdapter extends RecyclerView.Adapter<NewspaperAdapter.ViewHolder> {

    private List<News> listNews;
    private onCallBack click;

    public NewspaperAdapter(onCallBack click, List<News> listNews) {
        this.click = click;
        this.listNews = listNews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newspaper, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = listNews.get(position);
        holder.bindData(news);
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public interface onCallBack {
        void itemClick(int position);

        void itemLongClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvDesc, tvPubDate;
        private ImageView ivNewsPaper;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            ivNewsPaper = itemView.findViewById(R.id.iv_newspaper);
            tvTitle = itemView.findViewById(R.id.tv_title_newspaper);
            tvDesc = itemView.findViewById(R.id.tv_desc_newspaper);
            tvPubDate = itemView.findViewById(R.id.tv_pubDate_newspaper);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.itemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    click.itemLongClick(view, getAdapterPosition());
                    return true;
                }
            });
        }

        public void bindData(News news) {
            tvTitle.setText(news.getTitle());
            tvDesc.setText(news.getDesc());
            tvPubDate.setText(news.getPubDate());

            Glide.with(itemView.getContext())
                    .load(news.getImage())
                    .apply(new RequestOptions().centerCrop())
                    .into(ivNewsPaper);
        }
    }
}
