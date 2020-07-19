package sample.GameChess.StartServer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.GameChess.StartServer.modification.StartServer;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerMain extends Application {
    private boolean flag = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        InetAddress inetAddress = null;

        try {
            inetAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            System.out.println("Problem in printing IP address in server GUI");
        }

        primaryStage.setTitle("SERVER");
        Label label = new Label("IP: "+inetAddress+" Port: 8080");
        new StartServer();
        InetAddress finalInetAddress = inetAddress;
        label.setOnMouseClicked(e->{
            if(!flag){
                label.setText("SERVER STARTED..at "+ finalInetAddress);
                label.setTextFill(Color.BLUEVIOLET);
                flag = true;
            }
            else{
                label.setText("Server is Running...!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(label);
        primaryStage.setScene(new Scene(root, 200, 100));
        primaryStage.setResizable(true);
        primaryStage.setMaxHeight(500);
        primaryStage.setMaxWidth(400);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->System.exit(1));
    }

}