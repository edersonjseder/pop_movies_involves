package involves.test.com.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import involves.test.com.popularmovies.http.GenresHttpClient;
import involves.test.com.popularmovies.model.GenreHolder;
import involves.test.com.popularmovies.services.MoviesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static involves.test.com.popularmovies.constants.UrlConstants.KEY;

public class GenreHttpViewModel extends ViewModel {
    private static final String TAG = GenreHttpViewModel.class.getSimpleName();

    // This data will be fetched asynchronously
    private MutableLiveData<GenreHolder> genreHolder;

    //we will call this method to get the data
    public LiveData<GenreHolder> getGenreList(Context context) {

        if (genreHolder == null) {

            genreHolder = new MutableLiveData<>();

            //we will load it asynchronously from server in this method
            loadGenreList(context);

        }

        // Return the list
        return genreHolder;
    }

    // This method uses retrofit to fetch Json data from Url
    private void loadGenreList(Context context) {

        MoviesService moviesService = GenresHttpClient.create(context);

        Call<GenreHolder> call = moviesService.loadMovieGenres(KEY);

        call.enqueue(new Callback<GenreHolder>() {
            @Override
            public void onResponse(Call<GenreHolder> call, Response<GenreHolder> response) {

                genreHolder.setValue(response.body());

            }

            @Override
            public void onFailure(Call<GenreHolder> call, Throwable t) {
                Log.e(TAG, "onFailure() inside method - e: " + t.getMessage());
            }
        });

    }

}
