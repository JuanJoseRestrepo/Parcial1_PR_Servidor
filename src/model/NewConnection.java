package model;

import java.util.ArrayList;

public class NewConnection {

	public String type = "NewConnection";
	private String id;
	private String body;
	private String date;
	private ArrayList<NewConnection> usuariosEnviados;
	
	public NewConnection() {
		
	}
	
	public NewConnection(String id,String body,String date) {
		this.id = id;
		this.body = body;
		this.date = date;
		usuariosEnviados = new ArrayList<NewConnection>();
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getType() {
		return type;
	}
	
	public ArrayList<NewConnection> getUsuariosEnviados() {
		return usuariosEnviados;
	}
	public void setUsuariosEnviados(ArrayList<NewConnection> usuariosEnviados) {
		this.usuariosEnviados = usuariosEnviados;
	}
	
	
}
