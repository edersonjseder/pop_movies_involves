package involves.test.com.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import involves.test.com.popularmovies.R;
import involves.test.com.popularmovies.listener.OnItemClickListener;
import involves.test.com.popularmovies.listener.OnMovieItemSelectedListener;
import involves.test.com.popularmovies.model.Movie;
import involves.test.com.popularmovies.utils.GlideApp;
import involves.test.com.popularmovies.utils.ImagePathUtils;
import involves.test.com.popularmovies.utils.Utils;

/**
 * Adapter for the movies list on Main activity
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private List<Movie> movieList = new ArrayList<>();
    private Context context;

    public MovieAdapter(Context mContext) {

        this.context = mContext;

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {

        final Movie movie = movieList.get(position);

        movieViewHolder.getTextViewMovieTitleListItem().setText(movie.getOriginalTitle());
        movieViewHolder.getTextViewMovieReleaseDateListItem().setText(Utils.changeDateOrder(movie.getReleaseDate()));

        String uri = ImagePathUtils.buildImageUrl(movie.getPosterPath());

        GlideApp.with(context)
                .load(uri.trim())
                .fitCenter()
                .into(movieViewHolder.getImageViewMoviePicture());

        movieViewHolder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ((OnMovieItemSelectedListener)context).onMovieItemSelected(movie, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (movieList != null) ? movieList.size() : 0;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    /**
     * Adapter ViewHolder
     */
    protected class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_movie_picture)
        ImageView imageViewMoviePicture;

        @BindView(R.id.text_view_movie_title_list_item)
        TextView textViewMovieTitleListItem;

        @BindView(R.id.text_view_movie_release_date_list_item)
        TextView textViewMovieReleaseDateListItem;

        private OnItemClickListener onItemClickListener;

        public MovieViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public ImageView getImageViewMoviePicture() {
            return imageViewMoviePicture;
        }

        public TextView getTextViewMovieTitleListItem() {
            return textViewMovieTitleListItem;
        }
        public TextView getTextViewMovieReleaseDateListItem() {
            return textViewMovieReleaseDateListItem;
        }
    }

}
