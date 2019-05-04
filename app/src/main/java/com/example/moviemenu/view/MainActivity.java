package com.example.moviemenu.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviemenu.R;
import com.example.moviemenu.di.AppComponent;
import com.example.moviemenu.di.AppModule;
import com.example.moviemenu.di.DaggerAppComponent;
import com.example.moviemenu.di.RoomModule;
import com.example.moviemenu.di.UtilsModule;
import com.example.moviemenu.model.entity.MovieList;
import com.example.moviemenu.model.utils.ApiResponse;
import com.example.moviemenu.model.utils.ViewModelFactory;
import com.example.moviemenu.viewModel.MovieDataViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.movies_recycler_view)
    RecyclerView moviesRecyclerView;

    @BindView(R.id.search_et)
    EditText searchEditText;

    MovieDataViewModel movieDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .roomModule(new RoomModule(this))
                .utilsModule(new UtilsModule())
                .build();
        appComponent.doInjection(this);

        movieDataViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDataViewModel.class);
        movieDataViewModel.getMoviesResponse().observe(this, this::consumeResponse);
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        movieDataViewModel.getMovies(sharedPreferences);

        setUpSearchView();
    }


    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                Toast.makeText(MainActivity.this, "Loading", Toast.LENGTH_SHORT).show();
                break;

            case SUCCESS:
                displayMovies(apiResponse.data);
                break;

            case ERROR:
                Toast.makeText(MainActivity.this, "Error: " + apiResponse.error, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private void displayMovies(MovieList data) {
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        moviesRecyclerView.setAdapter(new MoviesAdapter(data));
    }

    private void setUpSearchView() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                ((MoviesAdapter) moviesRecyclerView.getAdapter()).getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
