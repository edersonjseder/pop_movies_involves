package involves.test.com.popularmovies.listener;


import involves.test.com.popularmovies.model.Movie;

/**
 * Interface to return the Movie object from the list
 */

public interface OnMovieItemSelectedListener {

    // called when user selects a movie from the list
    void onMovieItemSelected(Movie movie, int position);

}
