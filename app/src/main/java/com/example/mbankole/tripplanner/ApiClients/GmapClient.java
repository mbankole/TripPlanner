package com.example.mbankole.tripplanner.ApiClients;

import com.example.mbankole.tripplanner.models.Location;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by mbankole on 7/13/17.
 */

public class GmapClient {
    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/";

    static String[] Keys = {"AIzaSyAu4LnwwfQl6FQhWvl2K_mtjAj844rMyGU", "AIzaSyAAP5pDzemIFGVPdgDxhI9xxCNBShO1yM8", "AIzaSyAJ0ziieskALR-hFcJrO727DIUnIYcTPes"};

    private static final String API_KEY = Keys[ThreadLocalRandom.current().nextInt(0, 3)];
            //"AIzaSyAu4LnwwfQl6FQhWvl2K_mtjAj844rMyGU"; // good
    //private static final String API_KEY = "AIzaSyAIxBmOYjuG-dGC86LhChPsoR619GCcAOM";
    //private static final String API_KEY = "AIzaSyAAP5pDzemIFGVPdgDxhI9xxCNBShO1yM8"; // good
    //private static final String API_KEY = "AIzaSyA6Ps9FNXuKi4jY7w5usQoaad7vm-15m-Q";
    //public static final String API_KEY = "AIzaSyAJ0ziieskALR-hFcJrO727DIUnIYcTPes"; // good
    //pls no stealing


    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void locationSearch(String query, AsyncHttpResponseHandler responseHandler) {
        String relativeUrl = "place/textsearch/json";
        String absoluteUrl = getAbsoluteUrl(relativeUrl);
        RequestParams params = new RequestParams();
        params.put("key", API_KEY );
        params.put("query", query);
        client.get(absoluteUrl, params, responseHandler);
    }

    public static String generateImageUrl(String ref) {
        String relativeUrl = "place/photo";
        String absoluteUrl = getAbsoluteUrl(relativeUrl);
        absoluteUrl = absoluteUrl + "?key=" + API_KEY;
        absoluteUrl = absoluteUrl + "&maxwidth=1000";
        absoluteUrl = absoluteUrl + "&photoreference=" + ref;
        return absoluteUrl;
    }

    public static void getDetailFromId(String Id, AsyncHttpResponseHandler responseHandler) {
        String relativeUrl = "place/details/json";
        String absoluteUrl = getAbsoluteUrl(relativeUrl);
        RequestParams params = new RequestParams();
        params.put("key", API_KEY );
        params.put("placeid", Id);
        client.get(absoluteUrl, params, responseHandler);
    }

    public static void getImageFromReference(String ref, AsyncHttpResponseHandler responseHandler) {
        String relativeUrl = "place/photo";
        String absoluteUrl = getAbsoluteUrl(relativeUrl);
        RequestParams params = new RequestParams();
        params.put("key", API_KEY );
        params.put("photoreference", ref);
        params.put("maxwidth", 500);
        client.get(absoluteUrl, params, responseHandler);
    }

    public static void getDirections(Location origin, Location destination, String travelMode, AsyncHttpResponseHandler responseHandler) {
        String relativeUrl = "directions/json";
        String absoluteUrl = getAbsoluteUrl(relativeUrl);
        RequestParams params = new RequestParams();
        params.put("key", API_KEY );
        params.put("origin", "place_id:" + origin.googleId);
        params.put("destination", "place_id:" + destination.googleId);
        params.put("mode", travelMode);
        client.get(absoluteUrl, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
