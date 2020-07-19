package sample.GameChess.PlayGame.Controllers;

import javafx.scene.transform.Translate;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import sample.GameChess.PlayGame.PieceClasses.*;

public class BoardImplementation extends Pane {

	public static final int WHITE_PLAYER = 1,BLACK_PLAYER = 2,EMPTY_PLAYER = 0;

    public static int current_player = WHITE_PLAYER;

	public static int getCurrent_player() {
		return current_player;
	}

	public static void setCurrent_player(int current_player) {
		BoardImplementation.current_player = current_player;
	}

	public static final int PlayerWhite = 1;
    public static final int PlayerBlack = 2;
    public static final int Empty = 0;
    private boolean pieceSelect = false;
    private boolean winner = false;
    private String clicklogic="false";

	
	private Piece[][] pieces;
	private Rectangle[][] board;
	private Image[][] images;
	private ImageView[][] imageviews;
    private Translate position;

	public String getClicklogic(){ return this.clicklogic; }
	public void changeclicktrue(){ this.clicklogic = "true"; }
	public void changeclickfalse(){ this.clicklogic = "false"; }
	public void changeclicknull(){ this.clicklogic = "null"; }

	public int currentplayer(){ return this.current_player; }
	
	public int otherplayer(){
		if(current_player == PlayerWhite){ return PlayerBlack; }
		return PlayerWhite;
	}

	//return true if any  piece is selected by mouse
	public boolean pieceselect(){ return (boolean) this.pieceSelect; }

	public Rectangle[][] getBoard() {
		return this.board;
	}

	public void setBoard(Rectangle[][] board) {
		this.board = board;
	}

	public ImageView[][] getImageviews() {
		return this.imageviews;
	}

	public void setImageviews(ImageView[][] imageviews) {
		this.imageviews = imageviews;
	}

	// Reset imageViews array  MAYBE not used only one in GameControl Handler
	public void resetGame(){
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
			imageviews[x][y].setImage(images[x][y]);
			}
		}
	}

	//essential
    public void placeBoard(final int i, final int j){
        getChildren().add(board[i][j]);
    }

    //essential
    public void placeImageViews(final int i, final int j){
        getChildren().addAll(imageviews[i][j]);
    }

    // Returns stroke of board piece helps checking in checkMate
    public boolean getStroke(final int i, final int j, Paint color)
    { 
        return (board[i][j].getStroke() == color);
    }

    //Constructor of ChessBoard
    public BoardImplementation() {

        position = new Translate();

        // Declares new board
        int boardWidth = 8;
        int boardHeight = 8;
        board = new Rectangle[boardWidth][boardHeight];//8*8 size 2D array

        // Initializes new Chess Board first time
        for(int x=0; x < 8; x++){
            for(int j=0; j < 8; j++){
                board[x][j] = new Rectangle(50,50);
                board[x][j].setStroke(Color.TRANSPARENT);
                board[x][j].setStrokeType(StrokeType.INSIDE);
                board[x][j].setStrokeWidth(1);
                // Generates colours for the chessboard backgrounds
                if((x+j)%2==0)board[x][j].setFill(Color.WHITE);
                else board[x][j].setFill(Color.CHOCOLATE);
            }
        }


        // New image array
        images = new Image[8][8];

            // first row of renders (black)
            images[7][0] = new Image("sample/CursorPng/blackrook.png"); images[6][0] = new Image("sample/CursorPng/blackknight.png");
            images[5][0] = new Image("sample/CursorPng/blackbishop.png"); images[4][0] = new Image("sample/CursorPng/blackking.png");
            images[3][0] = new Image("sample/CursorPng/blackqueen.png"); images[2][0] = new Image("sample/CursorPng/blackbishop.png");
            images[1][0] = new Image("sample/CursorPng/blackknight.png"); images[0][0] = new Image("sample/CursorPng/blackrook.png");
            // second row (black)
            images[7][1] = new Image("sample/CursorPng/blackpawn.png"); images[6][1] = new Image("sample/CursorPng/blackpawn.png");
            images[5][1] = new Image("sample/CursorPng/blackpawn.png"); images[4][1] = new Image("sample/CursorPng/blackpawn.png");
            images[3][1] = new Image("sample/CursorPng/blackpawn.png"); images[2][1] = new Image("sample/CursorPng/blackpawn.png");
            images[1][1] = new Image("sample/CursorPng/blackpawn.png"); images[0][1] = new Image("sample/CursorPng/blackpawn.png");

            /**intentionally
             * made null* */
            // empty squares
            for(int x = 0; x < 8; x++){
                for(int y = 2; y < 6; y++){
                    //images[x][y] = new Image("sample/CursorPng/empty.png");
                    images[x][y] = null;
                }
            }

            // third row (white)
            images[7][6] = new Image("sample/CursorPng/whitepawn.png"); images[6][6] = new Image("sample/CursorPng/whitepawn.png");
            images[5][6] = new Image("sample/CursorPng/whitepawn.png"); images[4][6] = new Image("sample/CursorPng/whitepawn.png");
            images[3][6] = new Image("sample/CursorPng/whitepawn.png"); images[2][6] = new Image("sample/CursorPng/whitepawn.png");
            images[1][6] = new Image("sample/CursorPng/whitepawn.png"); images[0][6] = new Image("sample/CursorPng/whitepawn.png");
            // fourth row of renders (white)
            images[7][7] = new Image("sample/CursorPng/whiterook.png"); images[6][7] = new Image("sample/CursorPng/whiteknight.png");
            images[5][7] = new Image("sample/CursorPng/whitebishop.png"); images[4][7] = new Image("sample/CursorPng/whiteking.png");
            images[3][7] = new Image("sample/CursorPng/whitequeen.png"); images[2][7] = new Image("sample/CursorPng/whitebishop.png");
            images[1][7] = new Image("sample/CursorPng/whiteknight.png"); images[0][7] = new Image("sample/CursorPng/whiterook.png");

            // Viewers for each image
            imageviews = new ImageView[8][8];

            // Initializes imageView and windows and init it
            for(int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    imageviews[x][y] = new ImageView(images[x][y]);
                    imageviews[x][y].setFitWidth(50);
                    imageviews[x][y].setFitHeight(80);
                    imageviews[x][y].setPreserveRatio(true);
                    imageviews[x][y].setSmooth(true);
                    imageviews[x][y].setCache(true);
                    imageviews[x][y].setTranslateX(board[x][y].getWidth() / 8);
                }
            }

            //initialize the board: background, data structures, initial layout of pieces
            pieces = new Piece[boardWidth][boardHeight];

            // White Pieces
            pieces[7][7] = new PieceRook(PlayerWhite, 7, 7);
            pieces[6][7] = new PieceKnight(PlayerWhite, 6, 7);
            pieces[5][7] = new PieceBishop(PlayerWhite, 5, 7);
            pieces[4][7] = new PieceKing(PlayerWhite, 4, 7);
            pieces[3][7] = new PieceQueen(PlayerWhite, 3, 7);
            pieces[2][7] = new PieceBishop(PlayerWhite, 2, 7);
            pieces[1][7] = new PieceKnight(PlayerWhite, 1, 7);
            pieces[0][7] = new PieceRook(PlayerWhite, 0, 7);
            // Pawns
            for(int i = 0; i < 8;i++)
                pieces[i][6]  = new PiecePawn(PlayerWhite, i,6,true);

            // Black Pieces
            pieces[7][0] = new PieceRook(PlayerBlack, 7, 0);
            pieces[6][0] = new PieceKnight(PlayerBlack, 6, 0);
            pieces[5][0] = new PieceBishop(PlayerBlack, 5, 0);
            pieces[4][0] = new PieceKing(PlayerBlack, 4, 0);
            pieces[3][0] = new PieceQueen(PlayerBlack, 3, 0);
            pieces[2][0] = new PieceBishop(PlayerBlack, 2, 0);
            pieces[1][0] = new PieceKnight(PlayerBlack, 1, 0);
            pieces[0][0] = new PieceRook(PlayerBlack, 0, 0);
            // Pawns
            for(int i = 0; i < 8;i++)
                pieces[i][1]  = new PiecePawn(PlayerBlack, i,1,true);

            // Empty Pieces
            for(int x = 5; x > 1; x--){
                for(int j = 0; j < 8; j++){
                    pieces[j][x] = new Empty(Empty, j, x);
                }
            }

            current_player = PlayerWhite;
    }



	public void checkHighLight(int x, int y){
		board[x][y].setStroke(Color.RED);
	}

	//resize method
	@Override
	public void resize(double width, double height){

		super.resize(width, height);
        double cell_width = width / 8;
        double cell_height = height / 8;

		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				board[i][j].resize(i* cell_width, j* cell_height);
				board[i][j].relocate(i* cell_width, j* cell_height);
				board[i][j].setStrokeWidth(cell_width /14);//size of movable box boundaries
				board[i][j].setWidth(cell_width);
				board[i][j].setHeight(cell_height);

				//Image/Shapes of Black Royalty, White Royalty and Norm Royalty
				imageviews[i][j].resize(cell_width /8, cell_height /8);
				imageviews[i][j].relocate(i* cell_width, j* cell_height);
				imageviews[i][j].setFitWidth(cell_width /1.25);
				imageviews[i][j].setFitHeight(cell_height /1.25);
				imageviews[i][j].setTranslateX(board[i][j].getWidth() / 8);
			}
		}
	}

	@Override
	public void relocate(double x, double y){
		super.relocate(x, y);
		position.setX(x);
		position.setY(x);
	}

	public Piece selectTarget(int ti , int tj){

		int i  = ti, j = tj;

		int enemyPlayer;

		if(current_player == PlayerWhite){
			enemyPlayer = PlayerBlack;
		}
		else{enemyPlayer = PlayerWhite;}

		if( !winner ){
			if(pieces[i][j].type() == 0 || pieces[i][j].type() == enemyPlayer){
			return pieces[i][j];
			}
		}
		return pieces[i][j];
	}

	//select piece method
	public Piece selectPiece(int si, int sj){
		// Determine what piece was selected and if it can be selected

		int i = si, j = sj;

		if(current_player == PlayerWhite && !winner){
			if(pieces[i][j].type() == PlayerWhite){
				// If player has already selected the piece, deselect it
				if(board[i][j].getStroke() == Color.LIGHTCORAL && pieceSelect){

				    if((i+j)%2==0){
					    board[i][j].setFill(Color.WHITE);
					    pieceSelect = false;
                    }
                    else{
					    board[i][j].setFill(Color.CHOCOLATE);
					    pieceSelect = false;
                    }
				}
				// Otherwise select it and work out moves
				else{
				board[i][j].setStroke(Color.LIGHTCORAL);
				pieceSelect = true;
				return pieces[i][j];
				}
			}
		}

		// If current player is black
			else{
			if(pieces[i][j].type() == PlayerBlack){
				//if already selected it then just deselect
				if(board[i][j].getStroke() == Color.LIGHTCORAL && pieceSelect){
						// Resets color
                    if((i+j)%2==0){
                        board[i][j].setFill(Color.WHITE);
                        pieceSelect = false;
                    }
                    else{
                        board[i][j].setFill(Color.CHOCOLATE);
                        pieceSelect = false;
                    }
				}
				//else select and return make stroke also
				else{
				board[i][j].setStroke(Color.LIGHTCORAL);
				pieceSelect = true;
				return pieces[i][j];
				}
			}
		}

		return new Empty(0,i,j);
	}

	// Draw the move and remove highlights
	public void drawMove(final int si, int sj, int ti, int tj){
		Image empty = null;
		String piece = "black";
		if(pieces[ti][tj].type() == PlayerWhite){ piece = "white"; }

		piece = "sample/CursorPng/"+ piece + pieces[ti][tj].imagefilename();

		imageviews[ti][tj].setImage(new Image(piece));
		imageviews[si][sj].setImage(empty);
		// Remove highlight
		if(board[si][sj].getStroke() == Color.LIGHTCORAL && pieceSelect){
            if((si+sj)%2==0) board[si][sj].setFill(Color.WHITE);
            else board[si][sj].setFill(Color.CHOCOLATE);
		}
		else if(!pieceSelect){
		board[si][sj].setStroke(Color.LIGHTCORAL);
		pieceSelect = true;
		}
	}


	//after every move it will update the board
	public void setBoard(Piece[][] newBoard){
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
				pieces[x][y] = newBoard[x][y];
			}
		}
	}

	//we also use it in custom_control
	public void hoverhighlight(int x, int y){
		int i=x; int j=y;
		// Set highlight color
		board[i][j].setStroke(Color.BURLYWOOD);
	}

	public void clearHighLights(){
		for(int x=0; x < 8; x++){
			for(int j=0; j < 8; j++){
				board[x][j].setStroke(Color.TRANSPARENT);
				if((x+j)%2==0) board[x][j].setFill(Color.WHITE);
				else board[x][j].setFill(Color.CHOCOLATE);
			}
		}
	}

	//we have to call it through networking
	public void changePlayer(){
		if(current_player == PlayerWhite){current_player = PlayerBlack;}
		else{current_player = PlayerWhite;}
	}

    // Returns state of the chess board ..
    public Piece[][] getState(){
        return this.pieces;
    }

	//just setStroke of valid squares(called in line: 756 in CustomControl)
	public void validMoves(Piece p){

		if(p.toString() == "Pawn" && p.type() == PlayerWhite){
			// LOOK ONE SQUARE AHEAD IF CLEAR HIGHLIGHT
			if(p.jcoord()-1 >= 0){ // Guard for bounds
			if(pieces[p.icoord()][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
			}}

			if(p.firstmove() == true){
			// LOOK TWO SQUARE AHEAD IF CLEAR HIGHLIGHT
			if(pieces[p.icoord()][p.jcoord()-2].toString().equals("Empty") && pieces[p.icoord()][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()][p.jcoord()-2].setStroke(Color.CORNFLOWERBLUE);
			}}

			// LOOK ONE SQUARE LEFT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
			if(p.icoord()-1 >= 0 && p.jcoord()-1 >= 0){
			if(pieces[p.icoord()-1][p.jcoord()-1].type() == 2){
				board[p.icoord()-1][p.jcoord()-1].setStroke(Color.AQUAMARINE);
			}}

			// LOOK ONE SQUARE RIGHT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
			if(p.icoord()+1 < 8 && p.jcoord()-1 >= 0){
			if(pieces[p.icoord()+1][p.jcoord()-1].type() == 2){
				board[p.icoord()+1][p.jcoord()-1].setStroke(Color.AQUAMARINE);
			}}
		}

			if(p.toString() == "Pawn" && p.type() == 2){
				// LOOK ONE SQUARE AHEAD IF CLEAR HIGHLIGHT
				if(p.jcoord()+1 < 8){ // Guard for bounds
				if(pieces[p.icoord()][p.jcoord()+1].toString().equals("Empty")){
					board[p.icoord()][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}}

				if(p.firstmove()){
				// LOOK TWO SQUARE AHEAD IF CLEAR HIGHLIGHT
				if(pieces[p.icoord()][p.jcoord()+2].toString().equals("Empty") && pieces[p.icoord()][p.jcoord()+1].toString().equals("Empty")){
					board[p.icoord()][p.jcoord()+2].setStroke(Color.CORNFLOWERBLUE);
				}}

				// LOOK ONE SQUARE LEFT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
				if(p.icoord()-1 >= 0 && p.jcoord()+1 < 8){
				if(pieces[p.icoord()-1][p.jcoord()+1].type() == 1){
					board[p.icoord()-1][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}}

				// LOOK ONE SQUARE RIGHT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
				if(p.icoord()+1 < 8 && p.jcoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()+1].type() == 1){
					board[p.icoord()+1][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}}
			}

			if(p.toString() == "Rook" && p.type() == 1){
				// Look Up ..
				for(int y = p.jcoord()-1; y >= 0; y--){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()][y].type()==2){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=-1;
					}
					if(y!=-1 && pieces[p.icoord()][y].type()==1){
					// Stop looking
					y=-1;
					}
				}

				// Look Right ..
				for(int x = p.icoord()+1; x < 8; x++){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==2){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=8;
					}
					if(x!=8 && pieces[x][p.jcoord()].type()==1){
					// Stop looking
					x=8;
					}
				}

				// Look Left ..
				for(int x = p.icoord()-1; x >= 0; x--){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==2){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=-1;
					}
					if(x!=-1 && pieces[x][p.jcoord()].type()==1){
					// Stop looking
					x=-1;
					}
				}

				// Look Down ..
				for(int y = p.jcoord()+1; y < 8; y++){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()][y].type()==2){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=8;
					}
					if(y!=8 && pieces[p.icoord()][y].type()==1 && y!=8){
					// Stop looking
					y=8;
					}
				}
			}

			if(p.toString() == "Rook" && p.type() == 2){
				// Look Up ..
				for(int y = p.jcoord()-1; y >= 0; y--){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()][y].type()==1){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=-1;
					}
					if(y!=-1 && pieces[p.icoord()][y].type()==2){
					// Stop looking
					y=-1;
					}
				}

				// Look Right ..
				for(int x = p.icoord()+1; x < 8; x++){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==1){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=8;
					}
					if(x!=8 && pieces[x][p.jcoord()].type()==2){
					// Stop looking
					x=8;
					}
				}

				// Look Left ..
				for(int x = p.icoord()-1; x >= 0; x--){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==1){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=-1;
					}
					if(x!=-1 && pieces[x][p.jcoord()].type()==2){
					// Stop looking
					x=-1;
					}
				}

				// Look Down ..
				for(int y = p.jcoord()+1; y < 8; y++){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()][y].type()==1){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=8;
					}
					if(y!=8 && pieces[p.icoord()][y].type()==2){
					// Stop looking
					y=8;
					}
				}
			}

			if(p.toString() == "Bishop" && p.type() == 2){
				// Look up .. (left)
				for(int y=p.jcoord()-1, x=p.icoord()-1; y >= 0 && x >= 0; y--,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1; y=-1;
					}
					if(x!=-1 && y!=-1 && pieces[x][y].type()==2){
					// Stop Looking
					x=-1; y=-1;
					}
				}

				// Look up .. (right)
				for(int y=p.jcoord()-1, x=p.icoord()+1; y >= 0 && x < 8; y--,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8;y=-1;
					}
					if(x!=8 && y!=-1 && pieces[x][y].type()==2){
					// Stop Looking
					x=8;y = -1;
					}
				}

				// Look down .. (left)
				for(int y=p.jcoord()+1, x=p.icoord()-1; y < 8 && x >= 0; y++,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x= -1; y = 8;
					}
					if(x!=-1 && y!=8 && pieces[x][y].type()==2){
					// Stop Looking
					x = -1; y = 8;
					}
				}

				// Look down .. (right)
				for(int y=p.jcoord()+1, x=p.icoord()+1; y < 8 && x < 8; y++,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8;y=8;
					}
					if(x!=8 && y!=8 && pieces[x][y].type()==2){
					// Stop Looking
					x=8;y=8;
					}
				}
			}

		if(p.toString() == "Bishop" && p.type() == 1){
			// Look up .. (left)
			for(int y=p.jcoord()-1, x=p.icoord()-1; y >= 0 && x >= 0; y--,x--){
				if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1; y=-1;
				}
				if(x!=-1 && y!=-1 && pieces[x][y].type()==1){
					// Stop Looking
					x=-1; y=-1;
				}
			}

			// Look up .. (right)
			for(int y=p.jcoord()-1, x=p.icoord()+1; y >= 0 && x < 8; y--,x++){
				if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8; y=-1;
				}
				if(x!=8 && y!=-1 && pieces[x][y].type()==1){
					// Stop Looking
					x=8; y=-1;
				}
			}

			// Look down .. (left)
			for(int y=p.jcoord()+1, x=p.icoord()-1; y < 8 && x >= 0; y++,x--){
				if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1; y=8;
				}
				if(x!=-1 && y!=8 && pieces[x][y].type()==1){
					// Stop Looking
					x=-1; y=8;
				}
			}

			// Look down .. (right)
			for(int y=p.jcoord()+1, x=p.icoord()+1; y < 8 && x < 8; y++,x++){
				if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8;y=8;
				}
				if(x!=8 && y!=8 && pieces[x][y].type()==1){
					// Stop Looking
					x=8;y=8;
				}
			}
		}

			if(p.toString() == "Knight" && p.type() == 1){
				// Up and left (first)
				if(p.icoord()-1 >= 0 && p.jcoord()-2 >= 0){
					if(pieces[p.icoord()-1][p.jcoord()-2].toString().equals("Empty")){
						board[p.icoord()-1][p.jcoord()-2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-1][p.jcoord()-2].type()==2){
						board[p.icoord()-1][p.jcoord()-2].setStroke(Color.AQUAMARINE);
					}
				}

				// Up and left (second)
				if(p.icoord()-2 >= 0 && p.jcoord()-1 >= 0){				// Bound check
					if(pieces[p.icoord()-2][p.jcoord()-1].toString().equals("Empty")){
						board[p.icoord()-2][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-2][p.jcoord()-1].type()==2){
						board[p.icoord()-2][p.jcoord()-1].setStroke(Color.AQUAMARINE);
					}
				}

				// Up and right (first)
				if(p.icoord()+1 < 8 && p.jcoord()-2 >= 0){				// Bound check
					if(pieces[p.icoord()+1][p.jcoord()-2].toString().equals("Empty")){
						board[p.icoord()+1][p.jcoord()-2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+1][p.jcoord()-2].type()==2){
						board[p.icoord()+1][p.jcoord()-2].setStroke(Color.AQUAMARINE);
					}
				}

				// Up and right (second)
				if(p.icoord()+2 < 8 && p.jcoord()-1 >= 0){				// Bound check
					if(pieces[p.icoord()+2][p.jcoord()-1].toString().equals("Empty")){
						board[p.icoord()+2][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+2][p.jcoord()-1].type()==2){
						board[p.icoord()+2][p.jcoord()-1].setStroke(Color.AQUAMARINE);
					}
				}

				// Bottom and right (first)
				if(p.icoord()+1 < 8 && p.jcoord()+2 < 8){				// Bound check
					if(pieces[p.icoord()+1][p.jcoord()+2].toString().equals("Empty")){
						board[p.icoord()+1][p.jcoord()+2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+1][p.jcoord()+2].type()==2){
						board[p.icoord()+1][p.jcoord()+2].setStroke(Color.AQUAMARINE);
					}
				}

				// Bottom and right (second)
				if(p.icoord()+2 < 8 && p.jcoord()+1 < 8){				// Bound check
					if(pieces[p.icoord()+2][p.jcoord()+1].toString().equals("Empty")){
						board[p.icoord()+2][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+2][p.jcoord()+1].type()==2){
						board[p.icoord()+2][p.jcoord()+1].setStroke(Color.AQUAMARINE);
					}
				}

				// Bottom and left (first)
				if(p.icoord()-1 >= 0 && p.jcoord()+2 < 8){				// Bound check
					if(pieces[p.icoord()-1][p.jcoord()+2].toString().equals("Empty")){
						board[p.icoord()-1][p.jcoord()+2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-1][p.jcoord()+2].type()==2){
						board[p.icoord()-1][p.jcoord()+2].setStroke(Color.AQUAMARINE);
					}
				}

				// Bottom and left (second)
				if(p.icoord()-2 >= 0 && p.jcoord()+1 < 8){				// Bound check
					if(pieces[p.icoord()-2][p.jcoord()+1].toString().equals("Empty")){
						board[p.icoord()-2][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-2][p.jcoord()+1].type()==2){
						board[p.icoord()-2][p.jcoord()+1].setStroke(Color.AQUAMARINE);
					}
				}
			}

			//_________________________________BLACKKNIGHT_____________________________________//
			if(p.toString() == "Knight" && p.type() == 2){
				// Up and left (first)
				if(p.icoord()-1 >= 0 && p.jcoord()-2 >= 0){				// Bound check
					if(pieces[p.icoord()-1][p.jcoord()-2].toString().equals("Empty")){
						board[p.icoord()-1][p.jcoord()-2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-1][p.jcoord()-2].type()==1){
						board[p.icoord()-1][p.jcoord()-2].setStroke(Color.AQUAMARINE);
					}
				}

				// Up and left (second)
				if(p.icoord()-2 >= 0 && p.jcoord()-1 >= 0){				// Bound check
					if(pieces[p.icoord()-2][p.jcoord()-1].toString().equals("Empty")){
						board[p.icoord()-2][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-2][p.jcoord()-1].type()==1){
						board[p.icoord()-2][p.jcoord()-1].setStroke(Color.AQUAMARINE);
					}
				}

				// Up and right (first)
				if(p.icoord()+1 < 8 && p.jcoord()-2 >= 0){				// Bound check
					if(pieces[p.icoord()+1][p.jcoord()-2].toString().equals("Empty")){
						board[p.icoord()+1][p.jcoord()-2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+1][p.jcoord()-2].type()==1){
						board[p.icoord()+1][p.jcoord()-2].setStroke(Color.AQUAMARINE);
					}
				}

				// Up and right (second)
				if(p.icoord()+2 < 8 && p.jcoord()-1 >= 0){				// Bound check
					if(pieces[p.icoord()+2][p.jcoord()-1].toString().equals("Empty")){
						board[p.icoord()+2][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+2][p.jcoord()-1].type()==1){
						board[p.icoord()+2][p.jcoord()-1].setStroke(Color.AQUAMARINE);
					}
				}

				// Bottom and right (first)
				if(p.icoord()+1 < 8 && p.jcoord()+2 < 8){				// Bound check
					if(pieces[p.icoord()+1][p.jcoord()+2].toString().equals("Empty")){
						board[p.icoord()+1][p.jcoord()+2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+1][p.jcoord()+2].type()==1){
						board[p.icoord()+1][p.jcoord()+2].setStroke(Color.AQUAMARINE);
					}
				}

				// Bottom and right (second)
				if(p.icoord()+2 < 8 && p.jcoord()+1 < 8){				// Bound check
					if(pieces[p.icoord()+2][p.jcoord()+1].toString().equals("Empty")){
						board[p.icoord()+2][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+2][p.jcoord()+1].type()==1){
						board[p.icoord()+2][p.jcoord()+1].setStroke(Color.AQUAMARINE);
					}
				}

				// Bottom and left (first)
				if(p.icoord()-1 >= 0 && p.jcoord()+2 < 8){
					if(pieces[p.icoord()-1][p.jcoord()+2].toString().equals("Empty")){
						board[p.icoord()-1][p.jcoord()+2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-1][p.jcoord()+2].type()==1){
						board[p.icoord()-1][p.jcoord()+2].setStroke(Color.AQUAMARINE);
					}
				}

				// Bottom and left (second)
				if(p.icoord()-2 >= 0 && p.jcoord()+1 < 8){
					if(pieces[p.icoord()-2][p.jcoord()+1].toString().equals("Empty")){
						board[p.icoord()-2][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-2][p.jcoord()+1].type()==1){
						board[p.icoord()-2][p.jcoord()+1].setStroke(Color.AQUAMARINE);
					}
				}

			}

			if(p.toString() == "Queen" && p.type() == 1){
				// Look up .. (left)
				for(int y=p.jcoord()-1, x=p.icoord()-1; y >= 0 && x >= 0; y--,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1; y=-1;
					}
					if(x!=-1 && y!=-1 && pieces[x][y].type()==1){
					// Stop Looking
					x=-1; y=-1;
					}
				}

				// Look up .. (right)
				for(int y=p.jcoord()-1, x=p.icoord()+1; y >= 0 && x < 8; y--,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8; y=-1;
					}
					if(x!=8 && y!=-1 && pieces[x][y].type()==1){
					// Stop Looking
					x=8; y=-1;
					}
				}

				// Look down .. (left)
				for(int y=p.jcoord()+1, x=p.icoord()-1; y < 8 && x >= 0; y++,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1;y=8;
					}
					if(x!=-1 && y!=8 && pieces[x][y].type()==1){
					// Stop Looking
					x=-1;y=8;
					}
				}

				// Look down .. (right)
				for(int y=p.jcoord()+1, x=p.icoord()+1; y < 8 && x < 8; y++,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8;y=8;
					}
					if(x!=8 && y!=8 && pieces[x][y].type()==1){
					// Stop Looking
					x=8;y=8;
					}
				}

				// Look Up ..
				for(int y = p.jcoord()-1; y >= 0; y--){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()][y].type()==2){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=-1;
					}
					if(y!=-1 && pieces[p.icoord()][y].type()==1){
					// Stop looking
					y=-1;
					}
				}

				// Look Right ..
				for(int x = p.icoord()+1; x < 8; x++){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==2){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=8;
					}
					if(x!=8 && pieces[x][p.jcoord()].type()==1){
					// Stop looking
					x=8;
					}
				}

				// Look Left ..
				for(int x = p.icoord()-1; x >= 0; x--){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==2){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=-1;
					}
					if(x!=-1 && pieces[x][p.jcoord()].type()==1){
					// Stop looking
					x=-1;
					}
				}

				// Look Down ..
				for(int y = p.jcoord()+1; y < 8; y++){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()][y].type()==2){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=8;
					}
					if(y!=8 && pieces[p.icoord()][y].type()==1 && y!=8){
					// Stop looking
					y=8;
					}
				}
			}

			if(p.toString() == "Queen" && p.type() == 2){
				// Look up .. (left)
				for(int y=p.jcoord()-1, x=p.icoord()-1; y >= 0 && x >= 0; y--,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1;y=-1;
					}
					if(x!=-1 && y!=-1 && pieces[x][y].type()==2){
					// Stop Looking
					x=-1;y=-1;
					}
				}

				// Look up .. (right)
				for(int y=p.jcoord()-1, x=p.icoord()+1; y >= 0 && x < 8; y--,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8;y=-1;
					}
					if(x!=8 && y!=-1 && pieces[x][y].type()==2){
					// Stop Looking
					x=8;y=-1;
					}
				}

				// Look down .. (left)
				for(int y=p.jcoord()+1, x=p.icoord()-1; y < 8 && x >= 0; y++,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1;y=8;
					}
					if(x!=-1 && y!=8 && pieces[x][y].type()==2){
					// Stop Looking
					x=-1;y=8;
					}
				}

				// Look down .. (right)
				for(int y=p.jcoord()+1, x=p.icoord()+1; y < 8 && x < 8; y++,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8;y=8;
					}
					if(x!=8 && y!=8 && pieces[x][y].type()==2){
					// Stop Looking
					x=8;y=8;
					}
				}

				// Look Up ..
				for(int y = p.jcoord()-1; y >= 0; y--){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()][y].type()==1){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=-1;
					}
					if(y!=-1 && pieces[p.icoord()][y].type()==2){
					// Stop looking
					y=-1;
					}
				}

				// Look Right ..
				for(int x = p.icoord()+1; x < 8; x++){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==1){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=8;
					}
					if(x!=8 && pieces[x][p.jcoord()].type()==2){
					// Stop looking
					x=8;
					}
				}

				// Look Left ..
				for(int x = p.icoord()-1; x >= 0; x--){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==1){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=-1;
					}
					if(x!=-1 && pieces[x][p.jcoord()].type()==2){
					// Stop looking
					x=-1;
					}
				}

				// Look Down ..
				for(int y = p.jcoord()+1; y < 8; y++){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()][y].type()==1){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=8;
					}
					if(y!=8 && pieces[p.icoord()][y].type()==2 && y!=8){
					// Stop looking
					y=8;
					}
				}
			}

			if(p.toString() == "King" && p.type() == 1){
				// Up
				if(p.jcoord()-1 >= 0){
				if(pieces[p.icoord()][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()][p.jcoord()-1].type()==2){
				board[p.icoord()][p.jcoord()-1].setStroke(Color.AQUAMARINE);
				}
				}

				// Up - right
				if(p.jcoord()-1 >= 0 && p.icoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()+1][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()+1][p.jcoord()-1].type()==2){
				board[p.icoord()+1][p.jcoord()-1].setStroke(Color.AQUAMARINE);
				}
				}

				// Up - left
				if(p.jcoord()-1 >= 0 && p.icoord()-1 >= 0){
				if(pieces[p.icoord()-1][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()-1][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()-1][p.jcoord()-1].type()==2){
				board[p.icoord()-1][p.jcoord()-1].setStroke(Color.AQUAMARINE);
				}
				}

				// Left
				if(p.icoord()-1 >= 0){
				if(pieces[p.icoord()-1][p.jcoord()].toString().equals("Empty")){
				board[p.icoord()-1][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()-1][p.jcoord()].type()==2){
				board[p.icoord()-1][p.jcoord()].setStroke(Color.AQUAMARINE);
				}
				}

				// Right
				if(p.icoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()].toString().equals("Empty")){
				board[p.icoord()+1][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()+1][p.jcoord()].type()==2){
				board[p.icoord()+1][p.jcoord()].setStroke(Color.AQUAMARINE);
				}
				}

				// Down
				if(p.jcoord()+1 < 8){
				if(pieces[p.icoord()][p.jcoord()+1].toString().equals("Empty")){
				board[p.icoord()][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()][p.jcoord()+1].type()==2){
				board[p.icoord()][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}
				}

				// Down - left
				if(p.jcoord()+1 < 8 && p.icoord()-1 >= 0){
				if(pieces[p.icoord()-1][p.jcoord()+1].toString().equals("Empty")){
				board[p.icoord()-1][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()-1][p.jcoord()+1].type()==2){
				board[p.icoord()-1][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}
				}

				// Down - right
				if(p.jcoord()+1 < 8 && p.icoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()+1].toString().equals("Empty")){
				board[p.icoord()+1][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()+1][p.jcoord()+1].type()==2){
				board[p.icoord()+1][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}
				}
			}

			//_________________________________BLACKKING_____________________________________//
			if(p.toString() == "King" && p.type() == 2){
				// Up
				if(p.jcoord()-1 >= 0){
				if(pieces[p.icoord()][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()][p.jcoord()-1].type()==1){
				board[p.icoord()][p.jcoord()-1].setStroke(Color.AQUAMARINE);
				}
				}

				// Up - right
				if(p.jcoord()-1 >= 0 && p.icoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()+1][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()+1][p.jcoord()-1].type()==1){
				board[p.icoord()+1][p.jcoord()-1].setStroke(Color.AQUAMARINE);
				}
				}

				// Up - left
				if(p.jcoord()-1 >= 0 && p.icoord()-1 >= 0){
				if(pieces[p.icoord()-1][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()-1][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()-1][p.jcoord()-1].type()==1){
				board[p.icoord()-1][p.jcoord()-1].setStroke(Color.AQUAMARINE);
				}
				}

				// Left
				if(p.icoord()-1 >= 0){
				if(pieces[p.icoord()-1][p.jcoord()].toString().equals("Empty")){
				board[p.icoord()-1][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()-1][p.jcoord()].type()==1){
				board[p.icoord()-1][p.jcoord()].setStroke(Color.AQUAMARINE);
				}
				}

				// Right
				if(p.icoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()].toString().equals("Empty")){
				board[p.icoord()+1][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()+1][p.jcoord()].type()==1){
				board[p.icoord()+1][p.jcoord()].setStroke(Color.AQUAMARINE);
				}
				}

				// Down
				if(p.jcoord()+1 < 8){
				if(pieces[p.icoord()][p.jcoord()+1].toString().equals("Empty")){
				board[p.icoord()][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()][p.jcoord()+1].type()==1){
				board[p.icoord()][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}
				}

				// Down - left
				if(p.jcoord()+1 < 8 && p.icoord()-1 >= 0){
				if(pieces[p.icoord()-1][p.jcoord()+1].toString().equals("Empty")){
				board[p.icoord()-1][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()-1][p.jcoord()+1].type()==1){
				board[p.icoord()-1][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}
				}

				// Down - right
				if(p.jcoord()+1 < 8 && p.icoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()+1].toString().equals("Empty")){
				board[p.icoord()+1][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()+1][p.jcoord()+1].type()==1){
				board[p.icoord()+1][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}
				}	
			}
	}
}
