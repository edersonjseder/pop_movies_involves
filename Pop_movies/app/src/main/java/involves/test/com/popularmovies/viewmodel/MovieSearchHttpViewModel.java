package involves.test.com.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import involves.test.com.popularmovies.constants.UrlConstants;
import involves.test.com.popularmovies.http.UpcomingSearchMoviesHttpClient;
import involves.test.com.popularmovies.model.MoviesInfo;
import involves.test.com.popularmovies.services.MoviesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieSearchHttpViewModel extends ViewModel {
    private static final String TAG = MovieSearchHttpViewModel.class.getSimpleName();

    // This data will be fetched asynchronously
    private MutableLiveData<MoviesInfo> moviesInfo;

    //we will call this method to get the data
    public LiveData<MoviesInfo> getMoviesInfoSearch(String searchText,  Context context) {

        if (moviesInfo == null) {

            moviesInfo = new MutableLiveData<>();

            //we will load it asynchronously from server in this method
            loadMoviesInfoSearch(searchText, context);

        }

        // Return the list
        return moviesInfo;
    }

    // This method uses retrofit to fetch Movie search Json data from Url
    private void loadMoviesInfoSearch(String searchText,  Context context) {

        MoviesService moviesService = UpcomingSearchMoviesHttpClient.create(context);

        Call<MoviesInfo> call = moviesService.loadSearchedMoviesInfo(UrlConstants.KEY, searchText,
                UrlConstants.COUNTRY, UrlConstants.page, false);

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
