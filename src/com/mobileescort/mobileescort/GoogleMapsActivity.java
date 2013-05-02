package com.mobileescort.mobileescort;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.mobileescort.mobileescort.R;
import com.mobileescort.mobileescort.TesteOverlay;

public class GoogleMapsActivity extends MapActivity  {
	private MapView mapView ;
	TesteOverlay overlays;
	
	private LocationListener locationListener = new LocationListener() {
	
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onLocationChanged(Location location) {
			
			Log.d("loc", "lat = " + location.getLatitude());
			Log.d("loc", "lon = " + location.getLongitude());
			
			
			GeoPoint geo = new GeoPoint((int)(location.getLatitude() * 1E6),(int)(location.getLongitude() * 1E6));
			
	        int lat2 = (int) (-30.049436209186783 * 1E6);
	        int lon2 = (int) (-51.22432905000005 * 1E6);
			GeoPoint geo2 = new GeoPoint(lat2, lon2);
			
			int lat3 = (int) (-30.035395009181585 * 1E6);
	        int lon3 = (int) (-51.23696619999998 * 1E6);
			GeoPoint geo3 = new GeoPoint(lat3, lon3);
	        
			mapView.getController().animateTo(geo);

	        //mapView.getOverlays().clear();
	        
	        overlays.clear();
	        OverlayItem item = new OverlayItem(geo, "NOVA COORDENADA", "Essa veio do GPS");
	        OverlayItem item2 = new OverlayItem(geo2, "NOVA COORDENADA", "Essa veio do GPS");
	        OverlayItem item3 = new OverlayItem(geo3, "NOVA COORDENADA", "Essa veio do GPS");
	        overlays.addOverlay(item);
	        overlays.addOverlay(item2);
	        overlays.addOverlay(item3);
	        
	        
	        mapView.getOverlays().add(overlays);
			
		}
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
//        int lat = (int) (-30 * 1E6);
//        int lon = (int) (-51.2 * 1E6);
        
//        Log.d("GEO", lat + " - " + lon);
        
//        GeoPoint geo = new GeoPoint(lat, lon);
        
//        mapView.getController().animateTo(geo);
        
        mapView.setSatellite(false);
//        mapView.setStreetView(true);
        
        // overlays
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
        overlays = new TesteOverlay(drawable);
        
//        GeoPoint pointOverlay = new GeoPoint(lat, lon);
//        OverlayItem item = new OverlayItem(pointOverlay, "Centro do Universo", "diria o bairrista...");
//        overlays.addOverlay(item);
        
//        mapView.getOverlays().add(overlays);
        
        
        // Ligando o GPS
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, locationListener);
		
		Location location = locationManager.getLastKnownLocation("gps");
        
		if (location != null) {
			GeoPoint geo = new GeoPoint((int)(location.getLatitude() * 1E6),(int)(location.getLongitude() * 1E6));
			mapView.getController().animateTo(geo);
		}
		
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}


