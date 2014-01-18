package org.colossus.destalarm.model;

import org.colossus.destalarm.util.Util;

public class Alarm {
	private long id;
	private String label;
	private Destination dest;
	
	public Destination getDest() {
		return dest;
	}
	public void setDest(Destination dest) {
		this.dest = dest;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String toString(){
		if(!Util.isStringBlank(label)){
			return label;
		}else{
			return dest.getAddress();
		}
	}
}
