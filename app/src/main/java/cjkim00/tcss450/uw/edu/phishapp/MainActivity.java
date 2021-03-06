package cjkim00.tcss450.uw.edu.phishapp;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cjkim00.tcss450.uw.edu.phishapp.model.Credentials;

public class MainActivity extends AppCompatActivity implements WaitFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener, RegisterFragment.OnfragmentInteractionListener {
    private boolean mLoadFromChatNotification = false;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("type")) {
                Log.d(TAG, "type of message: " + getIntent().getExtras().getString("type"));
                mLoadFromChatNotification = getIntent().getExtras().getString("type").equals("msg");
            } else {
                Log.d(TAG, "NO MESSAGE");
            }
        }
        if (savedInstanceState == null) {
            if (findViewById(R.id.frame_main_container) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_main_container, new LoginFragment())
                        .commit();
            }
        }
    }

    @Override
    public void onWaitFragmentInteractionShow() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_main_container, new WaitFragment(), "WAIT")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onWaitFragmentInteractionHide() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(getSupportFragmentManager().findFragmentByTag("WAIT"))
                .commit();
    }

    @Override
    public void onLoginFragmentLoginInteraction(Credentials credentials) {
        /*
        SuccessFragment successFragment = new SuccessFragment();
        Bundle args = new Bundle();
        args.putString("Username", credentials.getEmail());
        args.putString("Password", credentials.getPassword());
        successFragment.setArguments(args);
        loadFragment(successFragment);
        */
        //End this Activity and remove it from the Activity back stack.
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        //Bundle args = new Bundle();
        //args.putString("Username", credentials.getEmail());
        //args.putString("Password", credentials.getPassword());
        //intent.putExtras(args);
        intent.putExtra("Username", credentials.getEmail());
        intent.putExtra("Password", credentials.getPassword());
        intent.putExtra(getString(R.string.keys_intent_notifification_msg), mLoadFromChatNotification);
        startActivity(intent);
        finish();
    }

    private void loadFragment(Fragment frag) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main_container, frag)
                .addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction2(Credentials credentials) {
        SuccessFragment successFragment = new SuccessFragment();
        Bundle args = new Bundle();
        args.putString("Username", credentials.getEmail());
        args.putString("Password", credentials.getPassword());
        successFragment.setArguments(args);
        loadFragment(successFragment);
    }
}
