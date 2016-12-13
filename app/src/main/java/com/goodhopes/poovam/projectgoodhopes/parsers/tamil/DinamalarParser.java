package com.goodhopes.poovam.projectgoodhopes.parsers.tamil;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.goodhopes.poovam.projectgoodhopes.parsers.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by poovam on 7/12/16.
 * Dinamalar parser
 * parsed date
 * image is parsed
 * date is cleaned
 * same as Dinakaran parser for data cleaning and image parsing
 */

public class DinamalarParser {

    public static ArrayList<Entry> parseResponse(String response, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.saved_data),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(Subscription.DINAMALAR.stringID), response);
        editor.apply();
        XMLParser parser = new XMLParser();
        ArrayList<Entry> dinamalarEntries = new ArrayList<>();

        Document doc = parser.getDomElement(response);
        NodeList nl = doc.getElementsByTagName("item");
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);
            String title = (parser.getValue(e, "title"));
            String content = (parser.getValue(e,"description"));

            String thumbNailURL = content.split(".jpg")[0];
            thumbNailURL = thumbNailURL.substring(thumbNailURL.lastIndexOf("'")+1);
            thumbNailURL = thumbNailURL+".jpg";

            content = content.replaceAll(".*</a>", "");
            content = content.replaceAll("<p>","");
            content = content.replaceAll("</p>","");

            String contentURL = (parser.getValue(e, "link"));
            String time = (parser.getValue(e, "pubDate"));
            Timestamp timestamp = new Timestamp(new Date().getDate());
            try {
                //Thu, 08 Dec 2016 22:08:00 +0530
                SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
                Date parsedDate = dateFormat.parse(time);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                //Log.d("dinamalr",timestamp.toString());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            dinamalarEntries.add(new Entry(title,Subscription.DINAMALAR.name,content,thumbNailURL,
                    timestamp,contentURL,Subscription.DINAMALAR.iconID));
        }
        return dinamalarEntries;
    }
}
