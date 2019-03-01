package involves.test.com.popularmovies.utils;

import involves.test.com.popularmovies.model.Movie;

/**
 * Class to format the String date to the dd/MM/yyy pattern
 */
public class Utils {

    static StringBuilder builder;

    public static String changeDateOrder(String date) {

        String[] dateArray = date.split("-");
        String dateModified = "";
        builder = new StringBuilder();

        for (int i = dateArray.length - 1; i >= 0 ; i--) {

            if (i != 0) {

                dateModified = builder.append(dateArray[i]).append("/").toString();

            } else {

                dateModified = builder.append(dateArray[i]).toString();

            }

        }

        return dateModified;
    }


    public static String selectAndCompareGenresById(Movie movie) {

        StringBuilder builder = new StringBuilder();

        String[] genreNames = {};

        if (movie.getGenreIds() != null) {

            genreNames = new String[movie.getGenreIds().size()];

            for (int j = 0; j < movie.getGenreIds().size(); j++) {

                for (int i = 0; i < movie.getGenres().size(); i++) {

                    if (movie.getGenres().get(i).getId().intValue() == movie.getGenreIds().get(j).intValue()) {

                        genreNames[j] = movie.getGenres().get(i).getName();

                        break;

                    }

                }

            }

        }

        for (int i = 0; i < genreNames.length; i++) {

            if (i != (genreNames.length - 1)) {

                builder.append(genreNames[i]).append(", ");

            } else {

                builder.append(genreNames[i]);

            }

        }

        return builder.toString();

    }
}
