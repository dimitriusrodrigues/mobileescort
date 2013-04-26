package com.mobileescort.mobileescort;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class TesteOverlay extends ItemizedOverlay {
private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
	
	public TesteOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	public void addOverlay(OverlayItem overlay) {
	    overlays.add(overlay);
	    populate();
	}
	
	public void clear() {
		overlays.clear();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return overlays.get(i);
	}

	@Override
	public int size() {
		return overlays.size();
	}

	@Override
	protected boolean onTap(int index) {
		
	  OverlayItem item = overlays.get(index);
	  Log.d("MAP", item.getTitle() + " - " + item.getSnippet());

	  return true;
	}
}
