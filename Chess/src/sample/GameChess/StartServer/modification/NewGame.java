package sample.GameChess.StartServer.modification;

import sample.GameChess.StartServer.utilServer.NetworkUtilServer;

/**
 * starts a new thread so a new game
 */
public class NewGame {
    private Thread thread;
    private static int gameCount = 0;
    private NetworkUtilServer player1,player2;
    private boolean firstPlayerMove = false;//which player's move

    public NewGame(NetworkUtilServer player1, NetworkUtilServer player2) {
        this.player1 = player1 ;
        this.player2 = player2 ;
        System.out.println((gameCount+1)+" th game start.");
        gameCount++;
        startGame();
    }

    public void startGame(){

        Runnable runnable= () -> {
            firstPlayerMove=true;
            while (true){
                Object moveData=receiveMoveData();
                if(moveData==null){
                    System.out.println("Exiting game");
                    break;
                }
                sentMoveData(moveData);
                if(firstPlayerMove)
                    firstPlayerMove=false;
                else
                    firstPlayerMove=true;
            }
        };
        thread=new Thread(runnable);
        thread.start();
    }

    public Object receiveMoveData(){
        Object s;
        if(firstPlayerMove){
            s= player1.read();
        }
        else {
            s= player2.read();
        }
        try{
            if(s==null || ((String)s).equals("EXIT")){
                closeGame();
                return null;
            }
            else
                return s;
        }catch (Exception e){
            return s;
        }
    }

    public void sentMoveData(Object data){
        if(!firstPlayerMove){
            if(!player1.write(data)){
                player2.write("EXIT");
            }
        }
        else {
            if(!player2.write(data))
                player1.write("EXIT");
        }
    }
    
    public void closeGame(){
        System.out.println("Player disconnected. Closing Game");
        try {
            thread.wait();
        } catch (Exception e) {
            System.out.println("error in NweGame.closeGame");
        }
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public static int getGameCount() {
        return gameCount;
    }

    public static void setGameCount(int gameCount) {
        NewGame.gameCount = gameCount;
    }

    public NetworkUtilServer getPlayer1() {
        return player1;
    }

    public void setPlayer1(NetworkUtilServer player1) {
        this.player1 = player1;
    }

    public NetworkUtilServer getPlayer2() {
        return player2;
    }

    public void setPlayer2(NetworkUtilServer player2) {
        this.player2 = player2;
    }

    public boolean isFirstPlayerMove() {
        return firstPlayerMove;
    }

    public void setFirstPlayerMove(boolean firstPlayerMove) {
        this.firstPlayerMove = firstPlayerMove;
    }

    @Override
    public String toString() {
        return "NewGame{" +
                "thread=" + thread +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", firstPlayerMove=" + firstPlayerMove +
                '}';
    }
}