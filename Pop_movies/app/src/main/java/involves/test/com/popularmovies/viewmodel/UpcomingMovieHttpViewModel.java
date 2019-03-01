package involves.test.com.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import involves.test.com.popularmovies.http.UpcomingSearchMoviesHttpClient;
import involves.test.com.popularmovies.model.MoviesInfo;
import involves.test.com.popularmovies.services.MoviesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static involves.test.com.popularmovies.constants.UrlConstants.COUNTRY;
import static involves.test.com.popularmovies.constants.UrlConstants.KEY;

public class UpcomingMovieHttpViewModel extends ViewModel {
    private static final String TAG = UpcomingMovieHttpViewModel.class.getSimpleName();

    // This data will be fetched asynchronously
    private MutableLiveData<MoviesInfo> moviesInfo;

    //we will call this method to get the data
    public LiveData<MoviesInfo> getMoviesInfoUpcoming(Context context) {

        if (moviesInfo == null) {

            moviesInfo = new MutableLiveData<>();

            //we will load it asynchronously from server in this method
            loadMoviesInfoUpcoming(context);

        }

        // Return the list
        return moviesInfo;
    }

    // This method uses retrofit to fetch Upcoming movies Json data from Url
    private void loadMoviesInfoUpcoming(Context context) {

        MoviesService moviesService = UpcomingSearchMoviesHttpClient.create(context);

        Call<MoviesInfo> call = moviesService.loadUpcomingMoviesInfo(KEY, COUNTRY);

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
