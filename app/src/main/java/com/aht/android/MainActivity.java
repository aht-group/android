package com.aht.android;

import android.content.Intent;
import android.database.SQLException;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aht.android.db.DatabaseHelper;
import com.aht.android.db.Question;
import com.aht.android.masterDetail.*;
import com.aht.android.rest.RestConnection;
import com.aht.android.rest.RestResultReceiver;
import com.aht.android.rest.TestRestActivity;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.jeesl.model.json.survey.Survey;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RestResultReceiver.Receiver{

    public static String EXTRA = "nix.Extra";
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ORMLite
        try {

            testOutOrmLiteDatabase();
            createQuestionMap();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createQuestionMap() {

    }

    private void testOutOrmLiteDatabase() throws SQLException {
        DatabaseHelper todoOpenDatabaseHelper = OpenHelperManager.getHelper(this,
                DatabaseHelper.class);

        Dao<Question, Long> questionDao = todoOpenDatabaseHelper.getDao();

        Date currDateTime = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDateTime);
        calendar.add(Calendar.DATE, 14);
        Date dueDate = calendar.getTime();

        //create DB Objects
        try {
            questionDao.create(new Question("Question Example 1", "Question Example 1 Description", currDateTime, dueDate));
            questionDao.create(new Question("Question Example 2", "Question Example 2 Description", currDateTime, dueDate));
            questionDao.create(new Question("Question Example 3", "Question Example 3 Description", currDateTime, dueDate));
            questions = questionDao.queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        //Logging DB Objects
//        for (Question q : questions) {
//            Log.i("Question ID", q.getId().toString());
//            Log.i("Question question", q.getQuestion());
//            Log.i("Question Answer", q.getAnswer());
//            Log.i("Question current Date", q.getDateCreated().toString());
//            Log.i("Question DueDate", q.getDueDate().toString());
//            Log.i("DB Path", String.valueOf(getDatabasePath("question")));
//        }
    }

    public void testRest(View view) throws Exception {

		Log.i("testRest", "Start REST test...");
		RestResultReceiver rr =  new RestResultReceiver(new Handler());
		rr.setReceiver(this);
        Intent intent = new Intent(this, RestConnection.class);
        intent.putExtra("relativeUrl", "/json/structure/en/4");
        intent.putExtra("post", false);
        intent.putExtra("receiver", rr);
        startService(intent);
    }

    public void startSurvey(View view) {
        startActivity(new Intent(getApplicationContext(), QuestionListActivity.class));
    }

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {
			case RestConnection.FINISH:
				Survey survey = (Survey) resultData.get("Survey");
				Log.i("Result FINISH", "Connection successful!");
				if(survey != null)
				{
					Intent intentTA = new Intent(this, TestRestActivity.class);
					intentTA.putExtra(EXTRA,survey.getTemplate().getSections().get(0).getQuestions().get(0).getQuestion()/*"Connection successful!"*/);
					startActivity(intentTA);
				}
				else
				{
					TextView textView = (TextView)findViewById(R.id.textView);
					textView.setText(R.string.failed);
				}
				break;
			case RestConnection.ERROR:
				Log.i("Result ERROR", "Connection failed!");
				TextView textView = (TextView)findViewById(R.id.textView);
				textView.setText(resultData.getString("Error"));
				break;
		}
	}
}
