package involves.test.com.popularmovies.main;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import involves.test.com.popularmovies.R;
import involves.test.com.popularmovies.adapter.MovieAdapter;
import involves.test.com.popularmovies.detail.MovieDetailActivity;
import involves.test.com.popularmovies.executors.AppExecutors;
import involves.test.com.popularmovies.flag.ConnectionFlag;
import involves.test.com.popularmovies.http.UpcomingSearchMoviesHttpClient;
import involves.test.com.popularmovies.listener.ConnectivityReceiverListener;
import involves.test.com.popularmovies.listener.OnMovieItemSelectedListener;
import involves.test.com.popularmovies.model.Genre;
import involves.test.com.popularmovies.model.GenreHolder;
import involves.test.com.popularmovies.model.Movie;
import involves.test.com.popularmovies.model.MoviesInfo;
import involves.test.com.popularmovies.receiver.NetworkChangeReceiver;
import involves.test.com.popularmovies.services.MoviesService;
import involves.test.com.popularmovies.utils.HolderList;
import involves.test.com.popularmovies.viewmodel.GenreHttpViewModel;
import involves.test.com.popularmovies.viewmodel.MovieSearchHttpViewModel;
import involves.test.com.popularmovies.viewmodel.MoviesPopularityHttpViewModel;
import involves.test.com.popularmovies.viewmodel.UpcomingMovieHttpViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static involves.test.com.popularmovies.constants.UrlConstants.COUNTRY;
import static involves.test.com.popularmovies.constants.UrlConstants.KEY;
import static involves.test.com.popularmovies.constants.UrlConstants.MOVIE;

public class MainMoviesActivity extends AppCompatActivity implements
        OnMovieItemSelectedListener, ConnectivityReceiverListener {
    private static final String TAG = MainMoviesActivity.class.getSimpleName();

    @BindView(R.id.recyclerview_movies)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh_layout_main)
    SwipeRefreshLayout swipeRefreshLayoutMain;

    @BindView(R.id.tv_error_message_display)
    TextView mErrorMessageDisplay;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;

    private SearchView searchView;

    private MovieAdapter movieAdapter;

    private LinearLayoutManager linearLayoutManager;

    private boolean isOnline;

    private ConnectionFlag connFlag;

    private NetworkChangeReceiver changeReceiver;

    private IntentFilter intentFilter;

    private List<Genre> mGenreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movies);
        Log.i(TAG, "onCreate() inside method");

        ButterKnife.bind(this);

        changeReceiver = new NetworkChangeReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        connFlag = new ConnectionFlag(this);

        linearLayoutManager = new LinearLayoutManager(this);

        if (isOnline) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        /*
         * The MovieAdapter is responsible for linking our movie data with the Views that
         * will end up displaying our movie data.
         */
        movieAdapter = new MovieAdapter(this);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        /**
         * Checks if there is a state saved
         */
        if (savedInstanceState == null) {

            getUpcomingMoviesList();
            getGenreList();

        } else {

            HolderList holder = (HolderList) savedInstanceState.getSerializable(MOVIE);

            List<Movie> listMovies = holder.getListMovie();

            mGenreList = holder.getListGenre();

            movieAdapter.setMovieList(listMovies);

            /* Setting the adapter attaches it to the RecyclerView in our layout. */
            mRecyclerView.setAdapter(movieAdapter);

            mLoadingIndicator.setVisibility(View.GONE);

        }

        // Component to reload content by dragging down the screen
        swipeRefreshLayoutMain.setColorSchemeResources(R.color.theme_accent, R.color.light_blue, R.color.blue);

        swipeRefreshLayoutMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                refreshContent();

                swipeRefreshLayoutMain.setRefreshing(false);

            }
        });

    }

    /**
     * Method to oad genres list
     */
    private void getGenreList() {

        // Different thread to load genres list together with the main movies list
        AppExecutors.getsInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {

                // This ModelView is used to fetch the list of genre Movies to be put on movie class
                GenreHttpViewModel upcomingMovieHttpViewModel =
                        ViewModelProviders.of(MainMoviesActivity.this).get(GenreHttpViewModel.class);

                upcomingMovieHttpViewModel.getGenreList(MainMoviesActivity.this)
                        .observe(MainMoviesActivity.this, new Observer<GenreHolder>() {
                            @Override
                            public void onChanged(@Nullable GenreHolder genreHolder) {

                                if (genreHolder != null) {

                                    mGenreList = genreHolder.getGenres();

                                }
                            }
                        });

            }
        });

    }

    private void getUpcomingMoviesList() {

        // This ModelView is used to fetch the list of Upcoming Movies to be shown on main screen
        UpcomingMovieHttpViewModel upcomingMovieHttpViewModel =
                ViewModelProviders.of(MainMoviesActivity.this).get(UpcomingMovieHttpViewModel.class);

        upcomingMovieHttpViewModel.getMoviesInfoUpcoming(this)
                .observe(MainMoviesActivity.this, new Observer<MoviesInfo>() {
                    @Override
                    public void onChanged(@Nullable MoviesInfo moviesInfo) {

                        if (moviesInfo != null) {

                            movieAdapter.setMovieList(moviesInfo.getMovieList());

                            mRecyclerView.setAdapter(movieAdapter);

                            mLoadingIndicator.setVisibility(View.GONE);

                        } else {

                            showErrorMessage();

                            mLoadingIndicator.setVisibility(View.GONE);

                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(changeReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(changeReceiver);
    }

    /**
     * Saves the state of the list when rotate the screen or leave the activity on background
     *
     * @param outState The bundle which will be saved the state of the list
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        HolderList holderList = new HolderList();

        holderList.setListMovie(movieAdapter.getMovieList());

        holderList.setListGenre(mGenreList);

        outState.putSerializable(MOVIE, holderList);

        super.onSaveInstanceState(outState);

    }

    /**
     * Method that refreshes the list on the screen after user pushes down the list
     */
    private void refreshContent(){

        MoviesService moviesService = UpcomingSearchMoviesHttpClient.create(this);

        Call<MoviesInfo> call = moviesService.loadUpcomingMoviesInfo(KEY, COUNTRY);

        call.enqueue(new Callback<MoviesInfo>() {
            @Override
            public void onResponse(Call<MoviesInfo> call, Response<MoviesInfo> response) {

                MoviesInfo moviesInfo = response.body();

                movieAdapter.setMovieList(moviesInfo.getMovieList());

                mRecyclerView.setAdapter(movieAdapter);

            }

            @Override
            public void onFailure(Call<MoviesInfo> call, Throwable t) {
                Log.e(TAG, "onFailure() inside method - e: " + t.getMessage());
                showErrorMessage();
            }
        });

    }

    /**
     * Method that gets the most popular movies selected from menu options
     */
    private void loadMostPopularMovie() {

        mLoadingIndicator.setVisibility(View.VISIBLE);

        MoviesPopularityHttpViewModel popularityHttpViewModel =
                ViewModelProviders.of(MainMoviesActivity.this).get(MoviesPopularityHttpViewModel.class);

        popularityHttpViewModel.getMoviesInfoPopularity(this)
                     .observe(this, new Observer<MoviesInfo>() {
            @Override
            public void onChanged(@Nullable MoviesInfo moviesInfo) {

                movieAdapter.setMovieList(moviesInfo.getMovieList());

                /* Setting the adapter attaches it to the RecyclerView in our layout. */
                mRecyclerView.setAdapter(movieAdapter);

                mLoadingIndicator.setVisibility(View.GONE);

            }
        });

    }


    /**
     * Method that select the movie from the list screen and enter to its details Activity
     *
     * @param movie The Movie object got from the list view
     * @param position the position of the object on the list
     */
    @Override
    public void onMovieItemSelected(Movie movie, int position) {

        Context context = this;
        Class destinationClass = MovieDetailActivity.class;

        View view = findViewById(R.id.image_movie_picture);

        movie.setGenres(mGenreList);

        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movie);

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this, view, getString(R.string.transition_image));

        startActivity(intentToStartDetailActivity, options.toBundle());

    }

    /**
     * Method that creates the menu on the screen
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.movies_menu, menu);

        searchView = (SearchView) menu.findItem(R.id.action_equipment_search).getActionView();

        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(onQueryTextListener);

        return true;

    }

    /**
     * Method to get the query entered by the user to search the desired movie
     */
    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {

            getMoviesBySearch(query);

            return true;

        }

        @Override
        public boolean onQueryTextChange(String newText) {

            return false;

        }

        /**
         * Method to get the result of the query movies entered by the user from the API
         *
         * @param searchText the movie entered by the user
         */
        private void getMoviesBySearch(String searchText) {

            // If there is no internet it won't be a search request
            if (isOnline) {

                mLoadingIndicator.setVisibility(View.VISIBLE);

                MovieSearchHttpViewModel searchHttpViewModel =
                        ViewModelProviders.of(MainMoviesActivity.this).get(MovieSearchHttpViewModel.class);

                searchHttpViewModel.getMoviesInfoSearch(searchText, MainMoviesActivity.this)
                        .observe(MainMoviesActivity.this, new Observer<MoviesInfo>() {
                            @Override
                            public void onChanged(@Nullable MoviesInfo moviesInfo) {

                                movieAdapter.setMovieList(moviesInfo.getMovieList());
                                mLoadingIndicator.setVisibility(View.GONE);

                            }
                        });

            } else {

                showNoInternetToast();

            }

        }
    };

    /**
     * Method to execute an action from the menu list
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_most_popular) {

            if (isOnline) {
                loadMostPopularMovie();
            } else {
                showNoInternetToast();
            }

            return true;

        }

        if (id == R.id.action_reload_movies_list) {

            if (isOnline) {
                refreshContent();
            } else {
                showNoInternetToast();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    // Shows a toast with 'No internet connection' message for the menu items and search
    private void showNoInternetToast() {
        Toast.makeText(this, getResources().getString(R.string.internet_connection_error_message), Toast.LENGTH_SHORT).show();
    }

    /**
     * This method will make the error message visible
     */
    private void showErrorMessage() {
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * Listener to fetch the status of the network from BroadcastReceiver
     *
     * @param isConnected The status flag of the network
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        isOnline = isConnected;
        int color = 0;
        String status;


         // Verify if it's connected, if it is not, it applies true in
         // shared preferences to no show the snackbar with connected message
         // when app is first launched, if it is, it remains false to pass
         // the last condition
        if (!isConnected) {

            connFlag.putValue(true);

        }


         // If is not connected, it shows a snackBar with the non connected message
        if (!isConnected) {

            color = Color.RED;
            status = getResources().getString(R.string.internet_connection_error_message);

            Snackbar snackbar = Snackbar.make(findViewById(R.id.main_activity_list), status, Snackbar.LENGTH_LONG);

            View view = snackbar.getView();
            TextView textView = view.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();

            if (movieAdapter.getMovieList().isEmpty()) {
                showErrorMessage();
            }

        }

        // Condition to show the connected snackBar only when connection is restored
        if (isConnected == connFlag.isOnline()) {

            connFlag.putValue(false);
            Snackbar snackbar =
                    Snackbar.make(findViewById(R.id.main_activity_list),
                            getResources().getString(R.string.internet_connection_back_message),
                            Snackbar.LENGTH_LONG);

            View view = snackbar.getView();
            TextView textView = view.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.GREEN);
            snackbar.show();

            mErrorMessageDisplay.setVisibility(View.GONE);

            refreshContent();

        }

    }

}
