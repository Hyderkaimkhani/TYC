package com.example.hyder.tuc;

import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by hv on 2/26/16.
 */
public class MapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,LocationListener
        {

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
  //  private final long interval = 30000;
    private LocationRequest mLocationRequest;
    private String Address = "15 Bath Road, Hounslow, United Kingdom";
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;


    private final int[] MAP_TYPES = { GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE };
    private int curMapTypeIndex = 1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(getActivity(),"Fragment",Toast.LENGTH_SHORT).show();
        setHasOptionsMenu(true);

        mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();
        mGoogleApiClient.connect();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
        initListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

      LatLng location=getLocationFromAddress(getActivity(),Address);
        double lat = location.latitude;
        double lon = location.longitude;

        Location newLoc = new Location("");
        newLoc.setLatitude(lat);
        newLoc.setLongitude(lon);

        initCamera(newLoc);
    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    private void initListeners() {
        getMap().setOnMarkerClickListener(this);
        getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener( this );
        getMap().setOnMapClickListener(this);
    }
    private void initCamera( Location location ) {
        CameraPosition position = CameraPosition.builder()
                .target( new LatLng( location.getLatitude(),
                        location.getLongitude() ) )
                .zoom( 16f )
                .bearing( 0.0f )
                .tilt( 0.0f )
                .build();

        getMap().animateCamera( CameraUpdateFactory
                .newCameraPosition( position ), null );

        getMap().setMapType( MAP_TYPES[curMapTypeIndex] );
        getMap().setTrafficEnabled( true );
        getMap().setMyLocationEnabled( true );
        getMap().getUiSettings().setZoomControlsEnabled( true );

        MarkerOptions marker = new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude()));
        marker.title( getAddressFromLatLng(new LatLng(location.getLatitude(),location.getLongitude())) );

        marker.icon( BitmapDescriptorFactory.defaultMarker());
                getMap().addMarker( marker );
    }

            private void CameraUpdate( Location location ) {
                CameraPosition position = CameraPosition.builder()
                        .target( new LatLng( location.getLatitude(),
                                location.getLongitude() ) )
                        .zoom( 16f )
                        .bearing( 0.0f )
                        .tilt( 0.0f )
                        .build();

                getMap().animateCamera( CameraUpdateFactory
                        .newCameraPosition( position ), null );

                getMap().setMapType( MAP_TYPES[curMapTypeIndex] );
                getMap().setTrafficEnabled( true );
                getMap().setMyLocationEnabled( true );
                getMap().getUiSettings().setZoomControlsEnabled( true );
            }

    @Override
    public void onConnected(Bundle bundle) {

            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mCurrentLocation == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
            else {
                initCamera( mCurrentLocation );
            }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position( latLng );
        options.title( getAddressFromLatLng( latLng ) );

        options.icon( BitmapDescriptorFactory.defaultMarker() );
        getMap().addMarker( options );

    }
    private String getAddressFromLatLng( LatLng latLng ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String address = "";
        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );
        } catch (IOException e ) {
        }

        return address;
    }

            public LatLng getLocationFromAddress(Context context, String strAddress) {

                Geocoder coder = new Geocoder(context);
                List<Address> address;
                LatLng p1 = null;

                try {
                    address = coder.getFromLocationName(strAddress, 5);
                    if (address == null) {
                        return null;
                    }
                    Address location = address.get(0);
                    location.getLatitude();
                    location.getLongitude();

                    p1 = new LatLng(location.getLatitude(), location.getLongitude() );

                } catch (Exception ex) {

                    ex.printStackTrace();
                }

                return p1;
            }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
            initCamera(location);
        }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
           if (connectionResult.hasResolution()) {
               try {
                        // Start an Activity that tries to resolve the error
                 connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
               } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
           } else {
                    Log.i("", "Location services connection failed with code " + connectionResult.getErrorCode());
                }
    }
}
