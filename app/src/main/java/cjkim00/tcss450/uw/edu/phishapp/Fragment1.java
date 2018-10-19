package cjkim00.tcss450.uw.edu.phishapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;


public class Fragment1 extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;
    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        Button button = (Button) view.findViewById(R.id.Fragment1_Register_Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_main_container, new Fragment2());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        button = view.findViewById(R.id.Fragment1_Login_Button);
        //button.setOnClickListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText Username = (EditText) view.findViewById(R.id.Fragment1_Username);
                final EditText Password = (EditText) view.findViewById(R.id.Fragment1_Password);

                String User = Username.getText().toString();
                String Pass = Password.getText().toString();
                if(User.length() == 0) {
                    Username.setError("Must enter a username");
                }
                if(Pass.length() == 0) {
                    Password.setError("Must enter a password");
                }
                if(User.length() > 0 && !User.contains("@")) {
                    Username.setError("Enter a valid email.");
                }
                if(User.length() > 0 && User.contains("@") && Pass.length() > 0) {
                    //HomeActivity homeActivity = new HomeActivity();
                    /*
                    Fragment3 f3 = new Fragment3();
                    Bundle args = new Bundle();
                    args.putString("Username", User);
                    args.putString("Password", Pass);
                    f3.setArguments(args);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_main_container, f3);
                    transaction.commit();
                    */
                    Intent intent = new Intent(v.getContext(), HomeActivity.class);
                    Bundle args = new Bundle();
                    args.putString("Username", User);
                    args.putString("Password", Pass);
                    intent.putExtras(args);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if(mListener != null) {
            mListener.onFragmentInteraction1();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction1();
    }
}
