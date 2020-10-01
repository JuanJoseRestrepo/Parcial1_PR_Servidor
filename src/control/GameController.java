package control;

import comm.Receptor.OnMessageListener;
import comm.Session;
import javafx.application.Platform;
import model.CheckTile;
import model.DirectMessage;
import model.Generic;
import model.Jugador;
import model.Message;
import model.NewConnection;
import model.UserDisconnect;
import model.UserMessage;


import com.google.gson.Gson;

import comm.TCPConnection;
import view.ChadWindow;
import comm.TCPConnection.OnConnectionListener;

public class GameController implements OnMessageListener, OnConnectionListener {

	
	private ChadWindow view;
	private TCPConnection connection;
	
	public GameController(ChadWindow view) {
		this.view = view;
		init();
	}
	
	public void init() {
		connection = TCPConnection.getInstance();
		connection.setPuerto(5000);
		connection.start();
		connection.setConnectionListener(this);
		connection.setMessageListener(this);
	}

	@Override
	public void onMessage(Session s,String message) {
		//
		Gson gson = new Gson();
		Generic type = gson.fromJson(message,Generic.class);

		switch(type.getType()) {
			
		case "UserMessage":
			
			UserMessage userM = gson.fromJson(message, UserMessage.class);
			
			s.setUserName(userM.getId());
			connection.addUser(s, userM.getId());
			break;
			
		case "CheckTile":
			
			CheckTile newTile = gson.fromJson(message, CheckTile.class);
			String body = Integer.toString(newTile.getCasilla());
			Message normalM = new Message(newTile.getId(),body,"");
			String jsonn = gson.toJson(normalM);
			
			
			connection.sendDirectMessage(newTile.getId(), jsonn);
			
			break;
			
		case "UserDisconnect":
	
			UserDisconnect looser = gson.fromJson(message, UserDisconnect.class);
			String jsons = gson.toJson(looser);
			
			connection.sendDirectMessage(looser.getId(), jsons);
			
			break;

		}
	}

	@Override
	public void onConnection(String id) {
	Platform.runLater(
				
				()->{
					
					view.getMessageArea().appendText("<<< Nuevo cliente conectado!:" + id + ">>>\n");
					
				}
				
				
				);
	}

	@Override
	public void onDisconection(Session s) {
		
		connection.getSalaDeEspera().remove(s);

	}

	@Override
	public void OnRepeatConnection() {
		
	Platform.runLater(
				
				()->{
					
					view.getMessageArea().appendText("<<< Ya hay mas de dos usuarios,espera tu turno:" + ">>>\n");
				}
				
				
				);
	}
	
	
	
	
	}
