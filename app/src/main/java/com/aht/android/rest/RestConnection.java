package com.aht.android.rest;


import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.gson.GsonBuilder;

import org.jeesl.model.json.system.status.JsonContainer;
import org.jeesl.model.json.survey.Survey;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RestConnection extends IntentService {

	public static final int FINISH = 0;
	public static final int ERROR = -1;
	public static final int TEST_FINISH = 10;
	public static final int TEST_ERROR = -11;

	public static final String BASE_URL = "http://10.0.2.2:8080/meis/rest/mobile";

	public RestConnection() {
		super(RestConnection.class.getSimpleName());
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.i("RestConnection", "Handle intent...");

		final ResultReceiver receiver = intent.getParcelableExtra("receiver");
		Bundle bundle = new Bundle();
		try {
			Log.i("RestConnection", "Connect to " + BASE_URL + intent.getStringExtra("relativeUrl") + "...");
			InputStreamReader isr = connect(BASE_URL + intent.getStringExtra("relativeUrl"), intent.getBooleanExtra("post", false));

			if(isr != null) {
				Log.i("RestConnection","InputStream received.");
				bundle.putSerializable("Survey", getSurveyStructure(isr));
				if(intent.hasExtra("testMode"))
					receiver.send(TEST_FINISH, bundle);
				receiver.send(FINISH, bundle);
			}
		}
		catch (Exception ex) {
			bundle.putString("Error", ex.getMessage());
			if(intent.hasExtra("testMode"))
				receiver.send(TEST_ERROR, bundle);
			receiver.send(ERROR, bundle);
		}
	}

	/**
	 * Establishing a connection via REST service
	 *
	 * @param urlStr URL to connect to as String
	 * @param post   false = GET request; true = POST request
	 * @return Data as InputStreamReader. NULL if nothing is returned from server
	 */
	private InputStreamReader connect(String urlStr, boolean post) {

		InputStreamReader ir = null;
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();

			if (!post) {
				con.setRequestMethod("GET");
				if (con.getResponseCode() == 200) {
					ir = new InputStreamReader(new BufferedInputStream(con.getInputStream()));

//					BufferedReader br = new BufferedReader(ir);
//					String input = br.readLine();
//					StringBuffer sb = new StringBuffer();
//					while (input != null)
//					{
//						sb.append(input);
//						input = br.readLine();
//					}
//					System.out.println(sb.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ir;
	}

	private GsonBuilder initGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("M/d/yy hh:mm a");
		return gsonBuilder;
	}

	public Survey getSurveyStructure(InputStreamReader isr)
	{
		return initGson().create().fromJson(isr, Survey.class);
	}

	public JsonContainer getQuestionUnits()
	{
		return initGson().create().fromJson(connect(BASE_URL + "/json/question/units/", false), JsonContainer.class);
	}

	/**
	 * Just for test purpose. Converts a InputStreamReader into a JsonContainer.
	 *
	 * @return JsonContainer object
	 */
	public JsonContainer test() {
		return initGson().create().fromJson(connect(BASE_URL + "/test/", false), JsonContainer.class);
	}


}
