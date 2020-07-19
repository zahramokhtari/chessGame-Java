package sample.GameChess.PlayGame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import sample.GameChess.PlayGame.Controllers.GameControl;

public class HuntNow extends Application {

	private static Stage huntStage;
    public boolean online = false;

	public static final int WHITE_PLAYER = 1,BLACK_PLAYER = 2,EMPTY_PLAYER = 0;
	public static String MyName = null;
	public static String MyPassWord = "Password";

	private StackPane stackLayout;	
	private GameControl gameController;
	private static Stage stage = null;
	private boolean hasLogin = false;

    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void init() {
		
		/*// initialize the layout, create a CustomControl and add it to the layout
 		stackLayout = new StackPane();
 		gameController = new GameControl();
 		stackLayout.getChildren().add(gameController);

 		typeLabel.setEffect(new InnerShadow());
 		
        bLabel.setTextFill(Color.BLACK);
        wLabel.setTextFill(Color.WHITE);
        bLabel.setText("Black's Part");
        wLabel.setText("White's Part");
        bLabel.setStyle("-fx-font-size: 15;");
        wLabel.setStyle("-fx-font-size: 15;");
        myName.setStyle("-fx-font-size: 15;");
        opponensName.setStyle("-fx-font-size: 12;");
        typeLabel.setTextFill(Color.BLACK);
        typeLabel.setStyle("-fx-font-size: 14;");*/
        
	}

	// overridden start method
	@Override
	public void start(Stage primaryStage) {
		// set the title and scene, and show the stage
		huntStage = primaryStage;
		showLogin();
	}

    //show login window
    public void showLogin(){
        stage = huntStage;
        stage.setTitle("Play Your Game");
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(40);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 500, 500);
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(500);

        Label userName = new Label("UserName: ");
        grid.add(userName, 1, 1);

        TextField userTextField = new TextField();
        userTextField.setText(null);
        grid.add(userTextField, 3, 1);
        userTextField.setPromptText("admin");

        Label pw = new Label("PassWord: ");
        grid.add(pw, 1, 2);

        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("password");
        pwBox.setText(null);
        grid.add(pwBox, 3, 2,1,1);

        Button btn = new Button("Log-In");
        HBox hbBtn = new HBox(50);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(btn);
        grid.add(hbBtn, 3, 4);

        Button information = new Button("maker information :)");
        HBox hbInfo = new HBox(20);
        hbInfo.setAlignment(Pos.BASELINE_RIGHT);
        hbInfo.getChildren().addAll(information);
        grid.add(hbInfo,3,5);

        information.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane root = new Pane();
                Label label = new Label("Name : Zahea Mokhtari");
                Label label1 = new Label("شماره دانشجویی : 98243052");
                label1.setPrefHeight(50);

                root.getChildren().addAll(label,label1);
                Scene scene1 = new Scene(root,500,500);
                Stage stage = new Stage();
                stage.setScene(scene1);
                stage.show();
            }
        });

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String s = userTextField.getText();
                MyName = userTextField.getText();
                userTextField.setText(null);
                MyPassWord = pwBox.getText();
                pwBox.setText(null);

                if (MyPassWord.length()<8 && MyPassWord!=null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("password should be at least 8 characters");
                    alert.showAndWait();
                }

                else if(s!=null && MyPassWord!=null){
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("You are Logged In");
                    hasLogin = true;
                    online = true;
                    huntPage();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid username or password😄");
                    alert.setHeaderText("USERNAME or PASSWORD is null");
                    alert.setContentText("Please give a valid username....");
                    alert.showAndWait();
                }
            }
        });
        stage.show();
    }

	public void huntPage(){
		if(hasLogin && online){
            // initialize the layout, create a CustomControl and add it to the layout
            stackLayout = new StackPane();
            gameController = new GameControl();
            stackLayout.getChildren().add(gameController);

			huntStage.setScene(new Scene(stackLayout, 650, 550));

			VBox vb1 = new VBox();

			BorderPane bp = new BorderPane();
			HBox hb1 = new HBox();

			HBox hb2 = new HBox();

			hb1.setAlignment(Pos.CENTER);
			hb1.setSpacing(60);
			hb1.setStyle("-fx-background-color: #D3D3D3");
			hb1.setLayoutY(30);

			hb2.setAlignment(Pos.CENTER);//make it resizable
			hb2.setSpacing(60);
			hb2.setStyle("-fx-background-color: #2C2C2C");
			hb2.setLayoutY(30);

			huntStage.setScene(new Scene(bp, 550, 450 ));
			bp.setCenter(stackLayout);
			bp.setTop(hb1);
			bp.setBottom(hb2);
			bp.setRight(vb1);

			huntStage.setMaxHeight(700);
			huntStage.setMaxWidth(900);
			huntStage.setMinWidth(500);
			huntStage.setMinHeight(400);
			huntStage.show();

			stage.setOnCloseRequest(e->{
				gameController.player.closeConnection();
			});
		}
	}

}