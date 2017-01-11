package com.aht.android.rest;

import net.sf.ahtutils.xml.status.Status;
import net.sf.exlp.util.xml.JaxbUtil;

//import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
//import org.jboss.resteasy.client.jaxrs.ResteasyClient;
//import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
//import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import rw.gov.loda.meis.interfaces.rest.MeisClientRest;
import rw.gov.loda.meis.model.xml.meis.Meis;

public class RestConnection {

    public static final  String HOME_DIR = System.getProperty("user.home");
    public static final  String FS = System.getProperty("file.separator");

    private MeisClientRest rest;
//    public MeisClientRest getRest() {
//        return rest;
//    }

    public void connect() {

        if(isInternetAvailable("http://testing.meis.loda.gov.rw/meis"))
        {
//            ResteasyClient client = new ResteasyClientBuilder().build();
//            client.register(new BasicAuthentication("roblick@aht-group.com", "vur3ar4hi3"));
//            ResteasyWebTarget restTarget = client.target("http://testing.meis.loda.gov.rw/meis");
//            rest = restTarget.proxy(MeisClientRest.class);
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

    /**
     * Gets ProjectStatus information from REST and saves them.
     */
    public void saveProjectStatus()
    {
        JaxbUtil.save(new File(HOME_DIR + FS + "status.xml"), rest.getProjectStatus(), true);
//        new File(HOME_DIR + FS + "status.xml").mkdir();
    }

    public List<Status> loadProjectStatus() throws FileNotFoundException {
        Meis projectStatus = JaxbUtil.loadJAXB(HOME_DIR + FS + "status.xml", Meis.class);
        return projectStatus.getStatus();
    }
}
