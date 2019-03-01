package involves.test.com.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import involves.test.com.popularmovies.http.UpcomingSearchMoviesHttpClient;
import involves.test.com.popularmovies.model.MovieListTrailer;
import involves.test.com.popularmovies.services.MoviesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static involves.test.com.popularmovies.constants.UrlConstants.KEY;

public class MovieTrailersHttpViewModel extends ViewModel {
    private static final String TAG = MovieTrailersHttpViewModel.class.getSimpleName();

    // This data will be fetched asynchronously
    private MutableLiveData<MovieListTrailer> movieTrailers;

    //we will call this method to get the data
    public LiveData<MovieListTrailer> getMovieTrailers(int idVideos, Context context) {

        if (movieTrailers == null) {

            movieTrailers = new MutableLiveData<>();

            //we will load it asynchronously from server in this method
            loadMovieTrailers(idVideos, context);

        }

        // Return the list
        return movieTrailers;
    }

    // This method uses retrofit to fetch Json data from Url
    private void loadMovieTrailers(int idVideos, Context context) {

        MoviesService moviesService = UpcomingSearchMoviesHttpClient.create(context);

        Call<MovieListTrailer> call = moviesService.loadVideos(idVideos, KEY);

        call.enqueue(new Callback<MovieListTrailer>() {
            @Override
            public void onResponse(Call<MovieListTrailer> call, Response<MovieListTrailer> response) {

                movieTrailers.setValue(response.body());

            }

            @Override
            public void onFailure(Call<MovieListTrailer> call, Throwable t) {
                Log.e(TAG, "onFailure() inside method - e: " + t.getMessage());
            }
        });

    }

}
