package involves.test.com.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import involves.test.com.popularmovies.detail.MovieDetailActivity;
import involves.test.com.popularmovies.main.MainMoviesActivity;
import involves.test.com.popularmovies.model.Movie;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    private Movie movie;

    @Rule
    public ActivityTestRule<MainMoviesActivity> mActivityRule =
            new ActivityTestRule<>(MainMoviesActivity.class);


    @Rule
    public ActivityTestRule<MovieDetailActivity> mActivityTestRule
            = new ActivityTestRule<MovieDetailActivity>(MovieDetailActivity.class, true,
            false);

    // Testing launching of Detail activity
    @Test
    public void verifyMovieSentToDetailActivity() {

        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();

        movie = new Movie();

        fillMovie(movie);

        Intent intent = new Intent(targetContext, MovieDetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, movie);

        mActivityTestRule.launchActivity(intent);


    }

    // Testing list scroll position
    @Test
    public void scrollToPosition() {
        
        onView(withId(R.id.recyclerview_movies))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, ViewActions.click()));

        onView(withId(R.id.image_movie_picture_detail)).check(matches(isDisplayed()));

        onView(withId(R.id.textview_movie_release_date_detail)).check(matches(isDisplayed()));

        onView(withId(R.id.textview_movie_vote_average_detail)).check(matches(isDisplayed()));

        onView(withId(R.id.textview_movie_overview_detail)).check(matches(isDisplayed()));

        onView(withId(R.id.textview_movie_release_date_detail_label)).check(matches(withText(R.string.label_movie_detail_release_date)));

        onView(withId(R.id.textview_movie_vote_average_detail_label)).check(matches(withText(R.string.label_movie_detail_vote_average)));

    }

    // Check if the list is not empty
    @Test
    public void ensureListHasValuesOnIt() {

        if (getRecyclerViewCount() > 0) {
            onView(withId(R.id.recyclerview_movies)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        }

    }

    /** Count items on list **/
    private int getRecyclerViewCount(){

        RecyclerView recyclerView = (RecyclerView) mActivityRule.getActivity().findViewById(R.id.recyclerview_movies);

        int spanCount = 0;

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {

            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

            spanCount = gridLayoutManager.getSpanCount();

            System.out.println("TESTS" + spanCount);


        } else {

            throw new IllegalStateException("no grid layout manager");

        }


        return spanCount;
    }

    // Fill the movie object
    private void fillMovie(Movie movie) {
        movie.setId(61020);
        movie.setTitle("Adventures of Captain Marvel");
        movie.setOriginalTitle("Adventures of Captain Marvel");
        movie.setVideo(false);
        movie.setVoteAverage(6.3);
        movie.setVoteCount(7);
        movie.setPopularity(1.171);
        movie.setPosterPath("/nxV2WBQHIPkFIoRzDzTVx52U5X6.jpg");
        movie.setOriginalLanguage("en");
        movie.setBackdropPath("/dkWcUCrXFTotX4VoIguEVC511dh.jpg");
        movie.setAdult(false);
        movie.setReleaseDate("1941-03-28");
        movie.setOverview("On a scientific expedition to Siam young Billy Batson is given the ability to change himself into the super-powered Captain Marvel by the wizard Shazam, who tells him his powers will last only as long as the Golden Scorpion idol is threatened. Finding the idol, the scientists realize it could be the most powerful weapon in the world and remove the lenses that energize it, distributing them among themselves so that no one would be able to use the idol by himself. Back in the US, Billy Batson, as Captain Marvel, wages a battle against an evil, hooded figure, the Scorpion, who hopes to accumulate all five lenses, thereby gaining control of the super-powerful weapon");
    }

}
