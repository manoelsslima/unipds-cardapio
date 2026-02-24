package mx.florinda.cardapio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * O OutputStream do cliente é o InputStream do servidor, e o InputStream do cliente é o OutputStream do servidor
 */
public class ClienteItensCardapioComSocket {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8099)) {
            OutputStream clientOS = socket.getOutputStream();

            // OutputStream do cliente é o InputStream do servidor
            PrintStream clientOut = new PrintStream(clientOS);
            clientOut.println("GET / HTTP/1.1");
            clientOut.println();

            // InputStream do cliente é o OutputStream do servidor
            InputStream clientIS = socket.getInputStream();
            Scanner scanner = new Scanner(clientIS);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        }
    }
}
