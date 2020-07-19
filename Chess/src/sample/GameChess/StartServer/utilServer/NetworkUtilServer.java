package sample.GameChess.StartServer.utilServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkUtilServer {
    boolean avaiable=true;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public NetworkUtilServer(Socket s) {
        try {
            this.socket = s;
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
        }
    }

    public Object read() {
        try {
            while (true){
                if(ois.available()>=0){
                    Object o=ois.readObject();
                  return  o;
                }
                if(ois.available()==-1){
                    return "EXIT";
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public <T> boolean write(T o) {
        try {
            oos.writeObject(o);
            return true;
        } catch (Exception e) {
            closeConnection();
            return false;
        }
    }

    public void closeConnection() {
        try {
            ois.close();
            oos.close();
        } catch (Exception e) {
        }
    }

    public boolean isAvaiable() {
        return avaiable;
    }
}