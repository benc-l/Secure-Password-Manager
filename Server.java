
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Hashtable; 


/**
 * A password manager server.
 * You will need to give it the ability to correctly store passwords and to send them back to the
 * client when requested.
 */
public class Server {
    static Hashtable<String, String> websiteStore = new Hashtable<>();
    public static boolean handleMessage(Socket conn, byte[] data) {
        try (
            ObjectOutputStream out = new ObjectOutputStream(conn.getOutputStream());

            
        ) {
            String message = new String(data);
            String[] split = message.trim().split("\\s+" ,3); 
            if (split[0].equalsIgnoreCase("end")) {
                out.writeObject("bye.".getBytes());
                out.flush();
                return true;
            }
            else if(split[0].equalsIgnoreCase("store")){
                websiteStore.put(split[1], split[2]);
                out.writeObject("Password successfully stored\n".getBytes());
                out.flush();
                return false; 

            }

            else if(split[0].equalsIgnoreCase("get")){
                if(websiteStore.containsKey(split[1])){
                String ciphertext = websiteStore.get(split[1]);
                
                out.writeObject(ciphertext.getBytes());
                    out.flush();
                    return false; 
                }
                else{
                    System.out.println("There is no associated password for this website");
                    return false; 
                }
                
            }

            System.out.println("Client -> Server: " + message);
            out.writeObject(data);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 22500;  // Arbitrary non-privileged port
        boolean end = false;
        System.out.format("Listening for a client on port %d\n", port);

        try (
            ServerSocket serverSocket = new ServerSocket(port)
        ) {
            do {
                Socket socket = serverSocket.accept();
                System.out.format(
                    "Connected by %s:%d\n",
                    socket.getInetAddress().toString(),
                    socket.getPort()
                );
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                byte data[] = (byte[]) in.readObject();
                end = handleMessage(socket, data);
                in.close();
                socket.close();
            } while (!end);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}