package com.aht.android.masterDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.aht.android.R;
import com.aht.android.appContent.AppContent;
import com.aht.android.appContent.AppContent.Item;
import com.aht.android.db.DatabaseHelper;
import com.aht.config.tables.Question;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aht.android.appContent.AppContent.ITEM_MAP;

/**
 * An activity representing a list of Questions. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link QuestionDetailActivity} representing
 * item answer. On tablets, the activity presents the list of items and
 * item answer side-by-side using two vertical panes.
 */
public class QuestionListActivity extends AppCompatActivity {

    /** Whether or not the activity is in two-pane mode, i.e. running on a tablet device.*/
    private boolean mTwoPane;

    private EditText editText;
    private TextView textView;
    private Map<String, Item> questionList;
    private LongSparseArray<Question> questions;
	private List<Question> qList;
    private Question question;

    //DB Access
    private DatabaseHelper dbHelper;
//    private Dao<Section, Long> sectionDao;
    private Dao<Question, Long> questionDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
		loadQuestions();

        View recyclerView = findViewById(R.id.question_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.question_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        //Initialize GUI Elements for Answering qs
        editText = (EditText) findViewById(R.id.editTextApp);
        textView = (TextView) findViewById(R.id.textViewApp);

        //GetQuestionList
        questionList = ITEM_MAP;


        //initialise dbHelper
//        dbHelper = getHelper();

    }

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if(dbHelper != null) {
			OpenHelperManager.releaseHelper();
			dbHelper = null;
		}
	}

	//SaveButton
    public void saveAnswer(View view) {
        //popup
        Toast.makeText(this, "Answer saved", Toast.LENGTH_SHORT).show();

        //rootView of the QuestionDetailFragment to insert answers
        View rootView = view.getRootView();
        ((TextView) rootView.findViewById(R.id.question_detail)).setText(editText.getText());

        //manipulate item
//        questionList.get(QuestionDetailFragment.ARG_ITEM_ID).setAnswer(editText.getText().toString());
//        Item i = questionList.get(getQuestion().id);
//        i.setAnswer(editText.getText().toString());
//        questionList.put(i.id, i);

    }

    private void loadQuestions() {
        try {
			Log.i("Get Table question", "" + getHelper().getQuestionDao().isTableExists());

			qList = getHelper().getQuestionDao().queryForAll();
			Log.i("Load Questions", "Loaded " + qList.size() + " Questions");
        } catch (SQLException e) {
            e.printStackTrace();
        }
		if(questions == null) {
			questions = new LongSparseArray<>();
		}
		for(Question q : qList) {
			questions.put(q.getId(), q);
		}
    }

	private DatabaseHelper getHelper() {
		if (dbHelper == null) {
			dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
		return dbHelper;
	}


    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(qList));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.QuestionViewHolder> {

        private final List<Question> mValues;

        public SimpleItemRecyclerViewAdapter(List<Question> items) {

			Log.i("SIRecyclerViewAdapter", items.size() + " Questions transferrred");
			mValues = items;
        }

        @Override
        public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_list_content, parent, false);
            return new QuestionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final QuestionViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
            holder.mContentView.setText(mValues.get(position).getQuestion());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();

                        //logging IDs
                        Log.i("ID: ", String.valueOf(holder.mItem.getId()));
                        //Set selected question
                        setQuestion(holder.mItem);
                        Log.i("Set Question: ", String.valueOf(getQuestion().getId()));

                        arguments.putString(QuestionDetailFragment.ARG_ITEM_ID, String.valueOf(holder.mItem.getId()));
                        QuestionDetailFragment fragment = new QuestionDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.question_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, QuestionDetailActivity.class);
                        intent.putExtra(QuestionDetailFragment.ARG_ITEM_ID, String.valueOf(holder.mItem.getId()));

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Item mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }

		public class QuestionViewHolder extends RecyclerView.ViewHolder {
			public final View mView;
			public final TextView mIdView;
			public final TextView mContentView;
			public Question mItem;

			public QuestionViewHolder(View view) {
				super(view);
				mView = view;
				mIdView = (TextView) view.findViewById(R.id.id);
				mContentView = (TextView) view.findViewById(R.id.content);
			}

			@Override
			public String toString() {
				return super.toString() + " '" + mContentView.getText() + "'";
			}
		}
    }
}
