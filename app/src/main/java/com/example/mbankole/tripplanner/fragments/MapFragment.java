package com.example.mbankole.tripplanner.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mbankole.tripplanner.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment extends Fragment implements GoogleMap.OnPoiClickListener, OnMapReadyCallback{

    MapView mMapView;
    private GoogleMap nGoogleMap;
    LocationManager locationManager;
    Criteria criteria;
    double lastLatitude;
    double lastLongitude;
    boolean mapReady = false;
    FragmentManager fm;
    String TAG = "MAPFRAGMENT";

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        mMapView.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // latitude and longitude
        nGoogleMap = googleMap;
        googleMap.setOnPoiClickListener(this);

        locationManager = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();

        nGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(39.178, -98.227)).zoom(4).build();
        nGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition((cameraPosition)));

        //handler for map location updating
        final android.os.Handler handler = new android.os.Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                updatePosition(false);
            }
        });

        mapReady = true;
    }

    @Override
    public void onPoiClick(PointOfInterest poi) {
        Toast.makeText(getContext(), "Clicked: " +
                        poi.name + "\nPlace ID:" + poi.placeId +
                        "\nLatitude:" + poi.latLng.latitude +
                        " Longitude:" + poi.latLng.longitude,
                Toast.LENGTH_SHORT).show();
        Log.d(TAG, poi.toString());
        com.example.mbankole.tripplanner.models.Location loc = new com.example.mbankole.tripplanner.models.Location(poi);
        LocationDetailFragment frag = LocationDetailFragment.newInstance(loc);
        frag.show(fm, "name");
    }


    public void updatePosition(boolean force) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    10);
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    11);
            updatePosition(false);
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        if ((latitude != lastLatitude || longitude != lastLongitude) || force) {
            // create marker
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(latitude, longitude)).title("Hello Maps");

            // Changing marker icon
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            // adding marker
            nGoogleMap.addMarker(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latitude, longitude)).zoom(12).build();
            nGoogleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            lastLatitude = latitude;
            lastLongitude = longitude;

            // Perform any camera updates here
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_map, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        if (mapReady) updatePosition(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



}