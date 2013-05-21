package com.helloruiz.superbad.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helloruiz.superbad.R;

public class FragmentArticle extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// The article fragment disappears whenever you rotate the screen. This will help re-create it.
		if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
		
		return inflater.inflate(R.layout.fragment_article, container, false);
	}

	@Override
    public void onStart() {
        super.onStart();
        
        // Helps re-apply the article view after a screen rotation or whatever.
        if (mCurrentPosition != -1) updateArticleView(mCurrentPosition);
	}
	public void updateArticleView(int position) {
		TextView article = (TextView) getActivity().findViewById(R.id.the_article_textview);
		article.setText(Content.article_content[position]);
		mCurrentPosition = position;
	}

	int mCurrentPosition = -1;
	final static String ARG_POSITION = "position";
}
