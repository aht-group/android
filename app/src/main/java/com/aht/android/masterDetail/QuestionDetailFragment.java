package com.aht.android.masterDetail;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aht.android.R;
import com.aht.android.appContent.AppContent;

/**
 * A fragment representing a single Question detail screen.
 * This fragment is either contained in a {@link QuestionListActivity}
 * in two-pane mode (on tablets) or a {@link QuestionDetailActivity}
 * on handsets.
 */
public class QuestionDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy question this fragment is presenting.
     */
    private AppContent.Item mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public QuestionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy question specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load question from a question provider.
            mItem = AppContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.question);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_detail, container, false);

        // Show the dummy question as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.question_detail)).setText(mItem.answer);
        }

        return rootView;
    }
}
