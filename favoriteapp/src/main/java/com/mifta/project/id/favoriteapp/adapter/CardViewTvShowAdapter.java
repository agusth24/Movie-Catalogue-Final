package com.mifta.project.id.favoriteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mifta.project.id.favoriteapp.R;
import com.mifta.project.id.favoriteapp.model.MoviesItems;

import java.util.ArrayList;

public class CardViewTvShowAdapter extends RecyclerView.Adapter<CardViewTvShowAdapter.CardViewViewHolder> {

    private ArrayList<MoviesItems> listMovie = new ArrayList<>();
    private CardViewTvShowAdapter.OnItemClickCallback onItemClickCallback;
    private Context context;

    public CardViewTvShowAdapter(ArrayList<MoviesItems> list) {
        this.listMovie = list;
    }

    public CardViewTvShowAdapter() {
    }

    public CardViewTvShowAdapter(Context context) {
        this.context = context;
        listMovie = new ArrayList<>();
    }

    public void setData(ArrayList<MoviesItems> items) {
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }

    public ArrayList<MoviesItems> getMoviesItem() {
        return listMovie;
    }

    public void setOnItemClickCallback(CardViewTvShowAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }


    @NonNull
    @Override
    public CardViewTvShowAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_tv_show, parent, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewTvShowAdapter.CardViewViewHolder holder, int position) {
        MoviesItems movie = listMovie.get(position);
        Glide.with(holder.itemView.getContext())
                .load(movie.getPhoto())
                .apply(new RequestOptions().override(350, 550))
                .placeholder(R.drawable.ic_nothing)
                .error(R.drawable.ic_nothing)
                .into(holder.imgPhoto);
        holder.title.setText(movie.getTitle());
        holder.overview.setText(movie.getOverview());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listMovie.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(MoviesItems data);
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView overview;
        ImageView imgPhoto;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_item_title);
            overview = itemView.findViewById(R.id.tv_item_overview);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
        }
    }
}
