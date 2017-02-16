package com.aht.android;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aht.android.db.DatabaseHelper;
import com.aht.android.db.Question;
import com.aht.android.masterDetail.*;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA = "nix.Extra";
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
        for (Question q : questions) {
            Log.i("Question ID", q.getId().toString());
            Log.i("Question question", q.getQuestion());
            Log.i("Question Answer", q.getAnswer());
            Log.i("Question current Date", q.getDateCreated().toString());
            Log.i("Question DueDate", q.getDueDate().toString());
            Log.i("DB Path", String.valueOf(getDatabasePath("question")));
        }
    }

    public void testRest(View view) throws Exception {
//        RestConnection rc = new RestConnection();
//        rc.connect();
//        rc.saveProjectStatus();
//
//        if(new File(RestConnection.HOME_DIR + RestConnection.FS + "status.xml").exists())
//        {
//            Intent intent = new Intent(this, TestRestActivity.class);
//            intent.putExtra(EXTRA,/*rc.loadProjectStatus().get(0).getCode()*/ "Test");
//            startActivity(intent);
//        }
//        else
//        {
//            TextView textView = (TextView)findViewById(R.id.textView);
//            textView.setText(R.string.failed);
//        }
    }

    public void startSurvey(View view) {
        startActivity(new Intent(getApplicationContext(), QuestionListActivity.class));
    }


}
