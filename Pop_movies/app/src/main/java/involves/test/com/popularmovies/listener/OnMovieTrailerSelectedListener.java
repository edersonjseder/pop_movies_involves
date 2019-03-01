package involves.test.com.popularmovies.listener;

/**
 * Interface to return the Movie Trailer object from the list
 */
public interface OnMovieTrailerSelectedListener {

    // called when user selects a movie trailer from the list
    void onMovieTrailerSelected(String movieTrailerId, int position);

}
