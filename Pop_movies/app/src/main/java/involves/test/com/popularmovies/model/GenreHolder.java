package involves.test.com.popularmovies.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GenreHolder {

    @JsonProperty("genres")
    private List<Genre> genres = null;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
