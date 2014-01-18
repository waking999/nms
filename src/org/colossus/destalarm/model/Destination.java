package org.colossus.destalarm.model;

import com.google.android.gms.maps.model.LatLng;

public class Destination {
	public LatLng getLatlng() {
		return latlng;
	}
	public void setLatlng(LatLng latlng) {
		this.latlng = latlng;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private LatLng latlng;
	private String address;
	
	public Destination(LatLng latlng, String address){
		this.address = address;
		this.latlng = latlng;
	}
}
