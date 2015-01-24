package com.weebly.marlookup.main;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.*;

public class Player {
	URL url;
	

	JSONObject info;
	
	long summonerID;
	String name;
	int profileIconid;
	long level;
	long revisionDate;
	public Runes runes;
	public Masteries masteries;
	public String server;
	public MatchHistory history;
	
	public Player(String apikey, String server, String Name) {
		this.server = server;
		this.name = Name;
		
		populateURL(apikey, true);
		
		try {
			info = JsonReader.getallFrom(url.toString());
			info = info.getJSONObject(info.names().get(0).toString());
			} catch (Exception e) {
			e.printStackTrace();
		}
		readInfo();
		
		
		
	}
	public Player(String apikey, String server, long summonerID) {
		this.server = server;
		this.summonerID = summonerID;
		
		if (populateURL(apikey, false))
		{
		try {
			info = JsonReader.getallFrom(url.toString());
			info = info.getJSONObject(info.names().get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		readInfo();}
		else {
			summonerID = 0;
			name = "ERROR";
			profileIconid = 0;
			level = 0;
			revisionDate = 0;
		}
	}
	
	private boolean populateURL(String apikey, boolean byname) {
		boolean ret = false;
		try {
			
			if (byname)
			url = new URL("https://"+server+".api.pvp.net/api/lol/"+server+"/v1.4/summoner/by-name/"+URLEncoder.encode(name, "UTF-8").replace("+", "%20")+"?api_key="+apikey);
			else url = new URL("https://"+server+".api.pvp.net/api/lol/"+server+"/v1.4/summoner/"+URLEncoder.encode(name, "UTF-8").replace("+", "%20")+"?api_key="+apikey);
			ret = true;
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
		return ret;
	}
	
	private void readInfo() {
		System.out.println("Reading Info...");
		System.out.println(info);
		System.out.println("Populating Player Info...");
		
		this.summonerID = info.getLong("id");
		this.name = info.getString("name");
		this.profileIconid = info.getInt("profileIconId");
		this.revisionDate = info.getLong("revisionDate");
		this.level = info.getLong("summonerLevel");
		
		System.out.println("Information succesfully Populated!");
		
	}
	public int getIconId() {
		return profileIconid;
	}

}
