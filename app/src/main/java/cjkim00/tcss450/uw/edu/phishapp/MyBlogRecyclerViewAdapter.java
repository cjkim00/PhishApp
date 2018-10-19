package cjkim00.tcss450.uw.edu.phishapp;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cjkim00.tcss450.uw.edu.phishapp.BlogFragment.OnListFragmentInteractionListener;
import cjkim00.tcss450.uw.edu.phishapp.blog.BlogPost;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BlogPost} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBlogRecyclerViewAdapter extends RecyclerView.Adapter<MyBlogRecyclerViewAdapter.ViewHolder> {

    private final BlogPost[] mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyBlogRecyclerViewAdapter(BlogPost[] blogs, OnListFragmentInteractionListener listener) {
        mValues = blogs;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_blog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues[position];
        holder.mBlogTitle.setText( mValues[position].getTitle());
        holder.mPublishDate.setText(mValues[position].getPubDate());
        holder.mBlogPreview.setText(Html.fromHtml(mValues[position].getTeaser().substring(0, 100)));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mBlogTitle;
        public final TextView mPublishDate;
        public final TextView mBlogPreview;
        public BlogPost mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mBlogTitle = (TextView) view.findViewById(R.id.blog_title);
            mPublishDate = (TextView) view.findViewById(R.id.publish_date);
            mBlogPreview = (TextView) view.findViewById(R.id.blog_preview);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mPublishDate.getText() + "'";
        }
    }
}
