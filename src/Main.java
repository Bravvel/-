import java.net.DatagramSocket;
import java.net.SocketException;

public class Main {
    public final static int SERVICE_PORT = 50001;

    public static void main(String[] args) {
        try{
            DatagramSocket socket = new DatagramSocket(SERVICE_PORT);
            Thread server = new UDPServer(socket);
            Thread client = new UDPClient(socket);
            server.start();
            client.start();
        } catch (SocketException e){
            e.printStackTrace();
        }
    }
}