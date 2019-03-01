package involves.test.com.popularmovies.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MovieListReviews {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("results")
    private List<MovieReviewInf> movieReviewInf = new ArrayList<>();

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("total_results")
    private Integer totalResults;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<MovieReviewInf> getMovieReviewInf() {
        return movieReviewInf;
    }

    public void setMovieReviewInf(List<MovieReviewInf> movieReviewInf) {
        this.movieReviewInf = movieReviewInf;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
