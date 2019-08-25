package com.example.utsav.youtubedetails;

import android.net.Uri;
import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

class SimpleYouTubeHelper extends AsyncTask<String, String, String[]>
{
    private static String APIKey="AIzaSyA3Fp6kS1be8qYBxlNTEHMZ2o8MsUuTfJg";

    protected String[] doInBackground(String... urls)
    {
        String X[]=new String[4];
        String title = getTitle(urls[0]);
        X[0]=title;
        String channel = getChannel(urls[0]);
        X[1]=channel;
        String imageURL = getImageUrl(urls[0]);
        X[2]=imageURL;
        String views = getViews(urls[0])+"";
        X[3]=views;
        return X;
    }

    private static String getImageUrl(String youtubeUrl) {
        try {
            if (youtubeUrl != null) {
                return String.format("http://img.youtube.com/vi/%s/0.jpg", Uri.parse(youtubeUrl).getQueryParameter("v"));
            }
        } catch (UnsupportedOperationException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static String getTitle(String youtubeUrl) {
        try {
            if (youtubeUrl != null) {
                URL embededURL = new URL("http://www.youtube.com/oembed?url=" + youtubeUrl + "&format=json");
                return new JSONObject(IOUtils.toString(embededURL)).getString("title");
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getChannel(String youtubeUrl) {
        try {
            if (youtubeUrl != null) {
                URL embededURL = new URL("http://www.youtube.com/oembed?url=" + youtubeUrl + "&format=json");
                return new JSONObject(IOUtils.toString(embededURL)).getString("author_name");
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Long getViews(String youtubeUrl)
    {
        try {
            if (youtubeUrl != null) {
                URL embededURL = new URL("https://www.googleapis.com/youtube/v3/videos?part=statistics&id="+Uri.parse(youtubeUrl).getQueryParameter("v")+"&key="+APIKey);
                JSONObject views = new JSONObject(IOUtils.toString(embededURL));
                JSONArray items = views.getJSONArray("items");
                JSONObject statistics = items.getJSONObject(0).getJSONObject("statistics");
                return statistics.getLong("viewCount");
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}