package cjkim00.tcss450.uw.edu.phishapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnClickSetListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class OnClickSetListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String setUrl;
    public OnClickSetListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null) {
            String date = getArguments().getString("Date");
            String location = getArguments().getString("Location");
            String setList = getArguments().getString("Data");
            String notes = getArguments().getString("Notes");


            final TextView setDate = getActivity().findViewById(R.id.click_long_date);
            final TextView setLocation = getActivity().findViewById(R.id.click_location);
            final TextView setListView = getActivity().findViewById(R.id.click_set_list_data);
            final TextView setNotes = getActivity().findViewById(R.id.click_set_list_notes);

            setDate.setText(date);
            setLocation.setText(location);
            setListView.setText(Html.fromHtml(setList));
            setNotes.setText(Html.fromHtml(notes));
            setUrl = getArguments().getString("Url");


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_click_set_list, container, false);
        Button b = (Button) view.findViewById(R.id.set_list_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(setUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
