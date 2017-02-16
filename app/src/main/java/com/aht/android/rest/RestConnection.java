package com.aht.android.rest;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.core.executors.URLConnectionClientExecutor;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import rw.gov.loda.meis.interfaces.rest.MeisMobileRest;

public class RestConnection {

    public MeisMobileRest getRest() {
        return rest;
    }
    private MeisMobileRest rest;

    public void connect() throws Exception {

		String urlStr = "http://localhost:8080/meis/rest/mobile/jaxb/";

        if(isInternetAvailable(urlStr))
        {
            try {
				ClientExecutor executor = new URLConnectionClientExecutor((HttpURLConnection) new URL(urlStr).openConnection());
                rest = ProxyFactory.create(MeisMobileRest.class,urlStr,executor);
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
