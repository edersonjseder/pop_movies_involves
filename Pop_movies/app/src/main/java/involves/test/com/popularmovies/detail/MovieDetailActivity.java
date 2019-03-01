package involves.test.com.popularmovies.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import involves.test.com.popularmovies.R;
import involves.test.com.popularmovies.adapter.MovieReviewsAdapter;
import involves.test.com.popularmovies.adapter.MovieTrailerAdapter;
import involves.test.com.popularmovies.http.UpcomingSearchMoviesHttpClient;
import involves.test.com.popularmovies.listener.ConnectivityReceiverListener;
import involves.test.com.popularmovies.listener.OnMovieTrailerSelectedListener;
import involves.test.com.popularmovies.model.Movie;
import involves.test.com.popularmovies.model.MovieListReviews;
import involves.test.com.popularmovies.model.MovieListTrailer;
import involves.test.com.popularmovies.receiver.NetworkChangeReceiver;
import involves.test.com.popularmovies.services.MoviesService;
import involves.test.com.popularmovies.utils.GlideApp;
import involves.test.com.popularmovies.utils.ImagePathUtils;
import involves.test.com.popularmovies.utils.Utils;
import involves.test.com.popularmovies.viewmodel.MovieReviewsHttpViewModel;
import involves.test.com.popularmovies.viewmodel.MovieTrailersHttpViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static involves.test.com.popularmovies.constants.UrlConstants.KEY;
import static involves.test.com.popularmovies.constants.UrlConstants.YOUTUBE_INTERNET_URL;
import static involves.test.com.popularmovies.constants.UrlConstants.YOUTUBE_URL;

public class MovieDetailActivity extends AppCompatActivity implements
        OnMovieTrailerSelectedListener, ConnectivityReceiverListener {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    @BindView(R.id.textview_movie_original_title_detail)
    TextView textViewMovieOriginalTitleDetail;

    @BindView(R.id.textview_movie_release_date_detail)
    TextView textViewMovieReleaseDateDetail;

    @BindView(R.id.textview_movie_overview_detail)
    TextView textViewMovieOverviewDetail;

    @BindView(R.id.tv_no_reviews_message_display)
    TextView textViewNoReviewsMessage;

    @BindView(R.id.tv_no_trailers_message_display)
    TextView textViewNoTrailersMessage;

    @BindView(R.id.textview_movie_genre_detail)
    TextView textViewMovieGenreDetail;

    @BindView(R.id.image_movie_picture_detail)
    ImageView imageMoviePictureDetail;

    @BindView(R.id.pb_loading_trailer_indicator)
    ProgressBar mLoadingIndicator;

    @BindView(R.id.recyclerview_movie_trailers)
    RecyclerView mRecyclerViewTrailers;

    @BindView(R.id.recyclerview_movie_reviews)
    RecyclerView mRecyclerReviewsView;

    @BindView(R.id.scrollViewParent)
    ScrollView mScrollViewParent;

    @BindView(R.id.scrollViewChild)
    ScrollView mScrollViewChild;

    private Movie movie;
    private int idVideo;

    private MovieTrailerAdapter movieTrailerAdapter;
    private MovieReviewsAdapter movieReviewsAdapter;

    private NetworkChangeReceiver changeReceiver;

    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Log.i(TAG, "onCreate() inside method");

        ButterKnife.bind(this);

        changeReceiver = new NetworkChangeReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        initViews();

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {

                movie = (Movie) intentThatStartedThisActivity.getSerializableExtra(Intent.EXTRA_TEXT);

                idVideo = movie.getId();

                setDetailsFields(movie);

            }
        }

        mLoadingIndicator.setVisibility(View.VISIBLE);

        /**
         * This ModelView is used to fetch the trailers of the selected movie
         */
        MovieTrailersHttpViewModel trailersHttpViewModel =
                ViewModelProviders.of(this).get(MovieTrailersHttpViewModel.class);

        trailersHttpViewModel.getMovieTrailers(idVideo, this)
                             .observe(this, new Observer<MovieListTrailer>() {
                                 @Override
                                 public void onChanged(@Nullable MovieListTrailer movieListTrailer) {

                                     if (movieListTrailer != null) {

                                         movieTrailerAdapter.setMovieTrailerInfList(movieListTrailer.getMovieTrailerInfList());

                                         mRecyclerViewTrailers.setAdapter(movieTrailerAdapter);

                                     } else {

                                         showNoTrailersMessage();

                                     }

                                     mLoadingIndicator.setVisibility(View.GONE);

                                 }
                             });

        /**
         * This ModelView is used to fetch the reviews of the selected movie
         */
        MovieReviewsHttpViewModel reviewsHttpViewModel =
                ViewModelProviders.of(this).get(MovieReviewsHttpViewModel.class);

        reviewsHttpViewModel.getMovieReviews(idVideo, this)
                            .observe(this, new Observer<MovieListReviews>() {
                                @Override
                                public void onChanged(@Nullable MovieListReviews movieListReviews) {

                                    if (movieListReviews != null) {

                                        if (movieListReviews.getMovieReviewInf().size() > 0) {

                                            movieReviewsAdapter.setMovieReviewsInfList(movieListReviews.getMovieReviewInf());

                                            mRecyclerReviewsView.setAdapter(movieReviewsAdapter);

                                        } else {
                                            showNoReviewsMessage();
                                        }

                                    } else {

                                        showNoReviewsMessage();

                                    }

                                    mLoadingIndicator.setVisibility(View.GONE);

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
     * Sets the recycler views layouts
     */
    private void initViews() {

        movieTrailerAdapter = new MovieTrailerAdapter(this);
        mRecyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewTrailers.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewTrailers.setHasFixedSize(true);

        movieReviewsAdapter = new MovieReviewsAdapter(this);
        mRecyclerReviewsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerReviewsView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerReviewsView.setHasFixedSize(true);

        /**
         * this snippet allows the parent scroll view to work properly
         */
        mScrollViewParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                findViewById(R.id.scrollViewChild).getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });


        /**
         * this snippet allows the child scroll view to work properly
         */
        mScrollViewChild.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });

    }

    /**
     * Method sets the movie details data on the UI components
     *
     * @param movie the Movie object got from the previous activity
     */
    private void setDetailsFields(Movie movie) {

        textViewMovieGenreDetail.setText(Utils.selectAndCompareGenresById(movie));
        textViewMovieOriginalTitleDetail.setText(movie.getOriginalTitle());
        textViewMovieReleaseDateDetail.setText(Utils.changeDateOrder(movie.getReleaseDate()));
        textViewMovieOverviewDetail.setText(movie.getOverview());

        String imageUrl = ImagePathUtils.buildImageUrl(movie.getPosterPath());

        GlideApp.with(getApplicationContext())
                .load(imageUrl)
                .fitCenter()
                .into(imageMoviePictureDetail);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Method that makes toolbar back arrow not to reload activity again when pressed
     *
     * @param item The toolbar back arrow item
     * @return The boolean value on item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Method that shows an empty list message if there are no reviews to show
     */
    private void showNoReviewsMessage() {

        textViewNoReviewsMessage.setVisibility(View.VISIBLE);

    }

    /**
     * Method that shows an empty list message if there are no trailers to show
     */
    private void showNoTrailersMessage() {

        textViewNoTrailersMessage.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.GONE);

    }

    /**
     * Method that loads the trailers list on the screen after reconnect the network
     */
    private void loadTrailers() {

        MoviesService moviesService = UpcomingSearchMoviesHttpClient.create(this);

        Call<MovieListTrailer> call = moviesService.loadVideos(idVideo, KEY);

        call.enqueue(new Callback<MovieListTrailer>() {
            @Override
            public void onResponse(Call<MovieListTrailer> call, Response<MovieListTrailer> response) {

                MovieListTrailer movieListTrailer = response.body();

                movieTrailerAdapter.setMovieTrailerInfList(movieListTrailer.getMovieTrailerInfList());

                mRecyclerViewTrailers.setAdapter(movieTrailerAdapter);

            }

            @Override
            public void onFailure(Call<MovieListTrailer> call, Throwable t) {
                Log.e(TAG, "onFailure() inside method - e: " + t.getMessage());
                showNoTrailersMessage();
            }
        });

    }

    /**
     * Method that loads the Reviews list on the screen after reconnect the network
     */
    private void loadReviews() {

        MoviesService moviesService = UpcomingSearchMoviesHttpClient.create(this);

        Call<MovieListReviews> call = moviesService.loadReviews(idVideo, KEY);

        call.enqueue(new Callback<MovieListReviews>() {
            @Override
            public void onResponse(Call<MovieListReviews> call, Response<MovieListReviews> response) {

                MovieListReviews movieListReviews = response.body();

                movieReviewsAdapter.setMovieReviewsInfList(movieListReviews.getMovieReviewInf());

                mRecyclerReviewsView.setAdapter(movieReviewsAdapter);

            }

            @Override
            public void onFailure(Call<MovieListReviews> call, Throwable t) {
                Log.e(TAG, "onFailure() inside method - e: " + t.getMessage());
                showNoTrailersMessage();
            }
        });

    }

    @Override
    public void onMovieTrailerSelected(String movieTrailerId, int position) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_URL + movieTrailerId));

        // Check if the youtube app exists on the device
        if (intent.resolveActivity(getPackageManager()) == null) {
            // If the youtube app doesn't exist, then use the browser
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(YOUTUBE_INTERNET_URL + movieTrailerId));
        }

        startActivity(intent);

    }

    /**
     * Listener to fetch the status of the network from BroadcastReceiver
     *
     * @param isConnected The status flag of the network
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if (!isConnected) {

            if (movieTrailerAdapter.getMovieTrailerInfList() == null &&
                    movieReviewsAdapter.getMovieReviewsInfList() == null) {

                showNoTrailersMessage();
                showNoReviewsMessage();

            }

        } else {

            textViewNoTrailersMessage.setVisibility(View.GONE);
            textViewNoReviewsMessage.setVisibility(View.GONE);

            loadTrailers();
            loadReviews();

        }

    }

}
