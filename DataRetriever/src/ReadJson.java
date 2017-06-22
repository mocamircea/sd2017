/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.net.URL;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author Roxana
 */
public class ReadJson 
{
	private ArrayList<DataEntry> objects;
    
    public ReadJson()
    {
        objects = new ArrayList<DataEntry>();
    }
    
    public JSONObject getJson(String exchange, String fsym, String tsym) 
    { //get Json from yahoo finance
        
        String str = new String();
        try {
        	String uri = "https://min-api.cryptocompare.com/data/histoday?fsym=" + fsym + "&tsym=" + tsym + "&e=" + exchange + "&allData=true";
        	URL yahoofUrl = new URL(uri); 
            // read from the URL
                try (Scanner scan = new Scanner(yahoofUrl.openStream())) {
                    while (scan.hasNext()) {
                        str += scan.nextLine();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ReadJson.class.getName()).log(Level.SEVERE, null, ex);
                }
        } catch (MalformedURLException e){
            System.out.println(e);
        }
         //build a JSON object
        JSONObject obj = new JSONObject(str);
        return obj;
    }
    
    public void parseJson(JSONObject obj)
    {
    	//check if Response == Success
        if(obj.getString("Response").equals("Success")) {
            JSONArray lang= (JSONArray) obj.get("Data");
            for(int i=0; i<lang.length(); i++){        
                DataEntry entry = new DataEntry();
                entry.setTime(lang.getJSONObject(i).getLong("time"));
                //System.out.println(entry.getTime());
                entry.setClose(lang.getJSONObject(i).getDouble("close"));
                entry.setHigh(lang.getJSONObject(i).getDouble("high"));
                entry.setLow(lang.getJSONObject(i).getDouble("low"));
                entry.setOpen(lang.getJSONObject(i).getDouble("open"));
                entry.setVolumeFrom(lang.getJSONObject(i).getDouble("volumefrom"));
                entry.setVolumeTo(lang.getJSONObject(i).getDouble("volumeto"));

                 objects.add(entry);
            }
        }
    }

	public ArrayList<DataEntry> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<DataEntry> objects) {
		this.objects = objects;
	}
}
