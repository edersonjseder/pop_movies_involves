package involves.test.com.popularmovies.services;

import involves.test.com.popularmovies.model.GenreHolder;
import involves.test.com.popularmovies.model.MovieListReviews;
import involves.test.com.popularmovies.model.MovieListTrailer;
import involves.test.com.popularmovies.model.MoviesInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static involves.test.com.popularmovies.constants.UrlConstants.ADULT;
import static involves.test.com.popularmovies.constants.UrlConstants.API_KEY;
import static involves.test.com.popularmovies.constants.UrlConstants.GENRE;
import static involves.test.com.popularmovies.constants.UrlConstants.LANGUAGE;
import static involves.test.com.popularmovies.constants.UrlConstants.LIST;
import static involves.test.com.popularmovies.constants.UrlConstants.MOVIE;
import static involves.test.com.popularmovies.constants.UrlConstants.PAGE;
import static involves.test.com.popularmovies.constants.UrlConstants.POPULAR;
import static involves.test.com.popularmovies.constants.UrlConstants.QUERY;
import static involves.test.com.popularmovies.constants.UrlConstants.REVIEWS;
import static involves.test.com.popularmovies.constants.UrlConstants.SEARCH;
import static involves.test.com.popularmovies.constants.UrlConstants.SLASH;
import static involves.test.com.popularmovies.constants.UrlConstants.UPCOMING;
import static involves.test.com.popularmovies.constants.UrlConstants.VIDEOS;


/**
 * Interface with the end point methods to be used to retrieve data from API
 */
public interface MoviesService {

    /**
     * Loads the upcoming movies on the list
     *
     * @param apiKey the key from API
     * @param language the Country language of movie
     * @return the list of upcoming movies
     */
    @GET(MOVIE + SLASH + UPCOMING)
    Call<MoviesInfo> loadUpcomingMoviesInfo(@Query(API_KEY) String apiKey, @Query(LANGUAGE) String language);

    /**
     * Loads the list of movies by search query
     *
     * @param apiKey the key from API
     * @param querySearch the query parameter the user inserts
     * @param language the Country language of movie
     * @param page the number of pages
     * @param incAdult the adult verification
     * @return the list of searched movies
     */
    @GET(SEARCH + SLASH + MOVIE)
    Call<MoviesInfo> loadSearchedMoviesInfo(@Query(API_KEY) String apiKey,
                                            @Query(QUERY) String querySearch,
                                            @Query(LANGUAGE) String language,
                                            @Query(PAGE) int page,
                                            @Query(ADULT) boolean incAdult);

    /**
     * Loads the video trailers on movie detail
     *
     * @param id the video ID of the movie
     * @param apiKey the key from API
     * @return the movie trailers
     */
    @GET(MOVIE + "/{id}/" + VIDEOS)
    Call<MovieListTrailer> loadVideos(@Path("id") int id, @Query(API_KEY) String apiKey);

    /**
     * Loads the users reviews of the movie on detail
     *
     * @param id the video ID of the movie
     * @param apiKey the key from API
     * @return the review list of the movies
     */
    @GET(MOVIE + "/{id}/" + REVIEWS)
    Call<MovieListReviews> loadReviews(@Path("id") int id, @Query(API_KEY) String apiKey);

    /**
     * Loads the list of genres
     *
     * @param apiKey the key from API
     * @return the list of movies
     */
    @GET(GENRE + "/" + MOVIE + "/" + LIST)
    Call<GenreHolder> loadMovieGenres(@Query(API_KEY) String apiKey);

    /**
     * Loads the most popular movies
     *
     * @param language the popular parameter needed for the search
     * @param apiKey the key from API
     * @return the list of the most popular movies
     */
    @GET(MOVIE + "/" + POPULAR)
    Call<MoviesInfo> loadMostPopularMoviesInfo(@Query(API_KEY) String apiKey, @Query(LANGUAGE) String language);

}
