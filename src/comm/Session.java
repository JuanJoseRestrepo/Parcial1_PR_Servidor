package comm;

import java.io.IOException;
import java.net.Socket;

import comm.Receptor.OnMessageListener;

public class Session {
	
	private String userName;
	private Socket socket;
	private Receptor receptor;
	private Emisor emisor;
	private OnMessageListener listener;
	
	public Session(Socket socket) {
		this.socket = socket;
		try {
			this.userName = userName;
			emisor = new Emisor(socket.getOutputStream());
			receptor = new Receptor(this,socket.getInputStream());
			receptor.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeSocket() {
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public Emisor getEmisor() {
		return this.emisor;
		
	}
	
	public Receptor getReceptor() {
		return this.receptor;
	}
	
}
