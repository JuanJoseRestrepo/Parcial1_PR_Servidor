package model;

public class Jugador {


	public String type = "Jugador";
	private String id;
	private int casilla;
	private String clientId;
	
	public Jugador(String id,  int casilla,String clientId) {
		super();
		this.id = id;
		this.casilla = casilla;
		this.clientId = clientId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	
	
	public int getCasilla() {
		return casilla;
	}

	public void setCasilla(int casilla) {
		this.casilla = casilla;
	}

	public String getType() {
		return type;
	}
	
	
}
