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
import com.squareup.picasso.Picasso;

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
        String year = movieList.getData().get(position).getYear();
        String title = movieList.getData().get(position).getTitle();
        String posterUrl = movieList.getData().get(position).getPoster();

        moviesViewHolder.genreTV.setText(genre);
        moviesViewHolder.yearTV.setText(year);
        moviesViewHolder.titleTV.setText(title);

        Picasso.get().load(posterUrl)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .resize(300, 500)
                .into(moviesViewHolder.posterIV);
    }

    @Override
    public int getItemCount() {
        return movieList.getData().size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        TextView genreTV;
        TextView yearTV;
        TextView titleTV;
        ImageView posterIV;


        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            genreTV = itemView.findViewById(R.id.tv_genre);
            yearTV = itemView.findViewById(R.id.tv_year);
            titleTV = itemView.findViewById(R.id.tv_title);
            posterIV = itemView.findViewById(R.id.iv_poster);
        }
    }
}
