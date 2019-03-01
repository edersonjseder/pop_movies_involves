package involves.test.com.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import involves.test.com.popularmovies.R;
import involves.test.com.popularmovies.model.MovieListReviews;
import involves.test.com.popularmovies.model.MovieReviewInf;

/**
 * Adapter for the reviews list on DetailsActivity
 */
public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsViewHolder> {

    private List<MovieReviewInf> movieReviewsInfList;
    private Context context;

    public MovieReviewsAdapter (Context mContext) {
        this.context = mContext;
    }

    @NonNull
    @Override
    public MovieReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_reviews_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewsViewHolder movieReviewsViewHolder, int position) {

        MovieReviewInf movieReviewInf = movieReviewsInfList.get(position);

        movieReviewsViewHolder.getTextViewReviewAuthor().setText(movieReviewInf.getAuthor());
        movieReviewsViewHolder.getTextViewDescReview().setText(movieReviewInf.getContent());
        movieReviewsViewHolder.getTextViewDescReview().setMovementMethod(new ScrollingMovementMethod());

    }

    @Override
    public int getItemCount() {
        return (movieReviewsInfList != null) ? movieReviewsInfList.size() : 0;
    }

    public List<MovieReviewInf> getMovieReviewsInfList() {
        return movieReviewsInfList;
    }

    public void setMovieReviewsInfList(List<MovieReviewInf> movieReviewsInfList) {
        this.movieReviewsInfList = movieReviewsInfList;
    }

    /**
     * Adapter ViewHolder
     */
    protected class MovieReviewsViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewReviewAuthor;
        private TextView textViewDescReview;

        public MovieReviewsViewHolder(View itemView) {
            super(itemView);

            textViewReviewAuthor = itemView.findViewById(R.id.textViewReviewAuthor);
            textViewDescReview = itemView.findViewById(R.id.textViewDescReview);

        }

        public TextView getTextViewReviewAuthor() {
            return textViewReviewAuthor;
        }

        public TextView getTextViewDescReview() {
            return textViewDescReview;
        }
    }
}
