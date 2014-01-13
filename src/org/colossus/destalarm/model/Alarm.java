package org.colossus.destalarm.model;

public class Alarm {
	private long id;
	private String label;
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
		return label;
	}
}
