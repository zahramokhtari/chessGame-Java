package sample.GameChess.PlayGame.Controllers;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import sample.GameChess.PlayGame.PieceClasses.*;
import sample.GameChess.PlayGame.clientS.Player;

public class GameControl extends Control {

    public static final int WHITE_PLAYER = 1, BLACK_PLAYER = 2, EMPTY_PLAYER = 0;
    public Player player;

    private  static boolean isMyTurn = false;
    private int myType = BLACK_PLAYER;

    private String message = null;
    private int si,sj;
    private int ti,tj;
    private Piece selectedPiece;
    private Piece targetedPiece;
    private boolean isJunkSelected;
    private boolean winner=false;
    private boolean stale=false;
    private BoardImplementation chessboard;
    private Translate pos;
    private Logics Logics;
    private int staleCountBlack=8;
    private int staleCountWhite=8;
    private int hash;

    public GameControl(){

        pos = new Translate();
        setSkin(new GameControlSkin(this));
        chessboard = new BoardImplementation();
        Logics = new Logics();
        getChildren().addAll(chessboard);

        // Places background squares
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                chessboard.placeBoard(i, j);
            }
        }

        // Places chess piece images
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                chessboard.placeImageViews(i, j);
            }
        }

        setOnKeyPressed(new EventHandler<KeyEvent>(){//when pressing space bar
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.SPACE)
                    System.out.print("Game Reset!");
                chessboard = new BoardImplementation();
                getChildren().addAll(chessboard);

                // Places background squares
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        chessboard.placeBoard(i, j);
                    }
                }

                // Places chess piece images
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        chessboard.placeImageViews(i, j);
                    }
                }
                // Reset game variables
                stale=false;
                winner=false;
                staleCountWhite=8;
                staleCountBlack=8;
                chessboard.changeclickfalse();
            }
        });

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try{
                    if(isMyTurn){

                        hash = event.getTarget().hashCode();
                        ImageView [][]selectView = chessboard.getImageviews();
                        Rectangle[][] targetSelect = chessboard.getBoard();

                        message = null;//intentionally made null just in mouse handler

                        boolean founds = false;
                        for(int x=0;x<8 && !founds;x++){
                            for(int y=0;y<8 && !founds;y++){
                                if(selectView[x][y].hashCode() == hash && selectView[x][y]!=null){
                                    si = x; sj = y;
                                    founds = true;
                                }
                            }
                        }

                        boolean foundt = false;
                        for(int x=0;x<8 && !foundt;x++){
                            for(int y=0;y<8 && !foundt;y++){
                                if(selectView[x][y].hashCode() == hash || targetSelect[x][y].hashCode() == hash){
                                    ti = x; tj = y;
                                    foundt = true;
                                }
                            }
                        }

                        // Second click
                        if(chessboard.getClicklogic() == "true"){
                            Piece[][] boardstate = chessboard.getState();
                            targetedPiece = chessboard.selectTarget(ti,tj);

                            // If pawn selected ..
                            if(selectedPiece.toString().equals("Pawn") && selectedPiece != null && targetedPiece != null
                                    && !selectedPiece.equals(targetedPiece)){
                                // Only executes if legal move ..
                                if(chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.CORNFLOWERBLUE)
                                        || chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.AQUAMARINE)){
                                    // If check is false
                                    if(!Logics.checkstatus()){
                                        Piece[][] oldstate = new Piece[8][8];
                                        // Transfer pieces to backup variable
                                        for(int x=0;x < 8; x++){
                                            for(int y=0; y < 8; y++){
                                                oldstate[x][y] = boardstate[x][y];
                                            }
                                        }
                                        // Do move
                                        boardstate = selectedPiece.movepawn(selectedPiece, targetedPiece, boardstate);
                                        // If move results in no check, do move
                                        if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                            chessboard.setBoard(boardstate);
                                            chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                            message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                            chessboard.changePlayer();
                                            chessboard.changeclicknull();
                                            // Successful move
                                            staleCountWhite=8;
                                            staleCountBlack=8;
                                        }
                                        // If check, reverse move
                                        else{
                                            //message = null;
                                            chessboard.changeclicknull();
                                            chessboard.setBoard(oldstate);
                                            Logics.flipcheck();

                                            // Stalemate check
                                            if(selectedPiece.type()==1){
                                                staleCountWhite--;
                                            }
                                            if(selectedPiece.type()==2){
                                                staleCountBlack--;
                                            }
                                            if(staleCountWhite==0 || staleCountBlack==0){
                                                stale=true;
                                            }

                                        }
                                    }

                                    // If in check ..
                                    if(Logics.checkstatus()){
                                        // Do move
                                        Piece[][] oldstate = new Piece[8][8];
                                        for(int x=0;x < 8; x++){
                                            for(int y=0; y < 8; y++){
                                                oldstate[x][y] = boardstate[x][y];
                                            }
                                        }
                                        boardstate = selectedPiece.movepawn(selectedPiece, targetedPiece, boardstate);
                                        // Check if still in check, if not, do move
                                        if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                            chessboard.setBoard(boardstate);
                                            chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                            message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                            chessboard.changePlayer();
                                            chessboard.changeclicknull();
                                            staleCountWhite=8;
                                            staleCountBlack=8;
                                            // If still in check, undo move
                                        }
                                        else{
                                            chessboard.changeclicknull();
                                            chessboard.setBoard(oldstate);
                                            Logics.flipcheck();

                                            // Stalemate check
                                            if(selectedPiece.type()==1){
                                                staleCountWhite--;
                                            }
                                            if(selectedPiece.type()==2){
                                                staleCountBlack--;
                                            }
                                            if(staleCountWhite==0 || staleCountBlack==0){
                                                System.out.print("STALEMATE FOUND!!\n");
                                                stale=true;
                                            }
                                        }
                                    }

                                }
                                else{

                                    // Stalemate check
                                    if(selectedPiece.type()==1){
                                        staleCountWhite--;
                                    }
                                    if(selectedPiece.type()==2){
                                        staleCountBlack--;
                                    }
                                    if(staleCountWhite==0 || staleCountBlack==0){
                                        System.out.print("STALEMATE FOUND!!\n");
                                        stale=true;
                                    }
                                }
                            }

                            // If bishop selected ..
                            if(selectedPiece.toString().equals("Bishop") && selectedPiece != null && targetedPiece != null && !selectedPiece.equals(targetedPiece)){
                                if(chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.CORNFLOWERBLUE) || chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.AQUAMARINE)){
                                    // If check is false
                                    if(!Logics.checkstatus()){
                                        Piece[][] oldstate = new Piece[8][8];
                                        // Transfer pieces to backup variable
                                        for(int x=0;x < 8; x++){
                                            for(int y=0; y < 8; y++){
                                                oldstate[x][y] = boardstate[x][y];
                                            }
                                        }
                                        // Do move
                                        boardstate = selectedPiece.movebishop(selectedPiece, targetedPiece, boardstate);
                                        // If move results in no check, do move
                                        if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                            chessboard.setBoard(boardstate);
                                            chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                            message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                            chessboard.changePlayer();
                                            chessboard.changeclicknull();
                                            staleCountWhite=8;
                                            staleCountBlack=8;
                                        }
                                        // If check, reverse move
                                        else{
                                            //message = null;
                                            chessboard.changeclicknull();
                                            chessboard.setBoard(oldstate);
                                            Logics.flipcheck();

                                            // Stalemate check
                                            if(selectedPiece.type()==1){
                                                staleCountWhite--;
                                            }
                                            if(selectedPiece.type()==2){
                                                staleCountBlack--;
                                            }
                                            if(staleCountWhite==0 || staleCountBlack==0){
                                                System.out.print("STALEMATE FOUND!!\n");
                                                stale=true;
                                            }
                                        }
                                    }

                                    // If in check ..
                                    if(Logics.checkstatus()){
                                        // Do move
                                        Piece[][] oldstate = new Piece[8][8];
                                        for(int x=0;x < 8; x++){
                                            for(int y=0; y < 8; y++){
                                                oldstate[x][y] = boardstate[x][y];
                                            }
                                        }
                                        boardstate = selectedPiece.movebishop(selectedPiece, targetedPiece, boardstate);
                                        // Check if still in check, if not, do move
                                        if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                            chessboard.setBoard(boardstate);
                                            chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                            message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                            chessboard.changePlayer();
                                            chessboard.changeclicknull();
                                            staleCountWhite=8;
                                            staleCountBlack=8;
                                            // If still in check, undo move
                                        }
                                        else{
                                            //message = null;
                                            chessboard.changeclicknull();
                                            chessboard.setBoard(oldstate);
                                            Logics.flipcheck();

                                            // Stalemate check
                                            if(selectedPiece.type()==1){
                                                staleCountWhite--;
                                            }
                                            if(selectedPiece.type()==2){
                                                staleCountBlack--;
                                            }
                                            if(staleCountWhite==0 || staleCountBlack==0){
                                                System.out.print("STALEMATE FOUND!!\n");
                                                stale=true;
                                            }
                                        }
                                    }
                                }
                                else{

                                    // Stalemate check
                                    if(selectedPiece.type()==1){
                                        staleCountWhite--;
                                    }
                                    if(selectedPiece.type()==2){
                                        staleCountBlack--;
                                    }
                                    if(staleCountWhite==0 || staleCountBlack==0){
                                        System.out.print("STALEMATE FOUND!!\n");
                                        stale=true;
                                    }
                                }
                            }

                            // If queen selected ..
                            if(selectedPiece.toString().equals("Queen") && selectedPiece != null && targetedPiece != null && !selectedPiece.equals(targetedPiece)){
                                if(chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.CORNFLOWERBLUE) || chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.AQUAMARINE)){
                                    // If check is false
                                    if(!Logics.checkstatus()){
                                        Piece[][] oldstate = new Piece[8][8];
                                        // Transfer pieces to backup variable
                                        for(int x=0;x < 8; x++){
                                            for(int y=0; y < 8; y++){
                                                oldstate[x][y] = boardstate[x][y];
                                            }
                                        }
                                        // Do move
                                        boardstate = selectedPiece.movequeen(selectedPiece, targetedPiece, boardstate);
                                        // If move results in no check, do move
                                        if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                            chessboard.setBoard(boardstate);
                                            chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                            message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                            chessboard.changePlayer();
                                            chessboard.changeclicknull();
                                            staleCountWhite=8;
                                            staleCountBlack=8;
                                        }
                                        // If check, reverse move
                                        else{
                                            chessboard.changeclicknull();
                                            chessboard.setBoard(oldstate);
                                            Logics.flipcheck();
                                            if(selectedPiece.type()==1){
                                                staleCountWhite--;
                                            }
                                            if(selectedPiece.type()==2){
                                                staleCountBlack--;
                                            }
                                            if(staleCountWhite==0 || staleCountBlack==0){
                                                System.out.print("STALEMATE FOUND!!\n");
                                                stale=true;
                                            }
                                        }
                                    }

                                    // If in check ..
                                    if(Logics.checkstatus()){
                                        // Do move
                                        Piece[][] oldstate = new Piece[8][8];
                                        for(int x=0;x < 8; x++){
                                            for(int y=0; y < 8; y++){
                                                oldstate[x][y] = boardstate[x][y];
                                            }
                                        }
                                        boardstate = selectedPiece.movequeen(selectedPiece, targetedPiece, boardstate);
                                        // Check if still in check, if not, do move
                                        if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                            chessboard.setBoard(boardstate);
                                            chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                            message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                            chessboard.changePlayer();
                                            chessboard.changeclicknull();
                                            staleCountWhite=8;
                                            staleCountBlack=8;
                                            // If still in check, undo move
                                        }
                                        else{
                                            chessboard.changeclicknull();
                                            chessboard.setBoard(oldstate);
                                            Logics.flipcheck();

                                            // Stalemate check
                                            if(selectedPiece.type()==1){
                                                staleCountWhite--;
                                            }
                                            if(selectedPiece.type()==2){
                                                staleCountBlack--;
                                            }
                                        }
                                        if(staleCountWhite==0 || staleCountBlack==0){
                                            stale=true;
                                        }
                                    }
                                }
                                else{

                                    // Stalemate check
                                    if(selectedPiece.type()==1){
                                        staleCountWhite--;
                                    }
                                    if(selectedPiece.type()==2){
                                        staleCountBlack--;
                                    }
                                    if(staleCountWhite==0 || staleCountBlack==0){
                                        stale=true;
                                    }
                                }
                            }

                            // If rook selected ..
                            if(selectedPiece.toString().equals("Rook") && selectedPiece != null && targetedPiece != null && !selectedPiece.equals(targetedPiece)){
                                if(chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.CORNFLOWERBLUE) || chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.AQUAMARINE)){
                                    // If check is false
                                    if(!Logics.checkstatus()){
                                        Piece[][] oldstate = new Piece[8][8];
                                        // Transfer pieces to backup variable
                                        for(int x=0;x < 8; x++){
                                            for(int y=0; y < 8; y++){
                                                oldstate[x][y] = boardstate[x][y];
                                            }
                                        }
                                        // Do move
                                        boardstate = selectedPiece.moverook(selectedPiece, targetedPiece, boardstate);
                                        // If move results in no check, do move
                                        if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                            chessboard.setBoard(boardstate);
                                            chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                            message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                            chessboard.changePlayer();
                                            chessboard.changeclicknull();
                                            staleCountWhite=8;
                                            staleCountBlack=8;
                                        }
                                        // If check, reverse move
                                        else{
                                            chessboard.changeclicknull();
                                            chessboard.setBoard(oldstate);
                                            Logics.flipcheck();

                                            // Stalemate check
                                            if(selectedPiece.type()==1){
                                                staleCountWhite--;
                                            }
                                            if(selectedPiece.type()==2){
                                                staleCountBlack--;
                                            }
                                            if(staleCountWhite==0 || staleCountBlack==0){
                                                System.out.print("STALEMATE FOUND!!\n");
                                                stale=true;
                                            }
                                        }
                                    }

                                    // If in check ..
                                    if(Logics.checkstatus()){
                                        // Do move
                                        Piece[][] oldstate = new Piece[8][8];
                                        for(int x=0;x < 8; x++){
                                            for(int y=0; y < 8; y++){
                                                oldstate[x][y] = boardstate[x][y];
                                            }
                                        }
                                        boardstate = selectedPiece.moverook(selectedPiece, targetedPiece, boardstate);
                                        // Check if still in check, if not, do move
                                        if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                            chessboard.setBoard(boardstate);
                                            chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                            message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                            chessboard.changePlayer();
                                            chessboard.changeclicknull();
                                            staleCountWhite=8;
                                            staleCountBlack=8;
                                            // If still in check, undo move
                                        }
                                        else{
                                            chessboard.changeclicknull();
                                            chessboard.setBoard(oldstate);
                                            Logics.flipcheck();

                                            // Stalemate check
                                            if(selectedPiece.type()==1){
                                                staleCountWhite--;
                                            }
                                            if(selectedPiece.type()==2){
                                                staleCountBlack--;
                                            }
                                            if(staleCountWhite==0 || staleCountBlack==0){
                                                System.out.print("STALEMATE FOUND!!\n");
                                                stale=true;
                                            }
                                        }
                                    }
                                }
                                else{

                                    // Stalemate check
                                    if(selectedPiece.type()==1){
                                        staleCountWhite--;
                                    }
                                    if(selectedPiece.type()==2){
                                        staleCountBlack--;
                                    }
                                    if(staleCountWhite==0 || staleCountBlack==0){
                                        System.out.print("STALEMATE FOUND!!\n");
                                        stale=true;
                                    }
                                }
                            }

                            // If king selected ..
                            if(selectedPiece.toString().equals("King") && selectedPiece != null && targetedPiece != null && !selectedPiece.equals(targetedPiece)){
                                if(chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.CORNFLOWERBLUE) || chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.AQUAMARINE)){
                                    // If check is false
                                    if(!Logics.checkstatus()){
                                        Piece[][] oldstate = new Piece[8][8];
                                        // Transfer pieces to backup variable
                                        for(int x=0;x < 8; x++){
                                            for(int y=0; y < 8; y++){
                                                oldstate[x][y] = boardstate[x][y];
                                            }
                                        }
                                        // Do move
                                        boardstate = selectedPiece.moveking(selectedPiece, targetedPiece, boardstate);
                                        // If move results in no check, do move
                                        if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                            chessboard.setBoard(boardstate);
                                            chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                            message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                            chessboard.changePlayer();
                                            chessboard.changeclicknull();
                                            staleCountWhite=8;
                                            staleCountBlack=8;
                                        }
                                        // If check, reverse move
                                        else{
                                            chessboard.changeclicknull();
                                            chessboard.setBoard(oldstate);
                                            Logics.flipcheck();

                                            // Stalemate check
                                            if(selectedPiece.type()==1){
                                                staleCountWhite--;
                                            }
                                            if(selectedPiece.type()==2){
                                                staleCountBlack--;
                                            }
                                            if(staleCountWhite==0 || staleCountBlack==0){
                                                System.out.print("STALEMATE FOUND!!\n");
                                                stale=true;
                                            }
                                        }
                                    }

                                    // If in check ..
                                    if(Logics.checkstatus()){
                                        // Do move
                                        Piece[][] oldstate = new Piece[8][8];
                                        for(int x=0;x < 8; x++){
                                            for(int y=0; y < 8; y++){
                                                oldstate[x][y] = boardstate[x][y];
                                            }
                                        }
                                        boardstate = selectedPiece.moveking(selectedPiece, targetedPiece, boardstate);
                                        // Check if still in check, if not, do move
                                        if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                            chessboard.setBoard(boardstate);
                                            chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                            message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                            chessboard.changePlayer();
                                            chessboard.changeclicknull();
                                            staleCountWhite=8;
                                            staleCountBlack=8;
                                            // If still in check, undo move
                                        }
                                        else{
                                            chessboard.changeclicknull();
                                            chessboard.setBoard(oldstate);
                                            Logics.flipcheck();

                                            // Stalemate check
                                            if(selectedPiece.type()==1){
                                                staleCountWhite--;
                                            }
                                            if(selectedPiece.type()==2){
                                                staleCountBlack--;
                                            }
                                            if(staleCountWhite==0 || staleCountBlack==0){
                                                System.out.print("STALEMATE FOUND!!\n");
                                                stale=true;
                                            }
                                        }
                                    }
                                }
                                else{

                                    // Stalemate check
                                    if(selectedPiece.type()==1){
                                        staleCountWhite--;
                                    }
                                    if(selectedPiece.type()==2){
                                        staleCountBlack--;
                                    }
                                    if(staleCountWhite==0 || staleCountBlack==0){
                                        System.out.print("STALEMATE FOUND!!\n");
                                        stale=true;
                                    }
                                }
                            }

                            // If knight selected ..
                            if(selectedPiece.toString().equals("Knight") && selectedPiece != null && targetedPiece != null && !selectedPiece.equals(targetedPiece)){
                                if(chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.CORNFLOWERBLUE) || chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.AQUAMARINE)){
                                    // If check is false
                                    if(!Logics.checkstatus()){
                                        Piece[][] oldstate = new Piece[8][8];
                                        // Transfer pieces to backup variable
                                        for(int x=0;x < 8; x++){
                                            for(int y=0; y < 8; y++){
                                                oldstate[x][y] = boardstate[x][y];
                                            }
                                        }
                                        // Do move
                                        boardstate = selectedPiece.moveknight(selectedPiece, targetedPiece, boardstate);
                                        // If move results in no check, do move
                                        if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                            chessboard.setBoard(boardstate);
                                            chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                            message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                            chessboard.changePlayer();
                                            chessboard.changeclicknull();
                                            staleCountWhite=8;
                                            staleCountBlack=8;
                                        }
                                        // If check, reverse move
                                        else{
                                            chessboard.changeclicknull();
                                            chessboard.setBoard(oldstate);
                                            Logics.flipcheck();

                                            // Stalemate check
                                            if(selectedPiece.type()==1){
                                                staleCountWhite--;
                                            }
                                            if(selectedPiece.type()==2){
                                                staleCountBlack--;
                                            }
                                            if(staleCountWhite==0 || staleCountBlack==0){
                                                System.out.print("STALEMATE FOUND!!\n(rare yet)");
                                                stale=true;
                                            }
                                        }
                                    }

                                    // If in check ..
                                    if(Logics.checkstatus()){
                                        // Do move
                                        Piece[][] oldstate = new Piece[8][8];
                                        for(int x=0;x < 8; x++){
                                            for(int y=0; y < 8; y++){
                                                oldstate[x][y] = boardstate[x][y];
                                            }
                                        }
                                        boardstate = selectedPiece.moveknight(selectedPiece, targetedPiece, boardstate);
                                        // Check if still in check, if not, do move
                                        if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                            chessboard.setBoard(boardstate);
                                            chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                            message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                            chessboard.changePlayer();
                                            chessboard.changeclicknull();
                                            staleCountWhite=8;
                                            staleCountBlack=8;
                                            // If still in check, undo move
                                        }
                                        else{
                                            //message = null;
                                            chessboard.changeclicknull();
                                            chessboard.setBoard(oldstate);
                                            Logics.flipcheck();

                                            // Stalemate check
                                            if(selectedPiece.type()==1){
                                                staleCountWhite--;
                                            }
                                            if(selectedPiece.type()==2){
                                                staleCountBlack--;
                                            }
                                            if(staleCountWhite==0 || staleCountBlack==0){
                                                System.out.print("STALEMATE FOUND!!\n");
                                                stale=true;
                                            }
                                        }
                                    }
                                }
                                else{

                                    // Stalemate check
                                    if(selectedPiece.type()==WHITE_PLAYER){
                                        staleCountWhite--;
                                    }
                                    if(selectedPiece.type()==BLACK_PLAYER){
                                        staleCountBlack--;
                                    }
                                    if(staleCountWhite==0 || staleCountBlack==0){
                                        System.out.print("STALEMATE found!!");
                                        stale=true;
                                    }
                                }
                            }
                            else{
                                chessboard.changeclicknull();
                                chessboard.clearHighLights();
                            }

                            chessboard.clearHighLights();
                            chessboard.changeclicknull();

                            // Check for checkmate ..
                            if(Logics.ischeckMateOccurs(chessboard.otherplayer(), chessboard.getState())=="true"){
                                winner=true;
                            }

                            getScene().setCursor(Cursor.DEFAULT);

                            // highlight check..

                            if(Logics.checkstatus()){
                                chessboard.checkHighLight(Logics.checkCoordI(), Logics.checkCoordJ());
                                chessboard.changeclicknull();
                            }
                        }

                        if(chessboard.getClicklogic().equals("false") && !stale && !winner){
                            selectedPiece = chessboard.selectPiece(si,sj);

                            if(selectedPiece.toString().equals("Empty") || !chessboard.pieceselect()){
                                isJunkSelected=true;
                            }
                            else{isJunkSelected=false;}

                            if(!selectedPiece.equals("Empty") && !isJunkSelected){
                                getScene().setCursor(new ImageCursor(selectedPiece.image()));
                                chessboard.changeclicktrue();
                                // Highlights valid moves..
                                chessboard.validMoves(selectedPiece);}

                            // Check 4 check .....for otherPlayer//he will be the current Player
                            if(!Logics.checkstatus()){
                                Logics.check4check(chessboard.otherplayer(), chessboard.getState());}
                        }

                        // If completed move, return to first click ..
                        if(chessboard.getClicklogic().equals("null")){
                            chessboard.changeclickfalse();
                        }

                        if(message!=null){
                            isMyTurn = false;
                            player.playerGiveMove = true;
                        }

                    }

                }catch (Exception e){
                    System.out.print(e);
                }

            }
        });

        //finally call it separately
        runClient();//in HuntNow
    }


    public void runClient(){
        player =new Player() {//Anonymous object of [Player] class have to implement every methods of Player class
            @Override
            public void initialize() {
                isMyTurn = true;
                myType = WHITE_PLAYER;
            }

            @Override
            public <T> T WriteMove() {
                return (T) message;
            }

            @Override
            public <T> void readMove(T data) {
                try {
                    //do something with your data
                    String[] coords = ((String) data).split(" ");
                    int si = Integer.parseInt(coords[0]);
                    int sj = Integer.parseInt(coords[1]);
                    int ti = Integer.parseInt(coords[2]);
                    int tj = Integer.parseInt(coords[3]);
                    doTheSameThing(si,sj,ti,tj);//first click
                    doTheSameThing(si,sj,ti,tj);//second click
                    } catch (Exception e) {
                        System.out.print("Errors in ReadMove");
                }
            }

            @Override//done
            public void setIp() {
                ipAdress="localhost";
            }

        };
    }

    public void doTheSameThing(int si , int sj , int ti , int tj){
        try {

            isMyTurn  = false;

            // Second click
            if(chessboard.getClicklogic() == "true"){
                Piece[][] boardstate = chessboard.getState();
                targetedPiece = chessboard.selectTarget(ti,tj);

                // If pawn selected ..
                if(selectedPiece.toString().equals("Pawn") && selectedPiece != null && targetedPiece != null
                        && !selectedPiece.equals(targetedPiece)){
                    // Only executes if legal move ..
                    if(chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.CORNFLOWERBLUE)
                            || chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.AQUAMARINE)){
                        // If check is false
                        if(!Logics.checkstatus()){
                            Piece[][] oldstate = new Piece[8][8];
                            // Transfer pieces to backup variable
                            for(int x=0;x < 8; x++){
                                for(int y=0; y < 8; y++){
                                    oldstate[x][y] = boardstate[x][y];
                                }
                            }
                            // Do move
                            boardstate = selectedPiece.movepawn(selectedPiece, targetedPiece, boardstate);
                            // If move results in no check, do move
                            if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                chessboard.setBoard(boardstate);
                                chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
//                                message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                chessboard.changePlayer();
                                chessboard.changeclicknull();
                                // Successful move
                                staleCountWhite=8;
                                staleCountBlack=8;
                            }
                            // If check, reverse move
                            else{
                                chessboard.changeclicknull();
                                chessboard.setBoard(oldstate);
                                Logics.flipcheck();

                                // Stalemate check
                                if(selectedPiece.type()==1){
                                    staleCountWhite--;
                                }
                                if(selectedPiece.type()==2){
                                    staleCountBlack--;
                                }
                                if(staleCountWhite==0 || staleCountBlack==0){
                                    stale=true;
                                }
                            }
                        }

                        // If in check ..
                        if(Logics.checkstatus()){
                            // Do move
                            Piece[][] oldstate = new Piece[8][8];
                            for(int x=0;x < 8; x++){
                                for(int y=0; y < 8; y++){
                                    oldstate[x][y] = boardstate[x][y];
                                }
                            }
                            boardstate = selectedPiece.movepawn(selectedPiece, targetedPiece, boardstate);
                            // Check if still in check, if not, do move
                            if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                chessboard.setBoard(boardstate);
                                chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                chessboard.changePlayer();
                                chessboard.changeclicknull();
                                staleCountWhite=8;
                                staleCountBlack=8;
                                // If still in check, undo move
                            }
                            else{
                                chessboard.changeclicknull();
                                chessboard.setBoard(oldstate);
                                Logics.flipcheck();

                                // Stalemate check
                                if(selectedPiece.type()==1){
                                    staleCountWhite--;
                                }
                                if(selectedPiece.type()==2){
                                    staleCountBlack--;
                                }
                                if(staleCountWhite==0 || staleCountBlack==0){
                                    System.out.print("STALEMATE FOUND!!\n");
                                    stale=true;
                                }
                            }
                        }

                    }
                    else{

                        // Stalemate check
                        if(selectedPiece.type()==1){
                            staleCountWhite--;
                        }
                        if(selectedPiece.type()==2){
                            staleCountBlack--;
                        }
                        if(staleCountWhite==0 || staleCountBlack==0){
                            stale=true;
                        }
                    }
                }

                // If bishop selected ..
                if(selectedPiece.toString().equals("Bishop") && selectedPiece != null && targetedPiece != null && !selectedPiece.equals(targetedPiece)){
                    if(chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.CORNFLOWERBLUE) || chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.AQUAMARINE)){
                        // If check is false
                        if(!Logics.checkstatus()){
                            Piece[][] oldstate = new Piece[8][8];
                            // Transfer pieces to backup variable
                            for(int x=0;x < 8; x++){
                                for(int y=0; y < 8; y++){
                                    oldstate[x][y] = boardstate[x][y];
                                }
                            }
                            // Do move
                            boardstate = selectedPiece.movebishop(selectedPiece, targetedPiece, boardstate);
                            // If move results in no check, do move
                            if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                chessboard.setBoard(boardstate);
                                chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                chessboard.changePlayer();
                                chessboard.changeclicknull();
                                staleCountWhite=8;
                                staleCountBlack=8;
                            }
                            // If check, reverse move
                            else{
                                //message = null;
                                chessboard.changeclicknull();
                                chessboard.setBoard(oldstate);
                                Logics.flipcheck();

                                // Stalemate check
                                if(selectedPiece.type()==1){
                                    staleCountWhite--;
                                }
                                if(selectedPiece.type()==2){
                                    staleCountBlack--;
                                }
                                if(staleCountWhite==0 || staleCountBlack==0){
                                    stale=true;
                                }
                            }
                        }

                        // If in check ..
                        if(Logics.checkstatus()){
                            // Do move
                            Piece[][] oldstate = new Piece[8][8];
                            for(int x=0;x < 8; x++){
                                for(int y=0; y < 8; y++){
                                    oldstate[x][y] = boardstate[x][y];
                                }
                            }
                            boardstate = selectedPiece.movebishop(selectedPiece, targetedPiece, boardstate);
                            // Check if still in check, if not, do move
                            if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                chessboard.setBoard(boardstate);
                                chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
//                                message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                chessboard.changePlayer();
                                chessboard.changeclicknull();
                                staleCountWhite=8;
                                staleCountBlack=8;
                                // If still in check, undo move
                            }
                            else{
                                chessboard.changeclicknull();
                                chessboard.setBoard(oldstate);
                                Logics.flipcheck();

                                // Stalemate check
                                if(selectedPiece.type()==1){
                                    staleCountWhite--;
                                }
                                if(selectedPiece.type()==2){
                                    staleCountBlack--;
                                }
                                if(staleCountWhite==0 || staleCountBlack==0){
                                    System.out.print("STALEMATE FOUND!!\n");
                                    stale=true;
                                }
                            }
                        }
                    }
                    else{

                        // Stalemate check
                        if(selectedPiece.type()==1){
                            staleCountWhite--;
                        }
                        if(selectedPiece.type()==2){
                            staleCountBlack--;
                        }
                        if(staleCountWhite==0 || staleCountBlack==0){
                            System.out.print("STALEMATE FOUND!!\n");
                            stale=true;
                        }
                    }
                }

                // If queen selected ..
                if(selectedPiece.toString().equals("Queen") && selectedPiece != null && targetedPiece != null && !selectedPiece.equals(targetedPiece)){
                    if(chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.CORNFLOWERBLUE) || chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.AQUAMARINE)){
                        // If check is false
                        if(!Logics.checkstatus()){
                            Piece[][] oldstate = new Piece[8][8];
                            // Transfer pieces to backup variable
                            for(int x=0;x < 8; x++){
                                for(int y=0; y < 8; y++){
                                    oldstate[x][y] = boardstate[x][y];
                                }
                            }
                            // Do move
                            boardstate = selectedPiece.movequeen(selectedPiece, targetedPiece, boardstate);
                            // If move results in no check, do move
                            if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                chessboard.setBoard(boardstate);
                                chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                chessboard.changePlayer();
                                chessboard.changeclicknull();
                                staleCountWhite=8;
                                staleCountBlack=8;
                            }
                            // If check, reverse move
                            else{
                                chessboard.changeclicknull();
                                chessboard.setBoard(oldstate);
                                Logics.flipcheck();
                                if(selectedPiece.type()==1){
                                    staleCountWhite--;
                                }
                                if(selectedPiece.type()==2){
                                    staleCountBlack--;
                                }
                                if(staleCountWhite==0 || staleCountBlack==0){
                                    stale=true;
                                }
                            }
                        }

                        // If in check ..
                        if(Logics.checkstatus()){
                            // Do move
                            Piece[][] oldstate = new Piece[8][8];
                            for(int x=0;x < 8; x++){
                                for(int y=0; y < 8; y++){
                                    oldstate[x][y] = boardstate[x][y];
                                }
                            }
                            boardstate = selectedPiece.movequeen(selectedPiece, targetedPiece, boardstate);
                            // Check if still in check, if not, do move
                            if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                chessboard.setBoard(boardstate);
                                chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                chessboard.changePlayer();
                                chessboard.changeclicknull();
                                staleCountWhite=8;
                                staleCountBlack=8;
                                // If still in check, undo move
                            }
                            else{
                                chessboard.changeclicknull();
                                chessboard.setBoard(oldstate);
                                Logics.flipcheck();

                                // Stalemate check
                                if(selectedPiece.type()==1){
                                    staleCountWhite--;
                                }
                                if(selectedPiece.type()==2){
                                    staleCountBlack--;
                                }
                            }
                            if(staleCountWhite==0 || staleCountBlack==0){
                                stale=true;
                            }
                        }
                    }
                    else{

                        // Stalemate check
                        if(selectedPiece.type()==1){
                            staleCountWhite--;
                        }
                        if(selectedPiece.type()==2){
                            staleCountBlack--;
                        }
                        if(staleCountWhite==0 || staleCountBlack==0){
                            stale=true;
                        }
                    }
                }

                // If rook selected ..
                if(selectedPiece.toString().equals("Rook") && selectedPiece != null && targetedPiece != null && selectedPiece.equals(targetedPiece)==false){
                    if(chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.CORNFLOWERBLUE) || chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.AQUAMARINE)==true){
                        // If check is false
                        if(!Logics.checkstatus()){
                            Piece[][] oldstate = new Piece[8][8];
                            // Transfer pieces to backup variable
                            for(int x=0;x < 8; x++){
                                for(int y=0; y < 8; y++){
                                    oldstate[x][y] = boardstate[x][y];
                                }
                            }
                            // Do move
                            boardstate = selectedPiece.moverook(selectedPiece, targetedPiece, boardstate);
                            // If move results in no check, do move
                            if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                chessboard.setBoard(boardstate);
                                chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
//                                message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                chessboard.changePlayer();
                                chessboard.changeclicknull();
                                staleCountWhite=8;
                                staleCountBlack=8;
                            }
                            // If check, reverse move
                            else{
                                //message = null;
                                chessboard.changeclicknull();
                                chessboard.setBoard(oldstate);
                                Logics.flipcheck();

                                // Stalemate check
                                if(selectedPiece.type()==1){
                                    staleCountWhite--;
                                }
                                if(selectedPiece.type()==2){
                                    staleCountBlack--;
                                }
                                if(staleCountWhite==0 || staleCountBlack==0){
                                    stale=true;
                                }
                            }
                        }

                        // If in check ..
                        if(Logics.checkstatus()){
                            // Do move
                            Piece[][] oldstate = new Piece[8][8];
                            for(int x=0;x < 8; x++){
                                for(int y=0; y < 8; y++){
                                    oldstate[x][y] = boardstate[x][y];
                                }
                            }
                            boardstate = selectedPiece.moverook(selectedPiece, targetedPiece, boardstate);
                            // Check if still in check, if not, do move
                            if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                chessboard.setBoard(boardstate);
                                chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                chessboard.changePlayer();
                                chessboard.changeclicknull();
                                staleCountWhite=8;
                                staleCountBlack=8;
                                // If still in check, undo move
                            }
                            else{
                                chessboard.changeclicknull();
                                chessboard.setBoard(oldstate);
                                Logics.flipcheck();

                                // Stalemate check
                                if(selectedPiece.type()==1){
                                    staleCountWhite--;
                                }
                                if(selectedPiece.type()==2){
                                    staleCountBlack--;
                                }
                                if(staleCountWhite==0 || staleCountBlack==0){
                                    stale=true;
                                }
                            }
                        }
                    }
                    else{

                        // Stalemate check
                        if(selectedPiece.type()==1){
                            staleCountWhite--;
                        }
                        if(selectedPiece.type()==2){
                            staleCountBlack--;
                        }
                        if(staleCountWhite==0 || staleCountBlack==0){
                            stale=true;
                        }
                    }
                }

                // If king selected ..
                if(selectedPiece.toString().equals("King") && selectedPiece != null && targetedPiece != null && !selectedPiece.equals(targetedPiece)){
                    if(chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.CORNFLOWERBLUE) || chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.AQUAMARINE)){
                        // If check is false
                        if(!Logics.checkstatus()){
                            Piece[][] oldstate = new Piece[8][8];
                            // Transfer pieces to backup variable
                            for(int x=0;x < 8; x++){
                                for(int y=0; y < 8; y++){
                                    oldstate[x][y] = boardstate[x][y];
                                }
                            }
                            // Do move
                            boardstate = selectedPiece.moveking(selectedPiece, targetedPiece, boardstate);
                            // If move results in no check, do move
                            if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                chessboard.setBoard(boardstate);
                                chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                chessboard.changePlayer();
                                chessboard.changeclicknull();
                                staleCountWhite=8;
                                staleCountBlack=8;
                            }
                            // If check, reverse move
                            else{
                                //message = null;
                                chessboard.changeclicknull();
                                chessboard.setBoard(oldstate);
                                Logics.flipcheck();

                                // Stalemate check
                                if(selectedPiece.type()==1){
                                    staleCountWhite--;
                                }
                                if(selectedPiece.type()==2){
                                    staleCountBlack--;
                                }
                                if(staleCountWhite==0 || staleCountBlack==0){
                                    stale=true;
                                }
                            }
                        }

                        // If in check ..
                        if(Logics.checkstatus()){
                            // Do move
                            Piece[][] oldstate = new Piece[8][8];
                            for(int x=0;x < 8; x++){
                                for(int y=0; y < 8; y++){
                                    oldstate[x][y] = boardstate[x][y];
                                }
                            }
                            boardstate = selectedPiece.moveking(selectedPiece, targetedPiece, boardstate);
                            // Check if still in check, if not, do move
                            if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                chessboard.setBoard(boardstate);
                                chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                chessboard.changePlayer();
                                chessboard.changeclicknull();
                                staleCountWhite=8;
                                staleCountBlack=8;
                                // If still in check, undo move
                            }
                            else{
                                //message = null;
                                chessboard.changeclicknull();
                                chessboard.setBoard(oldstate);
                                Logics.flipcheck();

                                // Stalemate check
                                if(selectedPiece.type()==1){
                                    staleCountWhite--;
                                }
                                if(selectedPiece.type()==2){
                                    staleCountBlack--;
                                }
                                if(staleCountWhite==0 || staleCountBlack==0){
                                    System.out.print("STALEMATE FOUND!!\n");
                                    stale=true;
                                }
                            }
                        }
                    }
                    else{

                        // Stalemate check
                        if(selectedPiece.type()==1){
                            staleCountWhite--;
                        }
                        if(selectedPiece.type()==2){
                            staleCountBlack--;
                        }
                        if(staleCountWhite==0 || staleCountBlack==0){
                            stale=true;
                        }
                    }
                }

                // If knight selected ..
                if(selectedPiece.toString().equals("Knight") && selectedPiece != null && targetedPiece != null && !selectedPiece.equals(targetedPiece)){
                    if(chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.CORNFLOWERBLUE) || chessboard.getStroke(targetedPiece.icoord(), targetedPiece.jcoord(), Color.AQUAMARINE)){
                        // If check is false
                        if(!Logics.checkstatus()){
                            Piece[][] oldstate = new Piece[8][8];
                            // Transfer pieces to backup variable
                            for(int x=0;x < 8; x++){
                                for(int y=0; y < 8; y++){
                                    oldstate[x][y] = boardstate[x][y];
                                }
                            }
                            // Do move
                            boardstate = selectedPiece.moveknight(selectedPiece, targetedPiece, boardstate);
                            // If move results in no check, do move
                            if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                chessboard.setBoard(boardstate);
                                chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                chessboard.changePlayer();
                                chessboard.changeclicknull();
                                staleCountWhite=8;
                                staleCountBlack=8;
                            }
                            // If check, reverse move
                            else{
                                //message = null;
                                chessboard.changeclicknull();
                                chessboard.setBoard(oldstate);
                                Logics.flipcheck();

                                // Stalemate check
                                if(selectedPiece.type()==1){
                                    staleCountWhite--;
                                }
                                if(selectedPiece.type()==2){
                                    staleCountBlack--;
                                }
                                if(staleCountWhite==0 || staleCountBlack==0){
                                    stale=true;
                                }
                            }
                        }

                        // If in check ..
                        if(Logics.checkstatus()){
                            // Do move
                            Piece[][] oldstate = new Piece[8][8];
                            for(int x=0;x < 8; x++){
                                for(int y=0; y < 8; y++){
                                    oldstate[x][y] = boardstate[x][y];
                                }
                            }
                            boardstate = selectedPiece.moveknight(selectedPiece, targetedPiece, boardstate);
                            // Check if still in check, if not, do move
                            if(!Logics.check4check(chessboard.otherplayer(), boardstate)){
                                chessboard.setBoard(boardstate);
                                chessboard.drawMove(selectedPiece.icoord(), selectedPiece.jcoord(), targetedPiece.icoord(), targetedPiece.jcoord());
                                message = selectedPiece.icoord()+" "+selectedPiece.jcoord()+" "+targetedPiece.icoord()+" "+targetedPiece.jcoord();
                                chessboard.changePlayer();
                                chessboard.changeclicknull();
                                staleCountWhite=8;
                                staleCountBlack=8;
                                // If still in check, undo move
                            }
                            else{
                                chessboard.changeclicknull();
                                chessboard.setBoard(oldstate);
                                Logics.flipcheck();

                                // Stalemate check
                                if(selectedPiece.type()==1){
                                    staleCountWhite--;
                                }
                                if(selectedPiece.type()==2){
                                    staleCountBlack--;
                                }
                                if(staleCountWhite==0 || staleCountBlack==0){
                                    System.out.print("STALEMATE FOUND!!\n");
                                    stale=true;
                                }
                            }
                        }
                    }
                    else{

                        // Stalemate check
                        if(selectedPiece.type()==WHITE_PLAYER){
                            staleCountWhite--;
                        }
                        if(selectedPiece.type()==BLACK_PLAYER){
                            staleCountBlack--;
                        }
                        if(staleCountWhite==0 || staleCountBlack==0){
                            stale=true;
                        }
                    }
                }

                else{
                    chessboard.changeclicknull();
                    chessboard.clearHighLights();
                }

                chessboard.clearHighLights();
                chessboard.changeclicknull();

                // Check for checkmate ..
                if(Logics.ischeckMateOccurs(chessboard.otherplayer(), chessboard.getState())=="true"){
                    winner=true;
                }

                getScene().setCursor(Cursor.DEFAULT);

                // highlight check..

                if(Logics.checkstatus()){
                    chessboard.checkHighLight(Logics.checkCoordI(), Logics.checkCoordJ());
                    chessboard.changeclicknull();
                }
            }

            if(chessboard.getClicklogic().equals("false") && !stale && !winner){
                selectedPiece = chessboard.selectPiece(si,sj);

                if(selectedPiece.toString().equals("Empty") || !chessboard.pieceselect()){
                    isJunkSelected=true;
                }
                else{isJunkSelected=false;}

                if(!selectedPiece.equals("Empty") && !isJunkSelected){
                    getScene().setCursor(new ImageCursor(selectedPiece.image()));
                    chessboard.changeclicktrue();
                    // Highlights valid moves..
                    chessboard.validMoves(selectedPiece);}

                // Check 4 check .....for otherPlayer//he will be the current Player
                if(!Logics.checkstatus()){
                    Logics.check4check(chessboard.otherplayer(), chessboard.getState());}
            }

            // If completed move, return to first click ..
            if(chessboard.getClicklogic().equals("null")){
                isMyTurn = true;
//                System.out.print(message);
                chessboard.changeclickfalse();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Piece counting -- could expand on this but only need total number
    public int whitepieces(Piece[][] boardstate){
        int whitepieces=0;

        // Count white pieces
        for(int x=0; x < 8; x++){
            for(int y=0; y < 8; y++){
                if(boardstate[x][y].type()==1){
                    whitepieces++;
                }
            }
        }
        // Return int
        return whitepieces;
    }

    public int blackpieces(Piece[][] boardstate){
        int blackpieces=0;

        // Count white pieces
        for(int x=0; x < 8; x++){
            for(int y=0; y < 8; y++){
                if(boardstate[x][y].type()==2){
                    blackpieces++;
                }
            }
        }
        // Return int
        return blackpieces;
    }

    public void highlightcheck(int x, int y){
        chessboard.checkHighLight(x,y);
    }

    @Override
    public void resize(double width, double height){
        super.resize(width, height);
        chessboard.resize(width, height);
    }

    @Override
    public void relocate(double x, double y){
        super.relocate(x, y);
        pos.setX(x);
        pos.setY(x);
    }
}