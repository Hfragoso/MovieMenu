package com.example.moviemenu.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.moviemenu.R;
import com.example.moviemenu.model.entity.MovieList;

import java.net.URI;

import butterknife.BindView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    MovieList movieList;

    public MoviesAdapter(MovieList movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_card_view, viewGroup, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int position) {
        String genre = movieList.getData().get(position).getGenre();
        String posterUrl = movieList.getData().get(position).getPoster();
        moviesViewHolder.genreTV.setText(genre);
        moviesViewHolder.glideRequestManager.load(posterUrl).into(moviesViewHolder.posterIV);
    }

    @Override
    public int getItemCount() {
        return movieList.getData().size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        TextView genreTV;
        ImageView posterIV;
        RequestManager glideRequestManager;


        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            genreTV = itemView.findViewById(R.id.tv_genre);
            posterIV = itemView.findViewById(R.id.iv_poster);
            glideRequestManager = Glide.with(itemView);
        }
    }
}
