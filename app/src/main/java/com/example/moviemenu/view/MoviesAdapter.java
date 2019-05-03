package com.example.moviemenu.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviemenu.R;
import com.example.moviemenu.model.entity.Movie;
import com.example.moviemenu.model.entity.MovieList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> implements Filterable {

    MovieList movieList;
    MovieList filteredMovies = new MovieList();

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

        Movie movie;
        if (filteredMovies.getData() == null) {
            movie = movieList.getData().get(position);
        } else {
            movie = filteredMovies.getData().get(position);
        }

        String genre = movie.getGenre();
        String year = movie.getYear();
        String title = movie.getTitle();
        String posterUrl = movie.getPoster();

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
        if (filteredMovies.getData() == null) {
            return movieList.getData().size();
        } else {
            return filteredMovies.getData().size();
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                String charSearched = charSequence.toString();
                List<Movie> filteredList = new ArrayList<>();

                if (charSearched.isEmpty()) {
                    filteredList = movieList.getData();
                } else {


                    for (Movie item : movieList.getData()) {
                        String toBeSearchedString = new StringBuilder().append(item.getGenre()).append(item.getTitle()).toString();
                        if (toBeSearchedString.toLowerCase().contains(charSearched.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                }

                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                List<Movie> filteredList = (List<Movie>) filterResults.values;
                filteredMovies.setData(filteredList);
                notifyDataSetChanged();
            }
        };
    }


    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_genre)
        TextView genreTV;

        @BindView(R.id.tv_year)
        TextView yearTV;

        @BindView(R.id.tv_title)
        TextView titleTV;

        @BindView(R.id.iv_poster)
        ImageView posterIV;


        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
