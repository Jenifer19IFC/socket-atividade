package socket;
	
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSocket {
	
	public static void main(String[] args) throws IOException {
		
		Scanner entrada = new Scanner(System.in);
		
		String texto = "";
		
		Socket cliente = null; // Socket Cliente
		
		PrintStream saida = null; // Stream (turbo) de saída de dados
		
		try {
			
			cliente = new Socket("127.0.0.1", 7006);
			saida = new PrintStream(cliente.getOutputStream());
			
			// Adiciona um BufferedReader para ler respostas do servidor
			BufferedReader entradaDoServidor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			String respostaDoServidor = "";
	
			do {
				texto = entrada.nextLine(); // Lê teclado do cliente
				saida.println(texto); // Imprime o texto de saída de dadsos (Servidor)
				
				 // Lê a resposta do servidor
			    if ((respostaDoServidor = entradaDoServidor.readLine())!= null) {
				System.out.println("Resposta do servidor: " + respostaDoServidor);
			    }
			    
			}while(!"sair".equals(texto));
			
		}catch(IOException e) {
			System.out.println("Algo errado aconteceu.");
		}finally {
			cliente.close(); // Encerra o socket cliente
		}
		
	}

}
