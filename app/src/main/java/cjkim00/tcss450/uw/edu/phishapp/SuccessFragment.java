package cjkim00.tcss450.uw.edu.phishapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SuccessFragment extends Fragment {


    public SuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_success, container, false);

        Button b = view.findViewById(R.id.successLoginButton);



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User = getArguments().get("Username").toString();
                String Pass = getArguments().get("Password").toString();
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                Bundle args = new Bundle();
                args.putString("Username", User);
                args.putString("Password", Pass);
                intent.putExtras(args);
                startActivity(intent);
            }
        });

        b = view.findViewById(R.id.successLogoutButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_main_container, loginFragment);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.popBackStack();
                transaction.commit();
            }
        });

        return view;
    }

}
