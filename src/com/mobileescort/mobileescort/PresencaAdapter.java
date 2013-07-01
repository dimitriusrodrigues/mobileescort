package com.mobileescort.mobileescort;

import java.util.List;

import com.mobileescort.mobileescort.model.Usuario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PresencaAdapter extends BaseAdapter{

	private Context ctx;
	private List<Usuario> presenca;
	private List<String> presente;
	
	public PresencaAdapter(Context ctx, List<Usuario> presenca, List<String> presente){
		this.ctx = ctx;
		this.presenca = presenca;
		this.presente = presente;
	}
	
	@Override
	public int getCount() {
		return presenca.size();
	}

	@Override
	public Object getItem(int position) {
		return presenca.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// pegar o item
		Usuario usu = presenca.get(position);
		String status = presente.get(position);
		
		//pegar a interface
		LayoutInflater inflater = 
				(LayoutInflater) ctx.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		
		View v = inflater.inflate(R.layout.activity_item_lista_presenca, null);
		
		//preencher a interface com o item
		TextView tvNome = (TextView) v.findViewById(R.id.txNome);
		String perfil;
		if (usu.getPerfil().equals("R") || usu.getPerfil().equals("P")){ 
			perfil = ctx.getString(R.string.perfil_responsavel);
		} else {
			perfil = ctx.getString(R.string.perfil_usuario);
		}
			
		tvNome.setText(perfil + ":" +usu.getNome());
		
		TextView tvTelefone = (TextView) v.findViewById(R.id.txTelefone);
		tvTelefone.setText(status);
		
		return v;
	}
}
