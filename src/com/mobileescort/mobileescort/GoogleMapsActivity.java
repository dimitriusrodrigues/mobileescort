package com.mobileescort.mobileescort;

import android.os.Bundle;  
import android.support.v4.app.*;  
  
import com.google.android.gms.common.*;  
import com.google.android.gms.maps.*;  
import com.google.android.gms.maps.model.*;  
  
public class GoogleMapsActivity extends FragmentActivity {  
	
  protected void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  
    setContentView(R.layout.activity_google_maps);  
   
    SupportMapFragment fragment =  
     (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);  
    GoogleMap map = fragment.getMap();  
  
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
    
    configuraPosicao(map, latLng);
    configuraPosicao(map, latLng2);
    configuraPosicao(map, latLng3);
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
