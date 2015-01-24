package com.weebly.marlookup.main;
//Code from: http://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException, InterruptedException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
      
    }
  }
  
  public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException, InterruptedException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONArray json = new JSONArray(jsonText);
	      return json;
	    } finally {
	      is.close();
	      
	    }
	  }
  
  public static String getFrom (String adress, String thing) throws IOException, JSONException {
	  String ret = "";
	 // System.out.println("Adresse: "+adress);
	  JSONObject json = null;
	try {
		json = readJsonFromUrl(adress);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//  System.out.println("Erfolgreich.");
	  	ret = json.get(thing).toString();
	//  System.out.println(ret);
	  return ret ;
  }
  
  public static JSONObject getallFrom (String adress) throws IOException, JSONException {
	//  System.out.println("Adresse: "+adress);
	  JSONObject json = null;
	try {
		json = readJsonFromUrl(adress);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//  System.out.println("Erfolgreich.");
	  return json ;
  }
}
