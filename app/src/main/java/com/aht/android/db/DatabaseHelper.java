package com.aht.android.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.aht.android.R;
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
         */
        private Dao<Question, Long> questionDao;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION,
                    /**
                     * R.raw.ormlite_config is a reference to the ormlite_config.txt file in the
                     * /res/raw/ directory of this project
                     * */
                    R.raw.ormlite_config);
        }

        @Override
        public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
            try {

                /**
                 * creates the question database table
                 */
                TableUtils.createTable(connectionSource, Question.class);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                              int oldVersion, int newVersion) {
            try {
                /**
                 * Recreates the database when onUpgrade is called by the framework
                 */
                TableUtils.dropTable(connectionSource, Question.class, false);
                onCreate(database, connectionSource);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Returns an instance of the data access object
         * @return
         * @throws SQLException
         */
        public Dao<Question, Long> getDao() throws SQLException {
            if(questionDao == null) {
                try {
                    questionDao = getDao(Question.class);
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
            return questionDao;
        }
    }

