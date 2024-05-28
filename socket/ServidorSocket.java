package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket {

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = null;
        try {
            servidor = new ServerSocket(7006);

            System.out.println("Servidor rodando na porta 7006");

            while (true) { // Loop infinito para aceitar +1 conexão
                Socket conexao = servidor.accept();

                // Inicia uma Thread para tratar cada nova conexão
                Thread threadCliente = new Thread(() -> trataCliente(conexao));
                threadCliente.start();
            }

        } catch (IOException e) {
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        } finally {
            if (servidor!= null) {
                try {
                    servidor.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o servidor: " + e.getMessage());
                }
            }
        }
    }

    private static void trataCliente(Socket conexao) {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            PrintWriter saida = new PrintWriter(conexao.getOutputStream(), true);

            String texto;
            while (!(texto = entrada.readLine()).equals("sair")) {
                System.out.println("Recebido de " + conexao.getLocalAddress() + ": '" +  texto + "'");
                saida.println("Mensagem recebida '" + texto + "'");
            }

        } catch (IOException e) {
            System.out.println("Erro ao tratar a conexão com o cliente: " + e.getMessage());
        } finally {
            try {
                conexao.close();
            } catch (IOException e) {
                System.out.println("Erro ao fechar a conexão com o cliente: " + e.getMessage());
            }
        }
    }
}
