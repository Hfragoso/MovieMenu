package com.example.moviemenu.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.example.moviemenu.R;
import com.example.moviemenu.di.AppComponent;
import com.example.moviemenu.di.AppModule;
import com.example.moviemenu.di.DaggerAppComponent;
import com.example.moviemenu.di.UtilsModule;
import com.example.moviemenu.model.utils.ApiResponse;
import com.example.moviemenu.model.utils.ViewModelFactory;
import com.example.moviemenu.viewModel.MovieDataViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.fetch_movies_btn)
    Button btnFetchMovies;

    MovieDataViewModel movieDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .utilsModule(new UtilsModule())
                .build();
        appComponent.doInjection(this);

        movieDataViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDataViewModel.class);
        movieDataViewModel.getMoviesResponse().observe(this, this::consumeResponse);
    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                Toast.makeText(MainActivity.this, "Loading", Toast.LENGTH_SHORT).show();
                break;

            case SUCCESS:
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                break;

            case ERROR:
                Toast.makeText(MainActivity.this, "Error: " + apiResponse.error, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @OnClick(R.id.fetch_movies_btn)
    void onFetchButtonClicked() {
        movieDataViewModel.getMovies();
    }
}
