package com.example.mbankole.tripplanner.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mbankole.tripplanner.ApiClients.GmapClient;
import com.example.mbankole.tripplanner.ExploreActivity;
import com.example.mbankole.tripplanner.PlanActivity;
import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.Route;
import com.example.mbankole.tripplanner.models.User;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import permissions.dispatcher.PermissionUtils;

import static com.example.mbankole.tripplanner.R.id.map;

/**
 * Created by ericar on 7/13/17.
 */

public class PlanMapFragment extends Fragment implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnPolylineClickListener, GoogleMap.OnPoiClickListener {

    public PlanActivity planActivity;
    public ExploreActivity exploreActivity;
    public ArrayList<User> people;
    public ArrayList<Location> places;
    FragmentManager fm;
    private GoogleMap mMap;

    final PlanMapFragment self = this;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    String TAG = "MAPFRAGMENT";

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    private View v;

    public static PlanMapFragment newInstance() {
        Bundle args = new Bundle();
        //args.putParcelable("user", user);
        PlanMapFragment fragment = new PlanMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // inflate and return the layout
        //setHasOptionsMenu(true);

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_map, container, false);
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_map, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        MenuItem miProfile = menu.findItem(R.id.miProfile);
        miProfile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        MenuItem miPlan = menu.findItem(R.id.miPlan);
        miPlan.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setOnMarkerClickListener(this);
        map.setOnPolylineClickListener(this);
        map.setOnPoiClickListener(this);
        enableMyLocation();
        if (places.size() != 0) {
            addPins(map);
        }
        if (places.size() > 1) {
            showRoutes(places);
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    public void addPins(GoogleMap map) {
        IconGenerator iconFactory = new IconGenerator(getContext());
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < places.size(); i++) {
            addIcon(iconFactory, alphabet.charAt(i) + ": " + places.get(i).name, places.get(i), map);
            builder.include(places.get(i).latLng);
        }
        LatLngBounds bounds = builder.build();
        int padding = 240; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.moveCamera(cu);
    }


    private void addIcon(IconGenerator iconFactory, CharSequence text, Location location, GoogleMap map) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(location.latLng).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
        Marker marker = map.addMarker(markerOptions);
        marker.setTag(location);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.hasSelfPermissions(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            //showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    public void refresh() {
        mMap.clear();
        addPins(mMap);
        showRoutes(places);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Location location = (Location) marker.getTag();
        LocationDetailFragment frag = LocationDetailFragment.newInstance(location, true);
        frag.planMapFragment = this;
        frag.show(fm, "name");
        return false;
    }

    private void showRoutes(ArrayList<Location> places) {
        for (int i = 0; i < places.size() - 1; i++) {
            if (places.get(i).transport != null && places.get(i).transport.mode != null) {
                String mode = "";
                switch (places.get(i).transport.mode) {
                    case DRIVING:
                        mode = "driving";
                        break;
                    case WALKING:
                        mode = "walking";
                        break;
                    case TRANSIT:
                        mode = "transit";
                        break;
                    default:
                        mode = null;
                        break;
                }
                GmapClient.getDirections(places.get(i), places.get(i + 1), mode, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Route rt = Route.routeFromJson(response);
                        mMap.addPolyline(new PolylineOptions()
//                                .color(R.color.colorPrimaryDark)
                                .addAll(rt.latLongArray)
                                .clickable(true))
                                .setTag(rt);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    }
                });

            }
        }
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        RouteFragment frag = RouteFragment.newInstance((Route) polyline.getTag());
        frag.show(fm, "name");
    }

    @Override
    public void onPoiClick(PointOfInterest poi) {
                /*Toast.makeText(getContext(), "Clicked: " +
                        poi.name + "\nPlace ID:" + poi.placeId +
                        "\nLatitude:" + poi.latLng.latitude +
                        " Longitude:" + poi.latLng.longitude,
                Toast.LENGTH_SHORT).show();
                */
        //Log.d(TAG, poi.toString());
        GmapClient.getDetailFromId(poi.placeId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.d(TAG, response.toString());
                Location loc = null;
                try {
                    loc = Location.locationFromJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LocationDetailFragment frag = LocationDetailFragment.newInstance(loc, false);
                frag.planMapFragment = self;
                frag.show(fm, "detail");
            }
        });
    }

    public void addLocation(Location location) {
        places.add(location);
        String ToastString = "";
        for (int i=0; i<places.size(); i++) {
            ToastString += places.get(i).name;
        }
        Toast toast = Toast.makeText(getContext(), ToastString, Toast.LENGTH_LONG);
        toast.show();
    }
}