package comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Receptor extends Thread {

	private InputStream is;
	private Session s;
	public OnMessageListener listener;
	
	public Receptor(Session s,InputStream is){
		this.is = is;
		this.s = s;
	
	}
	
	@Override
	public void run() {
		
		BufferedReader breader = new BufferedReader(new InputStreamReader(this.is));
		
		try {
		while(true) {
			
			
				String msj = breader.readLine();
				if(msj == null) {
					TCPConnection.getInstance().closeSession(this);
					break;
				}
				if(listener != null) {
					listener.onMessage(s,msj);
				}
				//System.out.println("Recibido:" + msj);
			
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			TCPConnection.getInstance().closeSession(this);
			System.out.println("Aqui fueque fueque");
			
		}
		
	}
	
	public void setList(OnMessageListener listener) {
		this.listener = listener;
	}
	
	
	public interface OnMessageListener{

		public void onMessage(Session s,String message);
		public void onDisconection(Session s);
	}

	
	
}
