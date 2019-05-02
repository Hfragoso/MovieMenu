package com.example.moviemenu.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviemenu.R;
import com.example.moviemenu.model.entity.MovieList;

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
    }

    @Override
    public int getItemCount() {
        return movieList.getData().size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        TextView genreTV;
        ImageView posterIV;


        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            genreTV = itemView.findViewById(R.id.tv_genre);
            posterIV = itemView.findViewById(R.id.iv_poster);
        }
    }
}
