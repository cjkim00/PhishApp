package cjkim00.tcss450.uw.edu.phishapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fragment2 extends Fragment {


    HashMap<String, String> UserPass = new HashMap<String, String>();
    public Fragment2() {
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

                final EditText Username = (EditText) view.findViewById(R.id.Fragment2_Username);
                final EditText Password1 = (EditText) view.findViewById(R.id.Fragment2_password1);
                final EditText Password2 = (EditText) view.findViewById(R.id.Fragment2_password2);

                String User = Username.getText().toString();
                String Pass1 = Password1.getText().toString();
                String Pass2 = Password2.getText().toString();

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
                    /*
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    Fragment3 f3 = new Fragment3();
                    Bundle args = new Bundle();
                    args.putString("Username", User);
                    args.putString("Password", Pass1);
                    f3.setArguments(args);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_main_container, f3);
                    manager.popBackStack();
                    transaction.commit();
                    */
                    Intent intent = new Intent(v.getContext(), HomeActivity.class);
                    Bundle args = new Bundle();
                    args.putString("Username", User);
                    args.putString("Password", Pass1);
                    intent.putExtras(args);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    public HashMap<String, String> getUserPass() {
        return UserPass;
    }

    public interface OnfragmentInteractionListener {
        void onFragmentInteraction2();
    }
}
