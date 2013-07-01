package com.mobileescort.mobileescort;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;  
import android.support.v4.app.*;  
import com.google.android.gms.maps.*;  
import com.google.android.gms.maps.model.*;  
import com.mobileescort.mobileescort.model.Viagem;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import com.mobileescort.mobileescort.utils.SessionManager;
  
public class GoogleMapsActivityUsuario extends FragmentActivity {  
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	int id_rota;
	int id_viagem;
	Viagem viagem;
	
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
		    
	    	//viagem.setLatitude(location.getLatitude());
		    //viagem.setLongitude(location.getLongitude());
		    
		    LatLng latLng = new LatLng(viagem.getLatitude(),viagem.getLongitude());
		    
		    map.addMarker(new MarkerOptions()  
		      .position(latLng)  
		      .icon(BitmapDescriptorFactory.fromResource(  
		        R.drawable.notificacao))  
		      .title("BUS")  
		      .snippet("Ponto m�vel"));
		 
		    configuraPosicao(map, latLng);
				    
				    
		}
	};
	
  protected void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  
    setContentView(R.layout.activity_google_maps);  
	Intent intent = getIntent();
	Bundle params = intent.getExtras();  
	if(params!=null) { 
		id_rota = params.getInt("id_rota");
		id_viagem = params.getInt("id_viagem");
		viagem = Login.repositorio.buscarViagem(id_rota);
	} else {
		finish();
	}
    
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
  
}