package involves.test.com.popularmovies.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MovieListTrailer {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("results")
    private List<MovieTrailerInf> movieTrailerInfList = new ArrayList<>();

    @JsonIgnore
    private List<MovieReviewInf> movieReviewInfList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieTrailerInf> getMovieTrailerInfList() {
        return movieTrailerInfList;
    }

    public void setMovieTrailerInfList(List<MovieTrailerInf> movieTrailerInfList) {
        this.movieTrailerInfList = movieTrailerInfList;
    }

    public List<MovieReviewInf> getMovieReviewInfList() {
        return movieReviewInfList;
    }

    public void setMovieReviewInfList(List<MovieReviewInf> movieReviewInfList) {
        this.movieReviewInfList = movieReviewInfList;
    }
}
