//criando classe conexao
package servidor;
//importando os pacotes java necessarios
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;
//classe conexao que herda thread
public class ThreadConexao extends Thread{
    //os atributos da classe conexao
	private JTextArea textArea;

	private String ip;

	private OutputStream os;
	
	private InputStream is;
        
        private String sender;
        
        private String usuario;
	//criando um construtor para classe conexao isso é sobre carga de métodos
	public ThreadConexao(JTextArea textArea, String ip, String usuario) {
		this.textArea = textArea;
		this.ip = ip;
                this.usuario = usuario;
		try {
                        //instaciando o socket 
			Socket socket = new Socket(ip, 9000);
                        this.sender = socket.getLocalAddress().getHostAddress();
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
        //método herdado da classe thread
	@Override
	public void run() {
		while(true){
			try {
                                //construido um buffer com a quantidade de bytes que a mensagem terá
				int count = 0;
				byte [] buffer = new byte[1024];
				while(count != -1){
					count = is.read(buffer);
					if(count != -1){
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						baos.write(buffer, 0, count);
						String msg = new String(baos.toByteArray());
						textArea.append(msg);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	//método inviar
	public void enviar(String msg){
		try {
			os.write((usuario+": "+msg).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
