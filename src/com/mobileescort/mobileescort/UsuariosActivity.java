package com.mobileescort.mobileescort;

import java.util.ArrayList;
import java.util.List;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.model.Viagem;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UsuariosActivity extends Activity {

	ListView lvUsuarios;
	Button btCadUsuario;
	Button btIniciarRota;
	int id_rota;
	int id_viagem;
	Viagem viagem;
	
	// Alert dialog manager
	//AlertDialogManager alert = new AlertDialogManager();
	
	OnItemClickListener onItemClickListener = new OnItemClickListener(){
		
		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

			Usuario usuario = (Usuario) adapter.getItemAtPosition(position);
			desvincularUsuariodaRota(usuario);
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		Bundle params = intent.getExtras();  
		if(params!=null) { 
			id_rota = params.getInt("id_rota");
		}
		
		setContentView(R.layout.activity_lista_usuarios);
		
		
		lvUsuarios = (ListView) findViewById(R.id.listView1);
		
		//adapterBase();
		
		btCadUsuario = (Button) findViewById(R.id.btCadUsuario);
        
		btCadUsuario.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(UsuariosActivity.this,CadastroUsuario.class);
				Bundle params = new Bundle();
                params.putString("origem", "UsuariosActivity");
                params.putInt("id_rota", id_rota);
                it.putExtras(params);
				startActivity(it);
			}
		});
		
		btIniciarRota = (Button) findViewById(R.id.btIniciarRota);
        
		btIniciarRota.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				iniciarRota();
			}
		});
		
	}
	
	public void iniciarRota(){
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    dialog.setIcon(R.drawable.ic_launcher);
	    dialog.setTitle("Iniciar Rota");
	    dialog.setMessage("Deseja iniciar rota?");
	            
	    dialog.setNegativeButton("Não", new DialogInterface.OnClickListener(){
	    	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(getBaseContext(), "Rota não inicianda!", Toast.LENGTH_SHORT).show();	
			}
	    });
		
	    dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
	    	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				if (!viagemIniciada()){
					viagem = Login.repositorio.buscarViagem(id_rota);
					if (viagem == null) {
						viagem = new Viagem();
						viagem.setId_rota(id_rota);
						viagem.setId_status("Iniciada");
						setId_viagem(Login.repositorio.salvarViagem(viagem));
						viagem.setId_viagem(getId_viagem());
					} else {
						setId_viagem(viagem.getId_viagem());
					}
					
					Intent it = new Intent(UsuariosActivity.this,GoogleMapsActivity.class);
					it.putExtra("id_viagem", viagem.getId_viagem());
					it.putExtra("id_rota", viagem.getId_rota());
					startActivity(it);	
					
				} else {
					if (viagem.getId_rota() == id_rota){
						Intent it = new Intent(UsuariosActivity.this,GoogleMapsActivity.class);
						it.putExtra("id_viagem", viagem.getId_viagem());
						it.putExtra("id_rota", viagem.getId_rota());
						startActivity(it);
					}else {
						if (finalizaViagem()) {
							Toast.makeText(getBaseContext(), "Rota finalizada!", Toast.LENGTH_SHORT).show();
							viagem = Login.repositorio.buscarViagem(id_rota);
							if (viagem == null) {
								viagem = new Viagem();
								viagem.setId_rota(id_rota);
								viagem.setId_status("Iniciada");
								setId_viagem(Login.repositorio.salvarViagem(viagem));
								viagem.setId_viagem(getId_viagem());
								Intent it = new Intent(UsuariosActivity.this,GoogleMapsActivity.class);
								it.putExtra("id_viagem", viagem.getId_viagem());
								it.putExtra("id_rota", viagem.getId_rota());
								startActivity(it);	
							}
						}else				{
							Toast.makeText(getBaseContext(), "Não foi possível finalizar a rota!", Toast.LENGTH_SHORT).show();
						}	
					}
				}
			}
	    });
	    dialog.show();
	}
	
	private void adapterBase(){
		
		List<Usuario> listUsuarios = new ArrayList<Usuario>();
		
		try{

	        if (!Login.session.checkLogin()) {
	        	Login.alert.showAlertDialog(UsuariosActivity.this,
	      					"Session Failed","Id do Motorista não encontrado..", false);
	        	finish();
	        }
	    
	        Rota rota = Login.repositorio.buscarRota(id_rota);
	        listUsuarios = rota.getUsuarios();
			
		 } catch (Exception e) {
        	 Login.alert.showAlertDialog(UsuariosActivity.this,
     					"Tried List User Failed",
     					e.getMessage(), false);
		}
		//adapter
		UsuariosAdapter adapter = new UsuariosAdapter(getBaseContext(), listUsuarios);
		lvUsuarios.setAdapter(adapter);
		
		lvUsuarios.setOnItemClickListener(onItemClickListener);
		
	}

	protected void onStart() {
		super.onStart();
		adapterBase();
		/*
		if (viagemIniciada()){
			viagem = Login.repositorio.buscarViagem(id_rota);
			if (viagem != null)	{
				Intent it = new Intent(UsuariosActivity.this,GoogleMapsActivity.class);
				it.putExtra("id_rota", id_rota);
				it.putExtra("id_viagem", id_viagem);
				startActivity(it);
			}
		}*/				
	}

	/**
	 * @return the id_viagem
	 */
	private int getId_viagem() {
		return id_viagem;
	}

	/**
	 * @param id_viagem the id_viagem to set
	 */
	private void setId_viagem(int id_viagem) {
		this.id_viagem = id_viagem;
	}	

	/**
	 * @param id_rota the id_rota to set
	 */
	public boolean finalizaViagem() {
		int count;
		count = Login.repositorio.deletarViagem(this.getId_viagem());
		
		if (count == 1) {
			this.setId_viagem(0);
			return true;
		}
		return false;
	}
	
	public boolean viagemIniciada() {
		if (getId_viagem() > 0) {
			return true;
		}
		return false;
	}
	
	public void desvincularUsuariodaRota(final Usuario removerUsuario){
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    dialog.setIcon(R.drawable.ic_launcher);
	    dialog.setTitle("Desvincular Usuário");
	    dialog.setMessage(getString(R.string.remove_user));
	            
	    dialog.setNegativeButton("Não", new DialogInterface.OnClickListener(){
	    	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
					
			}
	    });
		
	    dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
	    	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				long count;
				
				RotaREST rotaRest = new RotaREST();
				
				try{
					String retorno = rotaRest.deletarUsuarioRota(getId_viagem(),removerUsuario.getId_usuario());
					Login.alert.showAlertDialog(UsuariosActivity.this, "Removeu", "Retorno : " + retorno, true);
				} catch (Exception e) {
		        	 Login.alert.showAlertDialog(UsuariosActivity.this,
		     					"List Rotas Failed",
		     					e.getMessage(), false);
				}
				
				count = Login.repositorio.deletarRotaUsuario(getId_viagem(),removerUsuario.getId_usuario());
				if (count == 1) {
					Login.alert.showAlertDialog(UsuariosActivity.this, "Remover", "Usuário removido da rota com sucesso.", true);
				} else {
					Login.alert.showAlertDialog(UsuariosActivity.this, "Removido", "Não foi possível remover usuário da rota.", false);
				}
			}
	    });
	    dialog.show();
	}	

}
