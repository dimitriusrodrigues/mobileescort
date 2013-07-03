package com.mobileescort.mobileescort;

import android.content.Intent;
import android.os.Bundle;  
import android.os.Handler;
import android.support.v4.app.*;  
import com.google.android.gms.maps.*;  
import com.google.android.gms.maps.model.*;  
import com.mobileescort.mobileescort.model.Viagem;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
  
public class GoogleMapsActivityUsuario extends FragmentActivity implements Runnable {  
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	int id_rota;
	int id_viagem;
	Viagem viagem;
	Handler handler = new Handler();
	SupportMapFragment fragment;
    GoogleMap map;	
    private double [][] coordenadas = new double[][] {
    			{ -30.035290, -51.237069 },
    			{ -30.035262, -51.236565 },
    			{ -30.035476, -51.236286 },
    			{ -30.035847, -51.236243 },
    			{ -30.036005, -51.235793 },
    			{ -30.036117, -51.235063 },
    			{ -30.036275, -51.234398 },
    			{ -30.036451, -51.233690 },
    			{ -30.036581, -51.233003 },
    			{ -30.037045, -51.232831 },
    			{ -30.037593, -51.232874 },
    			{ -30.038151, -51.232982 },
    			{ -30.038699, -51.233003 },
    			{ -30.039470, -51.233025 },
    			{ -30.039962, -51.233068 },
    			{ -30.040501, -51.233153 },
    			{ -30.041067, -51.233228 },
    			{ -30.041727, -51.233250 },
    			{ -30.042135, -51.233497 },
    			{ -30.042646, -51.233518 },
    			{ -30.042971, -51.233003 },
    			{ -30.043463, -51.232542 },
    			{ -30.044076, -51.232392 },
    			{ -30.044819, -51.232467 },
    			{ -30.045516, -51.232531 },
    			{ -30.046194, -51.232563 },
    			{ -30.046760, -51.232628 },
    			{ -30.047030, -51.232005 },
    			{ -30.047169, -51.231072 },
    			{ -30.047290, -51.230010 },
    			{ -30.047429, -51.228937 },
    			{ -30.047559, -51.227778 },
    			{ -30.047652, -51.226544 },
    			{ -30.047754, -51.225311 },
    			{ -30.047810, -51.224141 },
    			{ -30.048135, -51.224002 },
    			{ -30.048599, -51.224087 },
    			{ -30.049231, -51.224173 },
    			{ -30.049407, -51.224345 },
    			{ -30.049388, -51.224549 },


    			{ -30.049459, -51.224334 },
    			{ -30.049338, -51.224120 },
    			{ -30.048827, -51.224050 },
    			{ -30.048358, -51.223975 },
    			{ -30.048395, -51.223420 },
    			{ -30.048442, -51.222816 },
    			{ -30.048511, -51.222092 },
    			{ -30.048548, -51.221357 },
    			{ -30.048572, -51.220622 },
    			{ -30.048572, -51.219941 },
    			{ -30.048488, -51.219152 },
    			{ -30.048367, -51.218433 },
    			{ -30.048302, -51.217693 },
    			{ -30.048172, -51.216803 },
    			{ -30.048065, -51.216014 },
    			{ -30.047963, -51.215032 },
    			{ -30.047875, -51.214233 },
    			{ -30.047736, -51.213348 },
    			{ -30.047504, -51.212559 },
    			{ -30.047169, -51.211835 },
    			{ -30.046686, -51.211084 },
    			{ -30.046241, -51.210365 },
    			{ -30.045841, -51.209636 },
    			{ -30.045395, -51.208885 },
    			{ -30.044945, -51.208161 },
    			{ -30.044467, -51.207356 },
    			{ -30.044026, -51.206573 },
    			{ -30.043673, -51.205891 },
    			{ -30.043496, -51.205055 },
    			{ -30.043524, -51.204175 },
    			{ -30.043543, -51.203333 },
    			{ -30.043543, -51.202485 },
    			{ -30.043552, -51.201605 },
    			{ -30.043594, -51.200827 },
    			{ -30.043877, -51.200178 },
    			{ -30.044313, -51.199733 },
    			{ -30.044694, -51.199465 },
    			{ -30.045214, -51.199121 },
    			{ -30.045711, -51.198784 },
    			{ -30.046231, -51.198365 },
    			{ -30.046770, -51.197973 },
    			{ -30.047309, -51.197603 },
    			{ -30.047912, -51.197196 },
    			{ -30.048465, -51.196847 },
    			{ -30.048952, -51.196493 },
    			{ -30.049398, -51.196166 },
    			{ -30.049895, -51.195855 },
    			{ -30.050359, -51.195522 },
    			{ -30.050828, -51.195184 },
    			{ -30.051251, -51.194867 },
    			{ -30.051734, -51.194535 },
    			{ -30.052198, -51.194202 },
    			{ -30.052672, -51.193838 },
    			{ -30.053052, -51.193537 },
    			{ -30.053433, -51.193199 },
    			{ -30.053795, -51.192797 },
    			{ -30.054148, -51.192201 },
    			{ -30.054492, -51.191611 },
    			{ -30.054794, -51.190989 },
    			{ -30.055040, -51.190469 },
    			{ -30.055286, -51.189873 },
    			{ -30.055602, -51.189278 },
    			{ -30.055848, -51.188784 },
    			{ -30.056163, -51.188200 },
    			{ -30.056428, -51.187609 },
    			{ -30.056711, -51.187068 },
    			{ -30.057022, -51.186424 },
    			{ -30.057301, -51.185893 },
    			{ -30.057566, -51.185378 },
    			{ -30.057863, -51.184836 },
    			{ -30.058132, -51.184321 },
    			{ -30.058443, -51.183656 },
    			{ -30.058694, -51.183050 },
    			{ -30.058940, -51.182443 },
    			{ -30.059130, -51.181768 },
    			{ -30.059237, -51.180990 },
    			{ -30.059246, -51.180142 },
    			{ -30.059149, -51.179305 },
    			{ -30.058893, -51.178554 },
    			{ -30.058629, -51.177787 },
    			{ -30.058285, -51.177058 },
    			{ -30.057960, -51.176301 },
    			{ -30.057631, -51.175518 },
    			{ -30.057310, -51.174783 }
    			};

    		
    private int indice = 0;
    
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.activity_google_maps_usuario);  
		Intent intent = getIntent();
		Bundle params = intent.getExtras();  
		if(params!=null) { 
			id_rota = params.getInt("id_rota");
			id_viagem = params.getInt("id_viagem");
			viagem = Login.repositorio.buscarViagem(id_rota);
		} else {
			finish();
		}
        
	    fragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);  
	    GoogleMap map = fragment.getMap();  
   
	    //LatLng latLng = new LatLng(viagem.getLatitude(),viagem.getLongitude());
	    double latitude = coordenadas[indice][0];
		double longitude = coordenadas[indice][1];
		LatLng latLng = new LatLng(latitude, longitude);
	       
	    map.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.notificacao)).title("BUS").snippet("Ponto móvel"));

	    configuraPosicao(map, latLng);
	    indice++;
	    handler.postDelayed(this, 2000);
	}  
  
	private void configuraPosicao(GoogleMap map, LatLng latLng) {  
			  
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng, 15));  
		map.animateCamera( CameraUpdateFactory.zoomTo(10), 2000, null);  
			  
		CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).bearing(90).tilt(45).build();  
		map.animateCamera( CameraUpdateFactory.newCameraPosition(cameraPosition));  
	}

	@Override
	public void run() {
		
		
		viagem = Login.repositorio.buscarViagem();
		if (viagem == null) {
			finish();
		}
		map = fragment.getMap();
		map.clear();
		//LatLng latLng = new LatLng(viagem.getLatitude(),viagem.getLongitude());
		double latitude = coordenadas[indice][0];
		double longitude = coordenadas[indice][1];
		LatLng latLng = new LatLng(latitude, longitude);
		map.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.notificacao)).title("BUS").snippet("Ponto móvel"));
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng, 15));
		CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).bearing(90).tilt(45).build();  
		map.animateCamera( CameraUpdateFactory.newCameraPosition(cameraPosition));
		indice++;
		if (indice == coordenadas.length) {
			finish(); 
		} else {
			handler.postDelayed(this, 2000);
		}
		
	}
  
}