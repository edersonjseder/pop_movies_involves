package involves.test.com.popularmovies.listener;

import android.view.View;

/**
 * Interface to do the event to get the position of the item clicked on recycler list view
 */
public interface OnItemClickListener {

    void onItemClick(View view, int position);

}
