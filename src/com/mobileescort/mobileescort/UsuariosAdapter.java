package com.mobileescort.mobileescort;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UsuariosAdapter extends BaseAdapter{
	
	private Context ctx;
	private List<Usuarios> usuarios;
	
	public UsuariosAdapter(Context ctx, List<Usuarios> usuarios){
		this.ctx = ctx;
		this.usuarios = usuarios;
	}
	
	@Override
	public int getCount() {
		return usuarios.size();
	}

	@Override
	public Object getItem(int position) {
		return usuarios.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// pegar o item
		Usuarios usu = usuarios.get(position);
		
		//pegar a interface
		LayoutInflater inflater = 
				(LayoutInflater) ctx.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		
		View v = inflater.inflate(R.layout.activity_item_lista_usuarios, null);
		
		//preencher a interface com o item
		TextView tvNome = (TextView) v.findViewById(R.id.txNome);
		tvNome.setText(usu.getNome());
		
		TextView tvTelefone = (TextView) v.findViewById(R.id.txTelefone);
		tvTelefone.setText(usu.getTelefone());
		
		return v;
	}

}
