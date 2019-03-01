package involves.test.com.popularmovies.constants;

import involves.test.com.popularmovies.BuildConfig;

/**
 * Constants for the REST requests
 */
public interface UrlConstants {

    String URL_BASE = "https://api.themoviedb.org/3/";
    String SEARCH  = "search";
    String MOVIE = "movie";
    String UPCOMING  = "upcoming";
    String GENRE = "genre";
    String LIST = "list";
    String API_KEY = "api_key";

    String SLASH = "/";

    String KEY = BuildConfig.API_KEY;

    String QUERY  = "query";
    String PAGE  = "page";
    String ADULT  = "include_adult";
    String VIDEOS  = "videos";
    String REVIEWS  = "reviews";
    String LANGUAGE  = "language";

    String YOUTUBE_URL = "vnd.youtube://";
    String YOUTUBE_INTERNET_URL = "http://www.youtube.com/watch?v=";

    String COUNTRY = "en_US";
    String POPULAR = "popular";

    int page = 1;
}
