package com.example.moviemenu.viewModel;

import com.example.moviemenu.model.Repository;
import com.example.moviemenu.model.entity.MovieList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MovieDataViewModelTest {

    @Mock
    Repository repositoryMock;


    @Mock
    MovieList movieList;

    MovieDataViewModel movieDataViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        movieDataViewModel = new MovieDataViewModel(repositoryMock);
    }

    @Test
    public void insertDB_fetchesFromRestApi_returnsTrue() {
        repositoryMock.refreshDB = true;
        boolean inserted = movieDataViewModel.insertDB(movieList);
        assertTrue(inserted);
    }

    @Test
    public void insertDB_fetchesFromCache_returnsFalse() {
        repositoryMock.refreshDB = false;
        boolean inserted = movieDataViewModel.insertDB(movieList);
        assertFalse(inserted);
    }
}