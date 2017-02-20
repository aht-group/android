package com.aht.android.rest;


import com.google.gson.GsonBuilder;

import org.jeesl.model.json.system.status.JsonContainer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestConnection {

    public JsonContainer getRest() {
        return rest;
    }
    private JsonContainer rest;

    public void connect() {

		String urlStr = "http://localhost:8080/meis/rest/mobile/test/";

        if(isInternetAvailable(urlStr))
        {
            try {
				HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
				con.setRequestMethod("GET");

				InputStream in = new BufferedInputStream(con.getInputStream());
				String response;
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder sb = new StringBuilder();
				try {
					while ((response = reader.readLine()) != null) {
						sb.append(response).append('\n');
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println(sb.toString());


				GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.setDateFormat("M/d/yy hh:mm a");
				rest = gsonBuilder.create().fromJson(sb.toString(), JsonContainer.class);
//				System.out.println(rest.getStatus().get(0).getCode());

////                //Connection for RestEasy & Jaxb Only works Api 24+
//				ClientExecutor executor = new URLConnectionClientExecutor((HttpURLConnection) new URL(urlStr).openConnection());
//                rest = ProxyFactory.create(MeisMobileRest.class,urlStr,executor);
            }
            catch (Exception e) {
				e.printStackTrace();
            }
        }
    }

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
        } catch(IOException e)
        {
            //ToDo Alert for missing connection
            return false;
        }
        return response == 200;
    }
}
