package com.example.mbankole.tripplanner.ApiClients;

import com.example.mbankole.tripplanner.models.Location;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mbankole on 7/21/17.
 */

public class LocationDetailCache {
    static ArrayList<Location> cache = new ArrayList<>();
    static long count = 0;

    public static Location getLocationDetail(String googleId) {
        for (int i = 0; i < cache.size(); i++) {
            Location loc = cache.get(i);
            if (loc.googleId.equals(googleId)) return loc;
        }
        final Location location = new Location();
        location.googleId = "nulsll";
        GmapClient.getDetailFromId(googleId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Location.locationFromJson(response, location);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //while(location.googleId.equals("nulsll")) {}
        cache.add(location);
        count++;
        return location;
    }
}
