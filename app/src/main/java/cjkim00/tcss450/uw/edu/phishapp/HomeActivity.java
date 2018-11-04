package cjkim00.tcss450.uw.edu.phishapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cjkim00.tcss450.uw.edu.phishapp.SetList.SetLists;
import cjkim00.tcss450.uw.edu.phishapp.blog.BlogPost;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BlogFragment.OnListFragmentInteractionListener,
        WaitFragment.OnFragmentInteractionListener,
        SetListsFragment.OnListFragmentInteractionListener,
        OnClickSetListFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final TextView Username = (TextView) findViewById(R.id.homeUsername);
        String user = intent.getStringExtra("Username");
        //Username.setText(user);

        final TextView Password = (TextView) findViewById(R.id.homePassword);
        String pass = intent.getStringExtra("Password");
        //Password.setText(pass);
        /*
        Fragment3 f3 = new Fragment3();
        Bundle b = new Bundle();
        b.putString("Username", user);
        b.putString("Password", pass);
        f3.setArguments(b);
        */
        final Bundle args = new Bundle();
        args.putString("email", user);
        Fragment fragment;
        if (getIntent().getBooleanExtra(getString(R.string.keys_intent_notifification_msg), false)) {
            fragment = new ChatFragment();
        } else {
            fragment = new SuccessFragment();
            fragment.setArguments(args);
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment);
                //.addToBackStack(null);
        // Commit the transaction
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.home_button) {
            // Handle the camera action
            TextView textView1 = this.findViewById(R.id.homeUsername);
            TextView textView2 = this.findViewById(R.id.homePassword);
            textView1.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
            Fragment3 f3 = new Fragment3();
            Bundle b = getIntent().getExtras();
            String user = b.getString("Username");
            String pass = b.getString("Password");
            Bundle args = new Bundle();
            args.putString("Username", user);
            args.putString("Password", pass);
            f3.setArguments(args);
            FragmentManager manager = this.getSupportFragmentManager();
            manager.popBackStack();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, f3);
                    //.addToBackStack(null);
            // Commit the transaction
            transaction.commit();


        } else if (id == R.id.blog_posts) {//use this for getting set lists
            //loadFragment(new BlogFragment());
            Uri uri = new Uri.Builder()
                    .scheme("https")
                    .appendPath(getString(R.string.ep_base_url))
                    .appendPath(getString(R.string.ep_phish))
                    .appendPath(getString(R.string.ep_blog))
                    .appendPath(getString(R.string.ep_get))
                    .build();
            new GetAsyncTask.Builder(uri.toString())
                    .onPreExecute(this::onWaitFragmentInteractionShow)
                    .onPostExecute(this::handleBlogGetOnPostExecute)
                    .build().execute();
        } else if (id == R.id.Set_Lists) {

            Uri uri = new Uri.Builder()
                    .scheme("https")
                    .appendPath(getString(R.string.ep_base_url))
                    .appendPath(getString(R.string.ep_phish))
                    .appendPath(getString(R.string.set_lists))
                    .appendPath(getString(R.string.ep_recent))
                    .build();
            new GetAsyncTask.Builder(uri.toString())
                    .onPreExecute(this::onWaitFragmentInteractionShow)
                    .onPostExecute(this::handleSetListsGetOnPostExecute)
                    .build().execute();

        } else if (id == R.id.Global_Chat) {
            ChatFragment chatFragment = new ChatFragment();
            FragmentManager manager = this.getSupportFragmentManager();
            manager.popBackStack();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, chatFragment);
            //.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);


        return true;
    }

    @Override
    public void onListFragmentInteraction(BlogPost post) {
        //use same function from ui lab
        BlogPostFragment bpf;
        bpf = (BlogPostFragment) getSupportFragmentManager().findFragmentById(R.id.display);
        if(bpf != null) {

        } else {
            bpf = new BlogPostFragment();
            Bundle args = new Bundle();
            args.putSerializable("title", post.getTitle());
            args.putSerializable("publish", post.getPubDate());
            args.putSerializable("teaser", post.getTeaser());
            args.putSerializable("url", post.getUrl());

            bpf.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, bpf)
                    .addToBackStack(null);
            transaction.commit();
        }
    }

    private void handleBlogGetOnPostExecute(final String result) {
        //parse JSON
        try {
            JSONObject root = new JSONObject(result);
            if (root.has("response")) {
                JSONObject response = root.getJSONObject("response");
                if (response.has("data")) {
                    JSONArray data = response.getJSONArray("data");
                    List<BlogPost> blogs = new ArrayList<>();
                    for(int i = 0; i < data.length(); i++) {
                        JSONObject jsonBlog = data.getJSONObject(i);
                        blogs.add(new BlogPost.Builder(jsonBlog.getString("pubdate"),
                                jsonBlog.getString("title"))
                                .addTeaser(jsonBlog.getString("teaser"))
                                .addUrl(jsonBlog.getString("url"))
                                .build());
                    }
                    BlogPost[] blogsAsArray = new BlogPost[blogs.size()];
                    blogsAsArray = blogs.toArray(blogsAsArray);
                    Bundle args = new Bundle();
                    args.putSerializable(BlogFragment.ARG_BLOG_LIST, blogsAsArray);
                    Fragment frag = new BlogFragment();
                    frag.setArguments(args);
                    onWaitFragmentInteractionHide();

                    TextView textView1 = this.findViewById(R.id.homeUsername);
                    TextView textView2 = this.findViewById(R.id.homePassword);
                    textView1.setVisibility(View.INVISIBLE);
                    textView2.setVisibility(View.INVISIBLE);

                    loadFragment(frag);
                } else {
                    Log.e("ERROR!", "No data array");
                    //notify user
                    onWaitFragmentInteractionHide();
                }
            } else {
                Log.e("ERROR!", "No response");
                //notify user
                onWaitFragmentInteractionHide();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
            //notify user
            onWaitFragmentInteractionHide();
        }
    }

    private void handleSetListsGetOnPostExecute(final String result) {
        try {
            JSONObject root = new JSONObject(result);
            if (root.has("response")) {
                JSONObject response = root.getJSONObject("response");
                if (response.has("data")) {
                    JSONArray data = response.getJSONArray("data");
                    List<SetLists> setLists = new ArrayList<>();
                    for(int i = 0; i < data.length(); i++) {
                        JSONObject jsonSet = data.getJSONObject(i);
                        /*
                        setLists.add(new SetLists.Builder(jsonSet.getString("long_date"),
                                jsonSet.getString("url"),
                                jsonSet.getString("venue"))
                                .addLocation(jsonSet.getString("location"))
                                .addSetList(jsonSet.getString("setlistdata"))
                                .addSetListNodes(jsonSet.getString("setlistnotes"))
                                .build());
                                */
                        setLists.add(new SetLists.Builder(jsonSet.getString("long_date"),
                                jsonSet.getString("location"),
                                jsonSet.getString("venue"))
                                .addSetList(jsonSet.getString("setlistdata"))
                                .addSetListNodes(jsonSet.getString("setlistnotes"))
                                .addUrl(jsonSet.getString("url"))
                                .build());
                    }
                    SetLists[] setAsArray = new SetLists[setLists.size()];
                    setAsArray = setLists.toArray(setAsArray);
                    Bundle args = new Bundle();
                    args.putSerializable(SetListsFragment.ARG_SET_LIST, setAsArray);
                    SetListsFragment frag = new SetListsFragment();
                    frag.setArguments(args);
                    onWaitFragmentInteractionHide();

                    TextView textView1 = this.findViewById(R.id.homeUsername);
                    TextView textView2 = this.findViewById(R.id.homePassword);
                    textView1.setVisibility(View.INVISIBLE);
                    textView2.setVisibility(View.INVISIBLE);

                    loadFragment(frag);
                } else {
                    Log.e("ERROR!", "No data array");
                    //notify user
                    onWaitFragmentInteractionHide();
                }
            } else {
                Log.e("ERROR!", "No response");
                //notify user
                onWaitFragmentInteractionHide();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
            //notify user
            onWaitFragmentInteractionHide();
        }
    }
    private void loadFragment(Fragment frag) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, frag)
                .addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onWaitFragmentInteractionShow() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, new WaitFragment(), "WAIT")
                //.addToBackStack(null)
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
    public void onListFragmentInteraction(SetLists item) {
        OnClickSetListFragment set;
        set = (OnClickSetListFragment) getSupportFragmentManager().findFragmentById(R.id.on_click_frag);
        if(set != null) {

        } else {
            set = new OnClickSetListFragment();
            Bundle args = new Bundle();
            args.putSerializable("Date", item.getLongDate());
            args.putSerializable("Location", item.getLocation());
            args.putSerializable("Data", item.getSetListData());
            args.putSerializable("Notes", item.getSetListNodes());
            args.putSerializable("Url", item.getUrl());

            set.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, set)
                    .addToBackStack(null);
            transaction.commit();
        }
    }

    private void logout() {
        new DeleteTokenAsyncTask().execute();
    }

    // Deleting the InstanceId (Firebase token) must be done asynchronously. Good thing
// we have something that allows us to do that.
    class DeleteTokenAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onWaitFragmentInteractionShow();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            //since we are already doing stuff in the background, go ahead
            //and remove the credentials from shared prefs here.
            SharedPreferences prefs =
                    getSharedPreferences(
                            getString(R.string.keys_shared_prefs),
                            Context.MODE_PRIVATE);
            prefs.edit().remove(getString(R.string.keys_prefs_password)).apply();
            prefs.edit().remove(getString(R.string.keys_prefs_email)).apply();
            try {
                //this call must be done asynchronously.
                FirebaseInstanceId.getInstance().deleteInstanceId();
            } catch (IOException e) {
                Log.e("FCM", "Delete error!");
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //close the app
            finishAndRemoveTask();
            //or close this activity and bring back the Login
// Intent i = new Intent(this, MainActivity.class);
// startActivity(i);
// //Ends this Activity and removes it from the Activity back stack.
// finish();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}


