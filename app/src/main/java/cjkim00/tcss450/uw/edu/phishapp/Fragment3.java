package cjkim00.tcss450.uw.edu.phishapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class Fragment3 extends Fragment {


    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        final TextView Username = (TextView) view.findViewById(R.id.Inputted_Username);
        String user = getArguments().get("Username").toString();
        Username.setText(user);

        final TextView Password = (TextView) view.findViewById(R.id.Inputted_Password);
        String pass = getArguments().get("Password").toString();
        Password.setText(pass);

        return view;
    }

}
