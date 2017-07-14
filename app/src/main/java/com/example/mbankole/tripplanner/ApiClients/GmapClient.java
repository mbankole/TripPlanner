package com.example.mbankole.tripplanner.ApiClients;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by mbankole on 7/13/17.
 */

public class GmapClient {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/";
    private static final String API_KEY = "AIzaSyAu4LnwwfQl6FQhWvl2K_mtjAj844rMyGU";
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

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
