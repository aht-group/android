package com.aht.android.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aht.android.R;
import com.aht.config.tables.Answer;
import com.aht.config.tables.Question;
import com.aht.config.tables.Section;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by krueger on 25.01.2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "question";
	private static final int DATABASE_VERSION = 1;

	/**
	 * The data access object used to interact with the Sqlite database to do C.R.U.D operations.
	 * <p>
	 * R.raw.ormlite_config is a reference to the ormlite_config.txt file in the
	 * /res/raw/ directory of this project
	 */
	private Dao<Question, Long> questionDao;
	private Dao<Section, Long> sectionDao;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);

	}

	/**
	 * creates the question database table
	 */
	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {

			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Question.class);
			TableUtils.createTable(connectionSource, Section.class);
			TableUtils.createTable(connectionSource, Answer.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Recreates the database when onUpgrade is called by the framework
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
						  int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Question.class, false);
			TableUtils.dropTable(connectionSource, Section.class, false);
			TableUtils.dropTable(connectionSource, Answer.class, false);
			onCreate(database, connectionSource);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns an instance of the data access object Question
	 * @throws SQLException
	 */
	public Dao<Question, Long> getQuestionDao() throws SQLException {
		if (questionDao == null) {
			try {
				questionDao = getDao(Question.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return questionDao;
	}

	/**
	 * @return Returns an instance of the data access object Section
	 * @throws SQLException
	 */
	public Dao<Section, Long> getSectionDao() throws SQLException {
		if (sectionDao == null) {
			try {
				sectionDao = getDao(Section.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return sectionDao;
	}
}

