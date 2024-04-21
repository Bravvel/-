import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class UDPServer extends Thread {
    private DatagramSocket socket;

    UDPServer(DatagramSocket socket){
        this.socket = socket;
    }

    public void run() {
        try {
//            DatagramSocket socket = new DatagramSocket(SERVICE_PORT);
            byte[] data = new byte[1024]; byte[] sendingDataBuffer = new byte[1024];
            DatagramPacket inputPacket = new DatagramPacket(data, data.length);
            System.out.println("Waiting for a client to connect...");

            while (true) {
                try {
                    socket.receive(inputPacket);
                    ByteArrayInputStream in = new ByteArrayInputStream(data, 0, inputPacket.getLength());

                    int type = in.read();
                    int nameLength = in.read();
                    String name = new String(in.readNBytes(nameLength));

                    if (type == 0) {
                        System.out.println(name + " зашел в чат");
                    }
                    if (type == 1) {
                        System.out.println(name + " вышел из чата");
                    }
                    if (type == 2) {
                        int messageLen = in.read();
                        String message = new String(in.readNBytes(messageLen));
                        System.out.println(name + ": " + message);
                    }
                }
                catch (SocketException e){
                    break;
                }
                catch (IOException e) {
                    System.out.println("Некорректное сообщение");
                    e.printStackTrace();
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
