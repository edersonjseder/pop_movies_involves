package involves.test.com.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import involves.test.com.popularmovies.R;
import involves.test.com.popularmovies.listener.OnItemClickListener;
import involves.test.com.popularmovies.listener.OnMovieTrailerSelectedListener;
import involves.test.com.popularmovies.model.MovieListTrailer;
import involves.test.com.popularmovies.model.MovieTrailerInf;

/**
 * Adapter for the Trailers list on DetailsActivity
 */
public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerViewHolder> {

    private static final String TAG = MovieTrailerAdapter.class.getSimpleName();

    private List<MovieTrailerInf> movieTrailerInfList;
    private Context context;

    public MovieTrailerAdapter(Context mContext) {

        this.context = mContext;

    }

    @NonNull
    @Override
    public MovieTrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieTrailerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerViewHolder movieViewHolder, int position) {

        MovieTrailerInf movieTrailer = movieTrailerInfList.get(position);

        movieViewHolder.getImageViewMovieTrailerPicture().setImageResource(R.drawable.youtube_icon);
        movieViewHolder.getTextViewMovieTrailerName().setText(movieTrailer.getName());

        movieViewHolder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ((OnMovieTrailerSelectedListener)context).onMovieTrailerSelected(movieTrailer.getVideoId(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (movieTrailerInfList != null) ? movieTrailerInfList.size() : 0;
    }

    public List<MovieTrailerInf> getMovieTrailerInfList() {
        return movieTrailerInfList;
    }

    public void setMovieTrailerInfList(List<MovieTrailerInf> movieTrailerInfList) {
        this.movieTrailerInfList = movieTrailerInfList;
    }

    /**
     * Adapter ViewHolder
     */
    protected class MovieTrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageViewMovieTrailerPicture;
        private TextView textViewMovieTrailerName;

        private OnItemClickListener onItemClickListener;

        public MovieTrailerViewHolder(View itemView) {
            super(itemView);

            imageViewMovieTrailerPicture = itemView.findViewById(R.id.movie_trailer_photo);
            textViewMovieTrailerName = itemView.findViewById(R.id.movie_trailer_name);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public ImageView getImageViewMovieTrailerPicture() {
            return imageViewMovieTrailerPicture;
        }

        public TextView getTextViewMovieTrailerName() {
            return textViewMovieTrailerName;
        }
    }

}
