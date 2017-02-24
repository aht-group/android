package com.aht.android.rest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aht.android.MainActivity;
import com.aht.android.R;
import com.aht.android.db.DatabaseHelper;

import org.jeesl.model.json.survey.Question;
import org.jeesl.model.json.survey.Section;
import org.jeesl.model.json.survey.Survey;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestRestActivity extends AppCompatActivity {

	private DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_rest);

		Intent intent = getIntent();
		List<Section> sections = ((Survey) intent.getBundleExtra("bundle").get("Survey")).getTemplate().getSections();

		ListView list = (ListView) findViewById(R.id.sectionList);
		ListAdapter adapter = new SectionsAdapter(this, R.layout.list_test_rest, R.id.testRest_listItem, sections);
		list.setAdapter(adapter);
		String message = intent.getStringExtra(MainActivity.EXTRA);
		TextView textView = (TextView) findViewById(R.id.success);
		textView.setText(message);

		dbHelper = new DatabaseHelper(this);
		Log.i("Init question dao?", "" +(dbHelper.getQuestionDao() != null));
		saveDataAtDb((Survey) intent.getBundleExtra("bundle").get("Survey"));
	}

	private void saveDataAtDb(Survey survey) {

		try {
			for (Section s : survey.getTemplate().getSections()) {
				com.aht.config.tables.Section section = new com.aht.config.tables.Section();
				section.setName(s.getName());
				section.setSystemId(s.getId());
				List<com.aht.config.tables.Question> questions = new ArrayList<>();
				for (Question q : s.getQuestions()) {
					com.aht.config.tables.Question question = new com.aht.config.tables.Question();
					question.setSystemId(q.getId());
					question.setCode(q.getCode());
					question.setMaxScore(q.getMaxScore());
					question.setPosition(q.getPosition());
					question.setMinScore(q.getMinScore());
					question.setQuestion(q.getQuestion());
					question.setShowBoolean(q.getShowBoolean());
					question.setShowText(q.getShowText());
					question.setShowRemark(q.getShowRemark());
					question.setShowInteger(q.getShowInteger());
					question.setShowDouble(q.getShowDouble());
					question.setShowScore(q.getShowScore());
					question.setRemark(q.getRemark());
					question.setVisible(q.isVisible());
					questions.add(question);
					dbHelper.getQuestionDao().create(question);
				}
				section.setQuestions(questions);
				dbHelper.getSectionDao().create(section);
			}
		}
		catch (SQLException ex) {
			Log.e("SQLException", "SQLException at saveDataAtDb(Survey survey) " + this.getClass().getSimpleName());
		}
	}

	private class SectionsAdapter extends ArrayAdapter<Section> {

		SectionsAdapter(Context context, int resource, int textViewResourceId, List<Section> objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@NonNull
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;

			if (v == null) {
				LayoutInflater vi;
				vi = LayoutInflater.from(getContext());
				v = vi.inflate(R.layout.list_test_rest, null);
			}

			Section s = getItem(position);
			if(s != null) {
				TextView tv = (TextView) v.findViewById(R.id.testRest_listItem);
				tv.setText(s.getName());
			}

			return v;
		}
	}

}
