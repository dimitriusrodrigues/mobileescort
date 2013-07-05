package com.mobileescort.mobileescort;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;  
import android.support.v4.app.*;  
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.google.android.gms.maps.*;  
import com.google.android.gms.maps.model.*;  
import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.model.Viagem;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import com.mobileescort.mobileescort.utils.SessionManager;
  
public class GoogleMapsActivity extends FragmentActivity {  
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	int id_rota;
	int id_viagem;
	Viagem viagem;
	Rota rota;
    RotaREST rotaRest = new RotaREST();
    ImageButton btFinalizar;
	
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
		    List<Usuario> listUsuarios = new ArrayList<Usuario>();
		    listUsuarios = rota.getUsuarios();
		    Usuario usuario;
		    
		    for (int i = 0; i < listUsuarios.size(); i++) {
		    	usuario = listUsuarios.get(i);
		    	if ( (usuario.getLatitude() != 0.0 ) && (usuario.getLongitude() != 0.0 ) ) { 
			    	map.addMarker(new MarkerOptions()  
				      .position(new LatLng(usuario.getLatitude(),usuario.getLongitude()))  
				      .icon(BitmapDescriptorFactory.fromResource(  
				        R.drawable.casa))  
				      .title(usuario.getNome())  
				      .snippet(usuario.getCidade()));
		    	}	
		    }
		    if (viagem.getLatitude() != location.getLatitude() ||
		    	viagem.getLatitude() != location.getLatitude()	) {
		    	
		    	viagem.setLatitude(location.getLatitude());
			    viagem.setLongitude(location.getLongitude());
		    	id_viagem = Login.repositorio.salvarViagem(viagem);
				try {
					rotaRest.enviarMensagem(viagem.getId_rota(), SessionManager.MSG_ATUALIZA_POSICAO+viagem.getId_rota()+":"+viagem.getLatitude()+","+viagem.getLongitude());
				} catch (Exception e) {
		        	 alert.showAlertDialog(GoogleMapsActivity.this,
		     					getString(R.string.title_msg_notificationsend),
		     					getString(R.string.body_msg_notificationfailed)+ " " + e.getMessage(), false);
				}
		    }
		    
		    LatLng latLng = new LatLng(viagem.getLatitude(),viagem.getLongitude());
		    
		    map.addMarker(new MarkerOptions()  
		      .position(latLng)  
		      .icon(BitmapDescriptorFactory.fromResource(  
		        R.drawable.notificacao))  
		      .title("BUS")  
		      .snippet("Ponto móvel"));
		 
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
		rota = Login.repositorio.buscarRota(id_rota);
	} else {
		finish();
	}
	
	btFinalizar = (ImageButton) findViewById(R.id.ibFinalizar);
    
	btFinalizar.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			finalizarRota(id_viagem);
		}
	});
   
    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 500, locationListener);    
  }  
  
  private void configuraPosicao( GoogleMap map, LatLng latLng) {  
	  map.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng, 15));  
	  map.animateCamera( CameraUpdateFactory.zoomTo(10), 2000, null);  
	  CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng)     
		      .zoom(15)       
		      .bearing(90)  
		      .tilt(45)  
		      .build();  
	  
	  map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));  
  }
  
  private void finalizarRota(int id_viagem) {
	  try {
			rotaRest.enviarMensagem(viagem.getId_rota(), SessionManager.MSG_ATUALIZA_ROTAFINALIZADA+viagem.getId_viagem());
			Login.repositorio.deletarViagem(id_viagem);
			finish();
		} catch (Exception e) {
      	 alert.showAlertDialog(GoogleMapsActivity.this,
   					getString(R.string.title_msg_notificationsend),
   					getString(R.string.body_msg_notificationfailed)+" "+e.getMessage(), false);
		}
  }
  
}