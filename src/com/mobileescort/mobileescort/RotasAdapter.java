package com.mobileescort.mobileescort;

import java.util.List;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RotasAdapter extends BaseAdapter {

	private Context ctx;
	private List<Rotas> rotas;
	
	public RotasAdapter(Context ctx, List<Rotas> rotas){
		this.ctx = ctx;
		this.rotas = rotas;
	}
	
	@Override
	public int getCount() {
		return rotas.size();
	}

	@Override
	public Object getItem(int position) {
		return rotas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// pegar o item
		Rotas rot = rotas.get(position);
		
		//pegar a interface
		LayoutInflater inflater = 
				(LayoutInflater) ctx.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		
		View v = inflater.inflate(R.layout.activity_item_lista_rotas, null);
		
		//preencher a interface com o item
		TextView tvDescricao = (TextView) v.findViewById(R.id.txDescricao);
		tvDescricao.setText(rot.getDescricao());
		
		TextView tvCodigo = (TextView) v.findViewById(R.id.txCodigo);
		tvCodigo.setText(rot.getCodigo());
		
		return v;
	}
}
