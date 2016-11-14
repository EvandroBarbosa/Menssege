//classe cliente 
package servidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author vando
 */

//classe cliente que extende a classe nativa thread
public class ThreadCliente extends Thread{
	
	private Socket socket;
	
	private List<ThreadCliente> clientes;
	
	private OutputStream os;
	
	private InputStream is;
	//construtor da classe cliente
	public ThreadCliente(List<ThreadCliente> clientes, Socket socket) {
		this.socket = socket;
		this.clientes = clientes;
		try {
                        this.is = socket.getInputStream();
			this.os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
        //método run 
	@Override
	public void run() {
		try {
			int count = 0;
			byte [] buffer = new byte[1024];
			while(count != -1){
				count = is.read(buffer);
				if(count != -1){
					for(ThreadCliente tc : clientes){
						tc.os.write(buffer, 0, count);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
