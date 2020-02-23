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

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListViewHolder> {

    private ArrayList<MoviesItems> mData = new ArrayList<>();
    private OnItemClickCallBack onItemClickCallBack;
    private Context context;

    public ListMovieAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public ListMovieAdapter() {

    }

    public ListMovieAdapter(ArrayList<MoviesItems> list) {
        this.mData = list;
    }

    public void setOnItemClickCallBack(OnItemClickCallBack onItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack;
    }

    public void setData(ArrayList<MoviesItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public ArrayList<MoviesItems> getMoviesItem() {
        return mData;
    }

    @NonNull
    @Override
    public ListMovieAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movies, parent, false);
        return new ListViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListMovieAdapter.ListViewHolder holder, int position) {
        MoviesItems moviesItems = mData.get(position);
        Glide.with(holder.itemView.getContext())
                .load(moviesItems.getPhoto())
                .apply(new RequestOptions().override(350, 550))
                .placeholder(R.drawable.ic_nothing)
                .error(R.drawable.ic_nothing)
                .into(holder.imgPhoto);
        holder.title.setText(moviesItems.getTitle());
        holder.date.setText(moviesItems.getDate());
        holder.overview.setText(moviesItems.getOverview());
        holder.rating.setText(moviesItems.getRating());
        holder.country.setText(moviesItems.getCountry());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallBack.onItemClicked(mData.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickCallBack {
        void onItemClicked(MoviesItems data);
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView id;
        TextView overview;
        TextView country;
        TextView rating;
        ImageView imgPhoto;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            date = itemView.findViewById(R.id.tv_date);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            id = itemView.findViewById(R.id.tv_id);
            overview = itemView.findViewById(R.id.tv_overview);
            country = itemView.findViewById(R.id.tv_country);
            rating = itemView.findViewById(R.id.tv_rating);
        }

    }
}
