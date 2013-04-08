package com.mobileescort.mobileescort;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PresencaAdapter extends BaseAdapter{

	private Context ctx;
	private List<Presenca> presenca;
	
	public PresencaAdapter(Context ctx, List<Presenca> presenca){
		this.ctx = ctx;
		this.presenca = presenca;
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
		Presenca pre = presenca.get(position);
		
		//pegar a interface
		LayoutInflater inflater = 
				(LayoutInflater) ctx.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		
		View v = inflater.inflate(R.layout.activity_item_lista_presenca, null);
		
		//preencher a interface com o item
		TextView tvNome = (TextView) v.findViewById(R.id.txNome);
		tvNome.setText(pre.getNome());
		
		TextView tvTelefone = (TextView) v.findViewById(R.id.txTelefone);
		tvTelefone.setText(pre.getTelefone());
		
		return v;
	}
}
