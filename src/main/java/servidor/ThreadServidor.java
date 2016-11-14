//criando a classe servidor 
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//classe servidor que herda da classe thread 
public class ThreadServidor extends Thread{

	private static boolean started;
	
	public List<ThreadCliente> clientes = new ArrayList<ThreadCliente>();
	//botao iniciar que var startar o servidor
	public static void iniciar(){
		if(!started){
			started = true;
			new ThreadServidor().start();
		}
	}
	//metodo run da classe thread
	@Override
	public void run() {
		try {
                        //criando um objeto do tipo server socket que recebe a porta 9000
			ServerSocket server = new ServerSocket(9000);
			while(started){
				Socket socket = server.accept();
				ThreadCliente thread = new ThreadCliente(clientes, socket);
				clientes.add(thread);
				thread.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
