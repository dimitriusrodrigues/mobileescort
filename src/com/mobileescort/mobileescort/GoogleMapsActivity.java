package com.mobileescort.mobileescort;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;  
import android.support.v4.app.*;  
import com.google.android.gms.common.*;  
import com.google.android.gms.maps.*;  
import com.google.android.gms.maps.model.*;  
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import com.mobileescort.mobileescort.utils.DatabaseHandler;
import com.mobileescort.mobileescort.utils.SessionManager;
  
public class GoogleMapsActivity extends FragmentActivity {  
	
	// Alert dialog manager
		AlertDialogManager alert = new AlertDialogManager();
		
		// Session Manager Class
		SessionManager session;
	
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
			// TODO Auto-generated method stub
			SupportMapFragment fragment =  
				     (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);  
				    GoogleMap map = fragment.getMap();  
				  
				    //map.clear();
				    
				    LatLng latLng = new LatLng(-23.561706,-46.655981);  
				    map.addMarker(new MarkerOptions()  
				      .position(latLng)  
				      .icon(BitmapDescriptorFactory.fromResource(  
				        R.drawable.ic_launcher))  
				      .title("Av. Paulista")  
				      .snippet("São Paulo"));  
				    
				    LatLng latLng2 = new LatLng(-30.049436209186783,-51.22432905000005);  
				    map.addMarker(new MarkerOptions()  
				      .position(latLng2)  
				      .icon(BitmapDescriptorFactory.fromResource(  
				        R.drawable.ic_launcher))  
				      .title("Marcilio Dias 517")  
				      .snippet("Willian"));
				    
				    LatLng latLng3 = new LatLng(-30.035395009181585,-51.23696619999998);  
				    map.addMarker(new MarkerOptions()  
				      .position(latLng3)  
				      .icon(BitmapDescriptorFactory.fromResource(  
				        R.drawable.ic_launcher))  
				      .title("Demétrio Ribeiro 247")  
				      .snippet("Dimi"));
				    /*
				    LatLng latLng4 = new LatLng(location.getLatitude(),location.getLongitude());  
				    map.addMarker(new MarkerOptions()  
				      .position(latLng4)  
				      .icon(BitmapDescriptorFactory.fromResource(  
				        R.drawable.ic_launcher))  
				      .title("BUS")  
				      .snippet("Ponto móvel"));
				    */
				    DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
				    Usuario usuario =  dbh.getUsuario(session.getIdMotorista());
				    
				    usuario.setLatitude(-30.035395009181585);
				    usuario.setLongitude(-51.23696619999998);
				    dbh.update(usuario);

				    configuraPosicao(map, latLng3);
		}
	};
	
  protected void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  
    setContentView(R.layout.activity_google_maps);  
    
	// Session class instance
    session = new SessionManager(getApplicationContext());
   
    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	//locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 100, locationListener);    
  }  
  
  private void configuraPosicao(  
		  GoogleMap map, LatLng latLng) {  
		  map.moveCamera(  
		   CameraUpdateFactory.newLatLngZoom(latLng, 15));  
		  map.animateCamera(  
		   CameraUpdateFactory.zoomTo(10), 2000, null);  
		  
		  CameraPosition cameraPosition =   
		    new CameraPosition.Builder()  
		      .target(latLng)     
		      .zoom(15)       
		      .bearing(90)  
		      .tilt(45)  
		      .build();  
		  map.animateCamera(  
		    CameraUpdateFactory.newCameraPosition(  
		      cameraPosition));  
		}
  
  private void configuraPosicao2(  
		    GoogleMap map, LatLng latLng) {  
		  
		    map.setMapType(GoogleMap.MAP_TYPE_HYBRID);  
		    map.animateCamera(  
		     CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));  
		  }
  
}