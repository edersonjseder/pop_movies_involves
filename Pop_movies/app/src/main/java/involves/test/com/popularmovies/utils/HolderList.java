package involves.test.com.popularmovies.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import involves.test.com.popularmovies.model.Genre;
import involves.test.com.popularmovies.model.Movie;

/**
 * Class to store the list on the main activity so that when the screen rotates this class with the
 * list loaded can be put in the bundle and be saved as an instance state
 */
public class HolderList implements Serializable {

    private List<Movie> listMovie;

    private List<Genre> listGenre;

    public HolderList() {
        listMovie = new ArrayList<>();
    }

    public List<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(List<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    public List<Genre> getListGenre() {
        return listGenre;
    }

    public void setListGenre(List<Genre> listGenre) {
        this.listGenre = listGenre;
    }
}
