package sample.GameChess.StartServer.modification;

public class StartServer {
    public StartServer(){
       new Thread(()->{
           new Server();
       }).start();
    }
}
