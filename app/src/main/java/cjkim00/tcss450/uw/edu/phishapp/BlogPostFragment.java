package cjkim00.tcss450.uw.edu.phishapp;


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
 */
public class BlogPostFragment extends Fragment {

    private String blogURL;
    public BlogPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null) {
            String title = getArguments().getString("title");
            final TextView blogTitle = getActivity().findViewById(R.id.display_blog_title);
            blogTitle.setText(title);

            String publish = getArguments().getString("publish");
            final TextView blogPublishDate = getActivity().findViewById(R.id.display_blog_publish_date);
            blogPublishDate.setText(publish);

            String preview = getArguments().getString("teaser");
            final TextView blogPreview = getActivity().findViewById(R.id.display_full_teaser);
            blogPreview.setText(Html.fromHtml(preview));

            blogURL = getArguments().getString("url");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_blog_post, container, false);
        Button b = (Button) view.findViewById(R.id.full_post);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(blogURL);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        return view;
    }

}
