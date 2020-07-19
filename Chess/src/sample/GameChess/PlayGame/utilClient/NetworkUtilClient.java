package sample.GameChess.PlayGame.utilClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkUtilClient {

    boolean avaiable=true;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public NetworkUtilClient(String s, int port) {
        try {
            this.socket = new Socket(s, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            avaiable=false;
        }
    }

    public Object read() {
        try {
            while (true){
                if(ois.available()>=0){
                  return  ois.readObject();
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
        return this.avaiable;
    }
}