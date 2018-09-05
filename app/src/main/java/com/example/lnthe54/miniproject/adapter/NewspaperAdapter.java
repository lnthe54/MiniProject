package com.example.lnthe54.miniproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lnthe54.miniproject.R;
import com.example.lnthe54.miniproject.model.Newspaper;

import java.util.List;

/**
 * @author lnthe54 on 9/4/2018
 * @project MiniProject
 */
public class NewspaperAdapter extends RecyclerView.Adapter<NewspaperAdapter.ViewHolder> {

    private List<Newspaper> listNews;

    public NewspaperAdapter(List<Newspaper> listNews) {
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
        Newspaper newspaper = listNews.get(position);
        holder.bindData(newspaper);
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvDesc, tvPubDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title_newspaper);
            tvDesc = itemView.findViewById(R.id.tv_desc_newspaper);
            tvPubDate = itemView.findViewById(R.id.tv_pubDate_newspaper);
        }

        public void bindData(Newspaper newspaper) {
            tvTitle.setText(newspaper.getTitle());
            tvDesc.setText(newspaper.getDesc());
            tvPubDate.setText(newspaper.getPubDate());
        }
    }
}
