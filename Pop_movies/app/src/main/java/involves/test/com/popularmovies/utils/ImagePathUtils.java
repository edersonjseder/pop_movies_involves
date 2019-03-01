package involves.test.com.popularmovies.utils;

import android.net.Uri;

/**
 * Class to generate image URL to be fetched by Glide library
 *
 * Created by root on 08/10/16.
 */

public class ImagePathUtils {

    static final String IMG_URL  = "http://image.tmdb.org/t/p/w500";

    /**
     * This method builds the URL to retrieve the images to be put on the screen
     *
     * @param imageUrl The url coming from POJO
     * @return The url generated to be passed to Glide
     */
    public static String buildImageUrl(String imageUrl) {

        Uri builtUri = Uri.parse(IMG_URL).buildUpon()
                .appendEncodedPath(imageUrl)
                .build();

        return builtUri.toString();
    }

}
