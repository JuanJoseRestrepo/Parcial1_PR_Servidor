package model;

public class UserDisconnect {

	public String type = "UserDisconnect";
	private String id;
	
	public UserDisconnect() {
		
	}
	
	public UserDisconnect(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
}
