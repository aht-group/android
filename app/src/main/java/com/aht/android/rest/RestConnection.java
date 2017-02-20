package com.aht.android.rest;


import com.google.gson.GsonBuilder;

import org.jeesl.model.json.system.status.JsonContainer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestConnection {

	/**
	 * Establishing a connection via REST service
	 * @param urlStr URL to connect to as String
	 * @param post false = GET request; true = POST request
	 * @return Data as InputStreamReader. NULL if nothing is returned from server
	 */
	private InputStreamReader connect(String urlStr, boolean post) {

       InputStreamReader ir = null;
        if(isInternetAvailable(urlStr))
        {
            try
			{
				HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();

				if (!post)
				{
					con.setRequestMethod("GET");
					ir = new InputStreamReader(new BufferedInputStream(con.getInputStream()));
				}
            }
            catch (Exception e) {e.printStackTrace();}
        }
		return ir;
    }

	/**
	 * Check for connection via internet
	 * @param address url as string to test
	 * @return true if connection could be established else false
	 */
    private boolean isInternetAvailable(String address)
    {
        URL url;
        HttpURLConnection con;
        int response;
        try
        {
            url = new URL(address);
            con = (HttpURLConnection) url.openConnection();
            con.connect();
            response = con.getResponseCode();
        }
		catch(IOException e)
        {
            //ToDo Alert for missing connection
            return false;
        }
        return response == 200;
    }


	/**
	 * Just for test case. Converts a InputStreamReader into a JsonContainer.
	 * @return JsonContainer object
	 */
	public JsonContainer test()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("M/d/yy hh:mm a");
		return gsonBuilder.create().fromJson(connect("http://localhost:8080/meis/rest/mobile/test/", false), JsonContainer.class);
	}
}
