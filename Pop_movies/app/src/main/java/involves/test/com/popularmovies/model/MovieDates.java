package involves.test.com.popularmovies.model;

import java.io.Serializable;

public class MovieDates implements Serializable {

    private String maximum;
    private String minimum;

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }
}
