package model;

import java.io.Serializable;
import java.util.List;

public class BartenderQuestion implements Serializable {
	private List<String> dish;
	private List<String> purpose;
	private List<String> type;
	private List<String> grape;
	private List<String> place;
	
	public List<String> getDish() {
		return dish;
	}
	public void setDish(List<String> dish) {
		this.dish = dish;
	}
	public List<String> getPurpose() {
		return purpose;
	}
	public void setPurpose(List<String> purpose) {
		this.purpose = purpose;
	}
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
	public List<String> getGrape() {
		return grape;
	}
	public void setGrape(List<String> grape) {
		this.grape = grape;
	}
	public List<String> getPlace() {
		return place;
	}
	public void setPlace(List<String> place) {
		this.place = place;
	}
	
}
