package com.mobileescort.mobileescort;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class PresencaActivity extends Activity {

	ListView lvPresenca;
	
	OnItemClickListener onItemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
			
			Presenca presenca = (Presenca) adapter.getItemAtPosition(position);
			
			Toast.makeText(getBaseContext(), presenca.getNome(), Toast.LENGTH_SHORT).show();
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_presenca);
		
		lvPresenca = (ListView) findViewById(R.id.listView1);
		
		adapterBase();
	}

	private void adapterBase(){
		
		List<Presenca> listPresenca = new ArrayList<Presenca>();
		
		Presenca pre1 = new Presenca();
		pre1.setNome("Ana Rocha");
		pre1.setTelefone("99811234");
		listPresenca.add(pre1);
		
		Presenca pre2 = new Presenca();
		pre2.setNome("Bia Falcão");
		pre2.setTelefone("92343564");
		listPresenca.add(pre2);
		
		Presenca pre3 = new Presenca();
		pre3.setNome("Carlos Novaes");
		pre3.setTelefone("93358626");
		listPresenca.add(pre3);
		
		Presenca pre4 = new Presenca();
		pre4.setNome("Dimitrius R.");
		pre4.setTelefone("84489104");
		listPresenca.add(pre4);
		
		Presenca pre5 = new Presenca();
		pre5.setNome("Maria Aparecida");
		pre5.setTelefone("99654543");
		listPresenca.add(pre5);
		
		Presenca pre6 = new Presenca();
		pre6.setNome("Willian Dufau");
		pre6.setTelefone("93341995");
		listPresenca.add(pre6);
		
		//adapter
		PresencaAdapter adapter = new PresencaAdapter(getBaseContext(), listPresenca);
		lvPresenca.setAdapter(adapter);
		
		lvPresenca.setOnItemClickListener(onItemClickListener);
	}


}
