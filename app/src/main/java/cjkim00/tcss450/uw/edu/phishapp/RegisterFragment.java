package cjkim00.tcss450.uw.edu.phishapp;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cjkim00.tcss450.uw.edu.phishapp.model.Credentials;
import cjkim00.tcss450.uw.edu.phishapp.utils.SendPostAsyncTask;


public class RegisterFragment extends Fragment implements View.OnClickListener {

    Credentials mCredentials;
    private OnfragmentInteractionListener mListener;
    HashMap<String, String> UserPass = new HashMap<String, String>();
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        Button button = (Button) view.findViewById(R.id.Fragment2_Register_Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration(v);
            }
        });
        return view;
    }

    private void attemptRegistration(View view) {
        final EditText firstName = (EditText) getActivity().findViewById(R.id.firstName);
        final EditText lastName = (EditText) getActivity().findViewById(R.id.lastName);
        final EditText Username = (EditText) getActivity().findViewById(R.id.Fragment2_Username);
        final EditText Password1 = (EditText) getActivity().findViewById(R.id.Fragment2_password1);
        final EditText Password2 = (EditText) getActivity().findViewById(R.id.Fragment2_password2);

        String User = Username.getText().toString();
        String Pass1 = Password1.getText().toString();
        String Pass2 = Password2.getText().toString();
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();

        if(firstName.length() == 0) {
            firstName.setError("Must enter a name");
        }
        if(lastName.length() == 0) {
            lastName.setError("Must enter a name");
        }
        if(!User.contains("@")) {
            Username.setError("Enter a valid email");
        }
        if(User.length() == 0) {
            Username.setError("Must enter a username");
        }
        if(Pass1.length() < 6) {
            Password1.setError("Password must be longer than 6 characters");
        }
        if(Pass1.length() == 0) {
            Password1.setError("Must enter a password");
        }
        if(Pass2.length() == 0) {
            Password2.setError("Must enter a password");
        }
        if (Pass1.length() > 0 && Pass2.length() > 0 && !Pass1.equals(Pass2)) {
            Password2.setError("Passwords must match");
        }
        if(User.length() > 0 && Pass1.length() > 5 && Pass2.length() > 5 && Pass1.equals(Pass2)) {

            Credentials.Builder builder = new Credentials.Builder(User, Pass1);
            builder.addFirstName(first);
            builder.addLastName(last);
            builder.addUsername(User);
            Credentials credentials = builder.build();
            Uri uri = new Uri.Builder()
                    .scheme("https")
                    .appendPath(getString(R.string.ep_base_url))
                    .appendPath(getString(R.string.ep_register))
                    .build();
            JSONObject msg = credentials.asJSONObject();
            mCredentials = credentials;
            new SendPostAsyncTask.Builder(uri.toString(), msg)
                    .onPreExecute(this::handleLoginOnPre)
                    .onPostExecute(this::handleLoginOnPost)
                    .onCancelled(this::handleErrorsInTask)
                    .build().execute();

        }
    }

    /**
     * Handle errors that may occur during the AsyncTask.
     * @param result the error message provide from the AsyncTask
     */
    private void handleErrorsInTask(String result) {
        Log.e("ASYNCT_TASK_ERROR", result);
    }

    /**
     * Handle the setup of the UI before the HTTP call to the webservice.
     */
    private void handleLoginOnPre() {
        mListener.onWaitFragmentInteractionShow();
    }

    /**
     * Handle onPostExecute of the AsynceTask. The result from our webservice is
     * a JSON formatted String. Parse it for success or failure.
     * @param result the JSON formatted String response from the web service
     */
    private void handleLoginOnPost(String result) {
        try {
            Log.d("JSON result",result);
            JSONObject resultsJSON = new JSONObject(result);
            boolean success = resultsJSON.getBoolean("success");
            mListener.onWaitFragmentInteractionHide();
            if (success) {
                //Login was successful. Inform the Activity so it can do its thing.
                mListener.onFragmentInteraction2(mCredentials);
            } else {
                //Login was unsuccessful. Don’t switch fragments and inform the user
                ((TextView) getView().findViewById(R.id.Fragment2_Username))
                        .setError("Login Unsuccessful");
            }
        } catch (JSONException e) {
            //It appears that the web service didn’t return a JSON formatted String
            //or it didn’t have what we expected in it.
            Log.e("JSON_PARSE_ERROR", result
                    + System.lineSeparator()
                    + e.getMessage());
            mListener.onWaitFragmentInteractionHide();
            ((TextView) getView().findViewById(R.id.Fragment2_Username))
                    .setError("Login Unsuccessful");
        }
    }
    /*
    @Override
    public void onClick(View v) {
        if(mListener != null) {
            mListener.OnfragmentInteractionListener(mCredentials);
        }
    }
    */
    public HashMap<String, String> getUserPass() {
        return UserPass;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnfragmentInteractionListener) {
            mListener = (OnfragmentInteractionListener) context;
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

    public interface OnfragmentInteractionListener extends WaitFragment.OnFragmentInteractionListener{
        void onFragmentInteraction2(Credentials credentials);
    }
}
