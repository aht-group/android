package com.aht.android.db;

/**
 * Created by krueger on 25.01.2017.
 */

import android.database.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;

/**
 * OrmliteDatabaseConfigUtil is a separate program from the actual android app,
 * that is used to generate the database structure configuration before runtime.
 * It uses the models provided via a list of class objects,
 * and also the annotations (e.g. @DatabaseField) on the models to generate the configuration accordingly.
 */
public class OrmLiteDatabaseConfigUtil extends OrmLiteConfigUtil {

    /**
     * classes represents the models to use for generating the ormlite_config.txt file
     */
    private static final Class<?>[] classes = new Class[] {Question.class};

    /**
     * Given that this is a separate program from the android app, we have to use
     * a static main java method to create the configuration file.
     * @param args
     * @throws IOException
     * @throws SQLException
     */
    public static void main(String[] args) throws IOException, SQLException {

        String currDirectory = "user.dir";

        String configPath = "/app/src/main/res/raw/ormlite_config.txt";

        /**
         * Gets the project root directory
         */
        String projectRoot = System.getProperty(currDirectory);

        /**
         * Full configuration path includes the project root path, and the location
         * of the ormlite_config.txt file appended to it
         */
        String fullConfigPath = projectRoot + configPath;

        File configFile = new File(fullConfigPath);

        /**
         * In the a scenario where we run this program several times, it will recreate the
         * configuration file each time with the updated configurations.
         */
        if(configFile.exists()) {
            configFile.delete();
            configFile = new File(fullConfigPath);
        }

        /**
         * writeConfigFile is a util method used to write the necessary configurations
         * to the ormlite_config.txt file.
         */
        try {
            writeConfigFile(configFile, classes);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}
