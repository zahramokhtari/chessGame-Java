package sample.GameChess.PlayGame.clientS;

import sample.GameChess.PlayGame.utilClient.NetworkUtilClient;

public abstract class Player {

    private boolean networkConnection = true;
    public boolean playerGiveMove = false;
    private NetworkUtilClient networkUtil = null;
    private boolean myMove = false;
    private Object receiveData;
    protected String ipAdress="localhost";
    private int port = 8080;

    //The constructor of Player Class
    public Player(){
        connectToServer();
    }

    public void connectToServer(){

        Thread thread = new Thread(() -> {
            setIp();

            networkUtil = new NetworkUtilClient(ipAdress, port);

            if (networkUtil.isAvaiable())
            {
                String str = (String) networkUtil.read();
                if (str != null) {
                    String string[] = str.split("\\+");
                    if (string[0].equalsIgnoreCase("start")) {
                        if (string[1].equalsIgnoreCase("firstPlayer")) {
                            initialize();
                            myMove = true;
                            playerGiveMove = false;
                        } else {
                            myMove = false;
                        }
                    }
                    startGame();
                }
            }
        });
        thread.start();
    }

    public void startGame(){
        while (networkConnection){
            if(myMove){
                networkUtil.write(WriteMoveData());
                myMove=false;
            }
            else {
                receiveData= networkUtil.read();
                if(receiveData==null ){
                    return;
                }
                else{
                    try{
                        if(((String) receiveData).equals("EXIT")){
                            return;
                        }
                    }catch (Exception e){ }
                }
                readMove(receiveData);
                myMove=true;
            }
        }
    }

    public <T> T WriteMoveData(){
        while(networkConnection){
            if(playerGiveMove){
                playerGiveMove=false;
                return WriteMove();
            }
        }
        return null;
    }

    public void closeConnection(){
        networkConnection = false;
        networkUtil.closeConnection();
    }

    public abstract void initialize();

    public abstract <T>  T WriteMove();

    public abstract <T> void readMove(T data);

    public abstract void setIp();

}