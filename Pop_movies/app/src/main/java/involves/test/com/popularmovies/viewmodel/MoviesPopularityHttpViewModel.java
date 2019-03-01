package involves.test.com.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import involves.test.com.popularmovies.http.GenresHttpClient;
import involves.test.com.popularmovies.model.MoviesInfo;
import involves.test.com.popularmovies.services.MoviesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static involves.test.com.popularmovies.constants.UrlConstants.KEY;
import static involves.test.com.popularmovies.constants.UrlConstants.POPULAR;

public class MoviesPopularityHttpViewModel extends ViewModel {
    private static final String TAG = MoviesPopularityHttpViewModel.class.getSimpleName();

    // This data will be fetched asynchronously
    private MutableLiveData<MoviesInfo> moviesInfo;

    // Get movies info popularity with this method
    public LiveData<MoviesInfo> getMoviesInfoPopularity( Context context) {

        if (moviesInfo == null) {

            moviesInfo = new MutableLiveData<>();

            //we will load it asynchronously from server in this method
            loadMoviesInfoPopularity(context);

        }

        // Return the list
        return moviesInfo;
    }

    // This method uses retrofit to fetch Json data from Url with popularity parameter
    private void loadMoviesInfoPopularity( Context context) {

        MoviesService moviesService = GenresHttpClient.create(context);

        Call<MoviesInfo> call = moviesService.loadMostPopularMoviesInfo(KEY, POPULAR);

        call.enqueue(new Callback<MoviesInfo>() {
            @Override
            public void onResponse(Call<MoviesInfo> call, Response<MoviesInfo> response) {

                moviesInfo.setValue(response.body());

            }

            @Override
            public void onFailure(Call<MoviesInfo> call, Throwable t) {
                Log.e(TAG, "onFailure() inside method - e: " + t.getMessage());
            }
        });

    }
}
