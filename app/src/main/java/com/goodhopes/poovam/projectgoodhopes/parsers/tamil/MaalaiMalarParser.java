package com.goodhopes.poovam.projectgoodhopes.parsers.tamil;

import android.util.Log;

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
import java.util.Locale;

/**
 * Created by poovam on 8/12/16.
 * image source present inside cdata has to be parsed
 * date has to be converted
 */

public class MaalaiMalarParser {

    public static ArrayList<Entry> parseResponse(String response){
        ArrayList<Entry> maalaiMalarEntries = new ArrayList<>();
        final XMLParser parser = new XMLParser();

        response = response.replace("\n","");
        response = response.replace("\r","");
        response = response.replace("<p>","");
        Document doc = parser.getDomElement(response);
        NodeList nl = doc.getElementsByTagName("item");
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);

            String title = (parser.getValue(e,"title"));
            String content = (parser.getValue(e,"description"));
            String thumbNailURL = (parser.getValue(e,"image"));
            String contentURL = (parser.getValue(e,"link"));
            String time = (parser.getValue(e,"pubDate"));
            Timestamp timestamp = new Timestamp(new Date().getDate());
            try {
                //Fri, 09 Dec 2016 00:44:00 +0530
                SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
                Date parsedDate = dateFormat.parse(time);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                //Log.d("maalaimalar",timestamp.toString());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            maalaiMalarEntries.add(new Entry(title,Subscription.MAALAIMALAR.name,content,thumbNailURL,
                    timestamp,contentURL, Subscription.MAALAIMALAR.iconID));
        }

        return maalaiMalarEntries;
    }
}
