package cjkim00.tcss450.uw.edu.phishapp;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import cjkim00.tcss450.uw.edu.phishapp.SetList.SetLists;
import cjkim00.tcss450.uw.edu.phishapp.SetListsFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SetLists} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySetListsRecyclerViewAdapter extends RecyclerView.Adapter<MySetListsRecyclerViewAdapter.ViewHolder> {

    private final List<SetLists> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MySetListsRecyclerViewAdapter(List<SetLists> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_setlists, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mLongDate.setText(mValues.get(position).getLongDate());
        //holder.mUrl
        holder.mLocation.setText(mValues.get(position).getLocation());
        holder.mVenue.setText(Html.fromHtml(mValues.get(position).getVenue()));
        //holder.mSetListData.setText(mValues.get(position).getSetListData());
        //holder.mSetListNodes.setText(mValues.get(position).getSetListNodes());

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
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mLongDate;
        public final TextView mLocation;
        public final TextView mVenue;
        //public final TextView mUrl;
        //public final TextView mSetListData;
        //public final TextView mSetListNodes;

        public SetLists mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mLongDate = (TextView) view.findViewById(R.id.date);
            mLocation = (TextView) view.findViewById(R.id.loc);
            mVenue = (TextView) view.findViewById(R.id.ven);
            //mSetListData = (TextView) view.findViewById(R.id.Set_List_Data);
            //mSetListNodes = (TextView) view.findViewById(R.id.Set_List_Nodes);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLongDate.getText() + "'";
        }
    }
}
