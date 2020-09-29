package comm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Emisor {

	private OutputStream os;
	private BufferedWriter bwriter;
	
	public Emisor(OutputStream os) {
		this.os = os;
		 bwriter = new BufferedWriter(new OutputStreamWriter(this.os));
	}
	
	public void setMessage(String msg) {
		new Thread(
				()->{
					try {
						bwriter.write(msg + "\n");
						bwriter.flush();
					} catch (IOException e) {
					
						System.out.println("Fue desconectado");
					}
	}).start();
		
	}
	
}
