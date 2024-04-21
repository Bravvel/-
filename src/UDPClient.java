import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPClient extends Thread {
    public final static int SERVICE_PORT = 50001;
    public final static String USER_NAME = "Bravel";
    private DatagramSocket socket;

    UDPClient(DatagramSocket socket){
        this.socket = socket;
    }

    public void run(){
        try {

            try {
                InetAddress IPAddress = InetAddress.getByName("255.255.255.255");

                Scanner scanner = new Scanner(System.in);

                byte[] data1 = new byte[1024];
                byte[] name1 = USER_NAME.getBytes();
                byte nameLen1 = (byte)name1.length;
                data1[0] = 0;
                data1[1] = nameLen1;
                for(int i = 0; i < nameLen1; i++){
                    data1[i+2] = name1[i];
                }

                DatagramPacket sendingHelloPacket = new DatagramPacket(data1, data1.length, IPAddress, SERVICE_PORT);
                socket.send(sendingHelloPacket);

                while (true) {
                    DatagramSocket socket = new DatagramSocket();
                    byte[] data = new byte[1024];
                    byte type = 2;
                    String nameT = USER_NAME;
                    byte[] name = nameT.getBytes();
                    byte nameLen = (byte)name.length;
                    data[0] = type;
                    data[1] = nameLen;
                    for(int i = 0; i < nameLen; i++){
                        data[i+2] = name[i];
                    }

                    if (type == 2) {
                        String messageT = scanner.next();
                        if(messageT.equals("/exit")){
                            data[0] = 1;
                            DatagramPacket sendingPacket = new DatagramPacket(data, data.length, IPAddress, SERVICE_PORT);
                            socket.send(sendingPacket);
                            socket.close();
                            break;
                        } else{
                            byte[] message = messageT.getBytes();
                            byte messageLen = (byte)message.length;
                            data[nameLen + 2] = messageLen;
                            for(int i = 0; i < messageLen; i++){
                                data[nameLen + 3 + i] = message[i];
                            }
                            DatagramPacket sendingPacket = new DatagramPacket(data, data.length, IPAddress, SERVICE_PORT);
                            socket.send(sendingPacket);
                        }
                    }
                }
            } catch (UnknownHostException e) {
                System.out.println("Неизвестный хост");
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}