package com.example.mbankole.tripplanner.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by danahe97 on 7/17/17.
 */

public class Route {

    public String overviewPolyline;
    public List<LatLng> latLongArray;

    public static Route routeFromJson(JSONObject obj) throws JSONException {
        Route route = new Route();
        routeFromJson(obj,route);
        return route;
    }

    public static void routeFromJson(JSONObject obj, Route route) throws JSONException{
        JSONObject result = obj.getJSONArray("routes").getJSONObject(0);
        route.overviewPolyline = result.getJSONObject("overview_polyline").getString("points");
        route.latLongArray = PolyUtil.decode(route.overviewPolyline);
        /**
        loc.address = result.optString("formatted_address", "No Address");
        loc.phoneNumber = result.optString("formatted_phone_number", "No Phone Number");
        loc.iconUrl = result.optString("icon", null);
        JSONArray photoData = result.getJSONArray("photos");
        loc.photoRef = photoData.getJSONObject(0).optString("photo_reference", null);
        loc.photoUrl = GmapClient.generateImageUrl(loc.photoRef);
        JSONObject latlngObj = result.getJSONObject("geometry").getJSONObject("location");
        loc.latLng = new LatLng(latlngObj.getDouble("lat"), latlngObj.getDouble("lng"));
         **/
    }
}
