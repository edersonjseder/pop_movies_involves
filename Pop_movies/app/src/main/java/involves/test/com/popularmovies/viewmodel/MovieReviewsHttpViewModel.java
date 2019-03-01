package involves.test.com.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import involves.test.com.popularmovies.http.UpcomingSearchMoviesHttpClient;
import involves.test.com.popularmovies.model.MovieListReviews;
import involves.test.com.popularmovies.services.MoviesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static involves.test.com.popularmovies.constants.UrlConstants.KEY;

public class MovieReviewsHttpViewModel extends ViewModel {
    private static final String TAG = MovieReviewsHttpViewModel.class.getSimpleName();

    // This data will be fetched asynchronously
    private MutableLiveData<MovieListReviews> movieReviews;

    //we will call this method to get the data
    public LiveData<MovieListReviews> getMovieReviews(int idVideos, Context context) {

        if (movieReviews == null) {

            movieReviews = new MutableLiveData<>();

            //we will load it asynchronously from server in this method
            loadMovieReviews(idVideos, context);

        }

        // Return the list
        return movieReviews;
    }

    // This method uses retrofit to fetch Json data from Url
    private void loadMovieReviews(int idVideos,  Context context) {

        MoviesService moviesService = UpcomingSearchMoviesHttpClient.create(context);

        Call<MovieListReviews> call = moviesService.loadReviews(idVideos, KEY);

        call.enqueue(new Callback<MovieListReviews>() {
            @Override
            public void onResponse(Call<MovieListReviews> call, Response<MovieListReviews> response) {

                movieReviews.setValue(response.body());

            }

            @Override
            public void onFailure(Call<MovieListReviews> call, Throwable t) {
                Log.e(TAG, "onFailure() inside method - e: " + t.getMessage());
            }
        });

    }

}
