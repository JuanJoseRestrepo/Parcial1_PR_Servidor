package comm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import model.Message;
import model.NewConnection;
import model.UserDisconnect;
import model.UserMessage;


public class TCPConnection extends Thread {


	private static TCPConnection instance = null;
	
	
	//GLOBAL
	private int puerto;
	private ServerSocket server;
	private OnConnectionListener connectionListener;
	private OnMessageListener messageListener;
	private ArrayList<Session> conectados;
	private ArrayList<Session> salaDeEspera;
	private ArrayList<NewConnection> conexiones;
	
	private  TCPConnection() {
		// TODO Auto-generated constructor stub
		conectados = new ArrayList<Session>();	
		salaDeEspera = new ArrayList<Session>();
		conexiones = new ArrayList<NewConnection>();
		setDaemon(true);
	}

	
	
	public ArrayList<Session> getSalaDeEspera() {
		return salaDeEspera;
	}



	public void setSalaDeEspera(ArrayList<Session> salaDeEspera) {
		this.salaDeEspera = salaDeEspera;
	}



	public static synchronized TCPConnection getInstance() {
		
		if(instance == null) {
			instance = new TCPConnection();
		}
		return instance;
	}
	
	
	@Override
	public void run() {

		try {
			
			server = new ServerSocket(puerto);
			
			while(true) {
				System.out.println("Esperando cliente...");
				Socket socket = server.accept();
				System.out.println("Nuevo cliente Conectado =)");
				System.out.println("Espere la respuesta del usuario...");
				Session session = new Session(socket);
				
				salaDeEspera.add(session);
				
				setAllSessionsInProgram(messageListener);
			}
		
				
		}catch(IOException e) {
			
		}
			
	}

	public void setConnectionListener(OnConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}

	public void setAllSessionsInProgram(OnMessageListener listener) {
		for(int i = 0; i < salaDeEspera.size();i++) {
		Session s = salaDeEspera.get(i);
		s.getReceptor().setList(listener);
		}
	}

	public void setPuerto(int puerto) {
	
		this.puerto = puerto;
		
	}
	
	public interface OnConnectionListener{
		public void onConnection(String id);
		public void OnRepeatConnection();
	}

	public OnMessageListener getMessageListener() {
		return messageListener;
	}


	public void setMessageListener(OnMessageListener messageListener) {
		this.messageListener = messageListener;
	}
	
	public void sendBroadCast(String msg) {
		
		for(int i = 0; i < conectados.size();i++) {
			Session s = conectados.get(i);
			s.getEmisor().setMessage(msg);
		}
		
	}

	public Session getSession(String id) {
		Session aux = null;
		boolean t = false;
		for(int i = 0; i < conectados.size() && !t;i++) {
			if(conectados.get(i).getUserName().equals(id)) {
				
				aux = conectados.get(i);
				t = true;
				
			}
		}
		
		return aux;
		
	}
	
	public void addUser(Session s,String msg) {
        int index = salaDeEspera.indexOf(s);
        Gson json = new Gson();

        if(hayMasDeDosUsuarios()) {
            //------------------
        	salaDeEspera.remove(index);
        	s.closeSocket();
        	
        	NewConnection coneccion = new NewConnection(msg,"Existe","");
            String mensaje2 = json.toJson(coneccion);
            connectionListener.OnRepeatConnection();
            s.getEmisor().setMessage(mensaje2);                   
            System.out.println(conectados.size() + "#");
            
        }else {

            salaDeEspera.remove(index);
            conectados.add(s);
            System.out.println("Sisa aqui entre" + s.getUserName());
            NewConnection m = new NewConnection(msg,"","");
            String msj1 = json.toJson(m);
            connectionListener.onConnection(msg);
            s.getEmisor().setMessage(msj1);
          
            
        }
	}
	
	public void sendDirectMessage(String id,String json) {
		boolean t = false;
		for(int i = 0; i < conectados.size() && !t;i++) {
			if(!conectados.get(i).getUserName().equalsIgnoreCase(id)) {
				conectados.get(i).getEmisor().setMessage(json);
				t = true;
			}
		}
	}

	
	public boolean hayMasDeDosUsuarios() {

		return conectados.size() >= 2 ? true : false;
	}
	
	
	
	public void closeSession(Receptor receptor) {
		
		for(int i = 0; i < conectados.size();i++) {
			
			Receptor r = conectados.get(i).getReceptor();
		
			if(r.equals(receptor)) {
				
				conectados.get(i).closeSocket();
				conectados.remove(conectados.get(i));
				break;
			}
			
			
		}
		
	}
	

}
