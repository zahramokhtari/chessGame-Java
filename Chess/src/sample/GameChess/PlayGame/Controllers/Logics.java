package sample.GameChess.PlayGame.Controllers;
import sample.GameChess.PlayGame.PieceClasses.*;


/**
 *This is the logic class for check resolve it & check for checkmates stalemate etc
 */
public class Logics implements Cloneable {
    private int currentPlayer;
    private int otherPlayer;

	public static final int
            WHITE_PLAYER = 1,
            BLACK_PLAYER = 2,
            EMPTY_PLAYER = 0;
	
	private String targetClick = "false";
	private boolean check = false;
	private int checkCoordI , checkCoordJ;

	public String changeclick(){
		return this.targetClick = (targetClick.equals("true"))?"false":"true";
	}

	public String ischeckMateOccurs(int currentPlayer, Piece[][] boardstate){

		String flagforCheckmate="null";
		
		// For loop to check every piece on current board
		for(int x=0; x < 8; x++){
			for(int y=0; y < 8; y++){			
				Piece[][] possibleMoves = new Piece[8][8];
				// No need to run check on empty pieces and just for enemy pieces
				if(!boardstate[x][y].toString().equals("Empty") && boardstate[x][y].type()==otherPlayer){
				    // Create an array of possible moves for this piece
				    possibleMoves = checkMateMoves(boardstate[x][y], boardstate);
                    // If possible_moves has a move that resolves check == false, flag=false
				if(!check4check(currentPlayer, possibleMoves)){ flagforCheckmate="false"; }
				}
			}
		}

		// If possible_moves has no move that resolves flag to false, checkmate==true
		if(flagforCheckmate.equals("null")){
			System.out.println("CHECKMATE Found!!!");
			System.out.println("//Press SPACE_BAR to Reset//");
			return flagforCheckmate="true";
		}
		else{return flagforCheckmate="null";}
	}


	// Method for checkmate, puts all valid moves for a piece into a board_state(2-D Piece array and return it)
	public Piece[][] checkMateMoves(Piece p, Piece[][] pieces){
	    return SaveCheckMate.tryAll(p,pieces);
	}

	public boolean check4check(int current_player, Piece[][] boardstate){
        if(current_player==1){otherPlayer=2;currentPlayer=1;}
        else{otherPlayer=1; currentPlayer=2;}

        // For loop to go through each piece and see if it can target the king
        for(int xi=0; xi < 8; xi++){
            for(int yi=0; yi < 8; yi++){

                if(boardstate[xi][yi].toString().equals("Pawn") && boardstate[xi][yi].type() == currentPlayer){
                    // LOOK ONE SQUARE LEFT DIAGONALLY IF KING PRESENT HIGHLIGHT
                    if(xi-1 >= 0 && yi-1 >= 0){
                        if(boardstate[xi-1][yi-1].toString().equals("King") && boardstate[xi-1][yi-1].type() == otherPlayer){
                            check = true;
                            checkCoordI=xi-1;
                            checkCoordJ=yi-1;
                            return true;
                        }}

                    // LOOK ONE SQUARE RIGHT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
                    if(xi+1 < 8 && yi-1 >= 0){
                        if(boardstate[xi+1][yi-1].toString().equals("King") && boardstate[xi-1][yi-1].type() == otherPlayer){
                            check = true;
                            checkCoordI=xi+1; checkCoordJ=yi-1;
                            return true;
                        }}
                }

                //__________________________________________ROOKS_____________________________________//
                if(boardstate[xi][yi].toString().equals("Rook") && boardstate[xi][yi].type() == currentPlayer){
                    // Look Up ..
                    for(int y = yi-1; y >= 0; y--){
                        boolean clearc = true;
                        if(!boardstate[xi][y].toString().equals("Empty") && !boardstate[xi][y].toString().equals("King")){
                            clearc=false;
                            y=-1;
                        }
                        // If path is clear, check for king ..
                        if(clearc==true && y!=-1){
                            //System.out.println(clearc);
                            if(boardstate[xi][y].toString().equals("King") && boardstate[xi][y].type() == otherPlayer){
                                //System.out.println("gotcha");
                                check = true;
                                checkCoordI=xi;checkCoordJ=y;
                                y=-1;
                                return true;
                            }}
                    }

                    // Look Right ..
                    for(int x = xi+1; x < 8; x++){
                        boolean clearc = true;
                        if(!boardstate[x][yi].toString().equals("Empty") && !boardstate[x][yi].toString().equals("King")){
                            clearc=false;
                            x=8;
                        }
                        // If path is clear, check for king ..
                        if(clearc==true && x!=8){
                            if(boardstate[x][yi].toString().equals("King") && boardstate[x][yi].type() == otherPlayer){
                                check = true;
                                checkCoordI=x;checkCoordJ=yi;
                                x=8;
                                return true;
                            }}
                    }

                    // Look Left ..
                    for(int x = xi-1; x >= 0; x--){
                        boolean clearc = true;
                        if(!boardstate[x][yi].toString().equals("Empty") && !boardstate[x][yi].toString().equals("King")){
                            clearc=false;
                            x=-1;
                        }
                        // If path is clear, check for king ..
                        if(clearc==true && x!=-1){
                            if(boardstate[x][yi].toString().equals("King") && boardstate[x][yi].type() == otherPlayer){
                                check = true;
                                checkCoordI=x;checkCoordJ=yi;
                                x=-1;
                                return true;
                            }}
                    }

                    // Look Down ..
                    for(int y = yi+1; y < 8; y++){
                        boolean clearc = true;
                        if(!boardstate[xi][y].toString().equals("Empty") && !boardstate[xi][y].toString().equals("King")){
                            clearc=false;
                            y=8;
                        }
                        // If path is clear, check for king ..
                        if(clearc==true && y!=8){
                            if(boardstate[xi][y].toString().equals("King") && boardstate[xi][y].type() == otherPlayer){
                                check = true;
                                checkCoordI=xi;checkCoordJ=y;
                                y=8;
                                return true;
                            }}
                    }
                }

                //__________________________________________BISHOPS_____________________________________//
                if(boardstate[xi][yi].toString().equals("Bishop") && boardstate[xi][yi].type() == currentPlayer){
                    // Look up .. (left)
                    for(int y=yi-1, x=xi-1; y >= 0 && x >= 0; y--,x--){
                        boolean clearb = true;
                        if(!boardstate[x][y].toString().equals("Empty") && !boardstate[x][y].toString().equals("King")){
                            clearb=false;
                            y=-1;x=-1;
                        }
                        // If path is clear, check for king ..
                        if(clearb==true && y!=-1 && x!=-1){
                            if(boardstate[x][y].toString().equals("King") && boardstate[x][y].type() == otherPlayer){
                                check = true;
                                checkCoordI=x;checkCoordJ=y;
                                x=-1;y=-1;
                                return true;
                            }}
                    }

                    // Look up .. (right)
                    for(int y=yi-1, x=xi+1; y >= 0 && x < 8; y--,x++){
                        boolean clearb = true;
                        if(!boardstate[x][y].toString().equals("Empty") && !boardstate[x][y].toString().equals("King")){
                            clearb=false;
                            y=-1;x=8;
                        }
                        // If path is clear, check for king ..
                        if(clearb==true && x!=8 && y!=-1){
                            if(boardstate[x][y].toString().equals("King") && boardstate[x][y].type() == otherPlayer){
                                check = true;
                                checkCoordI=x;checkCoordJ=y;
                                y=-1;x=8;
                                return true;
                            }}
                    }

                    // Look down .. (left)
                    for(int y=yi+1, x=xi-1; y < 8 && x >= 0; y++,x--){
                        boolean clearb = true;
                        if(!boardstate[x][y].toString().equals("Empty") && !boardstate[x][y].toString().equals("King")){
                            clearb=false;
                            y=8;x=-1;
                        }
                        // If path is clear, check for king ..
                        if(clearb==true && y!=8 && x!=-1){
                            if(boardstate[x][y].toString().equals("King") && boardstate[x][y].type() == otherPlayer){
                                check = true;
                                checkCoordI=x;checkCoordJ=y;
                                y=8;x=-1;
                                return true;
                            }}
                    }

                    // Look down .. (right)
                    for(int y=yi+1, x=xi+1; y < 8 && x < 8; y++,x++){
                        boolean clearb = true;
                        if(!boardstate[x][y].toString().equals("Empty") && !boardstate[x][y].toString().equals("King")){
                            clearb=false;
                            y=8;x=8;
                        }
                        // If path is clear, check for king ..
                        if(clearb==true && x!=8 && y!=8){
                            if(boardstate[x][y].toString().equals("King") && boardstate[x][y].type() == otherPlayer){
                                check = true;
                                checkCoordI=x;checkCoordJ=y;
                                y=8;x=8;
                                return true;
                            }}
                    }
                }

                //_________________________________KNIGHTS_____________________________________//

                // Assuming knights can jump regardless of what pieces are in the way

                if(boardstate[xi][yi].toString().equals("Knight") && boardstate[xi][yi].type() == currentPlayer){
                    // Up and left (first)
                    if(xi-1 >= 0 && yi-2 >= 0){				// Bound check
                        if(boardstate[xi-1][yi-2].toString().equals("King") && boardstate[xi-1][yi-2].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi-1; checkCoordJ=yi-2;
                            return true;
                        }
                    }

                    // Up and left (second)
                    if(xi-2 >= 0 && yi-1 >= 0){				// Bound check
                        if(boardstate[xi-2][yi-1].toString().equals("King") && boardstate[xi-2][yi-1].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi-2; checkCoordJ=yi-1;
                            return true;
                        }
                    }

                    // Up and right (first)
                    if(xi+1 < 8 && yi-2 >= 0){				// Bound check
                        if(boardstate[xi+1][yi-2].toString().equals("King") && boardstate[xi+1][yi-2].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi+1;checkCoordJ=yi-2;
                            return true;
                        }
                    }

                    // Up and right (second)
                    if(xi+2 < 8 && yi-1 >= 0){				// Bound check
                        if(boardstate[xi+2][yi-1].toString().equals("King") && boardstate[xi+2][yi-1].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi+2; checkCoordJ=yi-1;
                            return true;
                        }
                    }

                    // Bottom and right (first)
                    if(xi+1 < 8 && yi+2 < 8){				// Bound check
                        if(boardstate[xi+1][yi+2].toString().equals("King") && boardstate[xi+1][yi+2].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi+1; checkCoordJ=yi+2;
                            return true;
                        }
                    }

                    // Bottom and right (second)
                    if(xi+2 < 8 && yi+1 < 8){				// Bound check
                        if(boardstate[xi+2][yi+1].toString().equals("King") && boardstate[xi+2][yi+1].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi+2; checkCoordJ=yi+1;
                            return true;
                        }
                    }

                    // Bottom and left (first)
                    if(xi-1 >= 0 && yi+2 < 8){				// Bound check
                        if(boardstate[xi-1][yi+2].toString().equals("King") && boardstate[xi-1][yi+2].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi-1; checkCoordJ=yi+2;
                            return true;
                        }
                    }

                    // Bottom and left (second)
                    if(xi-2 >= 0 && yi+1 < 8){				// Bound check
                        if(boardstate[xi-2][yi+1].toString().equals("King") && boardstate[xi-2][yi+1].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi-2; checkCoordJ=yi+1;
                            return true;
                        }
                    }
                }

                //______________________________QUEENS_____________________________________//
                if(boardstate[xi][yi].toString().equals("Queen") && boardstate[xi][yi].type()==currentPlayer){
                    // Look Up ..
                    for(int y = yi-1; y >= 0; y--){
                        boolean clearq = true;
                        if(!boardstate[xi][y].toString().equals("Empty") && !boardstate[xi][y].toString().equals("King")){
                            clearq=false;
                            y=-1;
                        }
                        // If path is clear, check for king ..
                        if(clearq==true && y!=-1){
                            if(boardstate[xi][y].toString().equals("King") && boardstate[xi][y].type() == otherPlayer){
                                check = true;
                                checkCoordI=xi;checkCoordJ=y;
                                y=-1;
                                return true;
                            }}
                    }

                    // Look Right ..
                    for(int x = xi+1; x < 8; x++){
                        boolean clearq = true;
                        if(!boardstate[x][yi].toString().equals("Empty") && !boardstate[x][yi].toString().equals("King")){
                            clearq=false;
                            x=8;
                        }
                        // If path is clear, check for king ..
                        if(clearq==true && x!=8){
                            if(boardstate[x][yi].toString().equals("King") && boardstate[x][yi].type() == otherPlayer){
                                check = true;
                                checkCoordI=x;checkCoordJ=yi;
                                x=8;
                                return true;
                            }}
                    }

                    // Look Left ..
                    for(int x = xi-1; x >= 0; x--){
                        boolean clearq = true;
                        if(!boardstate[x][yi].toString().equals("Empty") && !boardstate[x][yi].toString().equals("King")){
                            clearq=false;
                            x=-1;
                        }
                        // If path is clear, check for king ..
                        if(clearq==true && x!=-1){
                            if(boardstate[x][yi].toString().equals("King") && boardstate[x][yi].type() == otherPlayer){
                                check = true;
                                checkCoordI=x;checkCoordJ=yi;
                                x=-1;
                                return true;
                            }}
                    }

                    // Look Down ..
                    for(int y = yi+1; y < 8; y++){
                        boolean clearq = true;
                        if(!boardstate[xi][y].toString().equals("Empty") && !boardstate[xi][y].toString().equals("King")){
                            clearq=false;
                            y=8;
                        }
                        // If path is clear, check for king ..
                        if(clearq==true && y!=8){
                            if(boardstate[xi][y].toString().equals("King") && boardstate[xi][y].type() == otherPlayer){
                                check = true;
                                checkCoordI=xi;checkCoordJ=y;
                                y=8;
                                return true;
                            }}
                    }

                    // Look up .. (left)
                    for(int y=yi-1, x=xi-1; y >= 0 && x >= 0; y--,x--){
                        boolean clearq = true;
                        if(!boardstate[x][y].toString().equals("Empty") && !boardstate[x][y].toString().equals("King")){
                            clearq=false;
                            y=-1;x=-1;
                        }
                        // If path is clear, check for king ..
                        if(clearq==true && y!=-1 && x!=-1){
                            if(boardstate[x][y].toString().equals("King") && boardstate[x][y].type() == otherPlayer){
                                check = true;
                                checkCoordI=x;checkCoordJ=y;
                                x=-1;y=-1;
                                return true;
                            }}
                    }

                    // Look up .. (right)
                    for(int y=yi-1, x=xi+1; y >= 0 && x < 8; y--,x++){
                        boolean clearq = true;
                        if(!boardstate[x][y].toString().equals("Empty") && !boardstate[x][y].toString().equals("King")){
                            clearq=false;
                            y=-1;x=8;
                        }
                        // If path is clear, check for king ..
                        if(clearq==true && y!=-1 && x!=8){
                            if(boardstate[x][y].toString().equals("King") && boardstate[x][y].type() == otherPlayer){
                                check = true;
                                checkCoordI=x;checkCoordJ=y;
                                y=-1;x=8;
                                return true;
                            }}
                    }

                    // Look down .. (left)
                    for(int y=yi+1, x=xi-1; y < 8 && x >= 0; y++,x--){
                        boolean clearq = true;
                        if(!boardstate[x][y].toString().equals("Empty") && !boardstate[x][y].toString().equals("King")){
                            clearq=false;
                            y=8;x=-1;
                        }
                        // If path is clear, check for king ..
                        if(clearq==true && y!=8 && x!=-1){
                            if(boardstate[x][y].toString().equals("King") && boardstate[x][y].type() == otherPlayer){
                                check = true;
                                checkCoordI=x;checkCoordJ=y;
                                y=8;x=-1;
                                return true;
                            }}
                    }

                    // Look down .. (right)
                    for(int y=yi+1, x=xi+1; y < 8 && x < 8; y++,x++){
                        boolean clearq = true;
                        if(!boardstate[x][y].toString().equals("Empty") && !boardstate[x][y].toString().equals("King")){
                            clearq=false;
                            y=8;x=8;
                        }
                        // If path is clear, check for king ..
                        if(clearq==true && y!=8 && x!=8){
                            if(boardstate[x][y].toString().equals("King") && boardstate[x][y].type() == otherPlayer){
                                check = true;
                                checkCoordI=x;checkCoordJ=y;
                                y=8;x=8;
                                return true;
                            }}
                    }
                }

                //______________________________KINGS_____________________________________// (possibly don't need this .. )
                if(boardstate[xi][yi].toString().equals("King") && boardstate[xi][yi].type()==currentPlayer){
                    // Up
                    if(yi-1 >= 0){
                        if(boardstate[xi][yi-1].toString().equals("King") && boardstate[xi][yi-1].type() == otherPlayer){
                            check=true;
                            checkCoordI=xi; checkCoordJ=yi-1;
                        }
                    }

                    // Up - right
                    if(yi-1 >= 0 && xi+1 < 8){
                        if(boardstate[xi+1][yi-1].toString().equals("King") && boardstate[xi+1][yi-1].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi+1; checkCoordJ=yi-1;
                        }
                    }

                    // Up - left
                    if(yi-1 >= 0 && xi-1 >= 0){
                        if(boardstate[xi-1][yi-1].toString().equals("King") && boardstate[xi-1][yi-1].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi-1; checkCoordJ=yi-1;
                        }
                    }

                    // Left
                    if(xi-1 >= 0){
                        if(boardstate[xi-1][yi].toString().equals("King") && boardstate[xi-1][yi].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi-1; checkCoordJ=yi;
                        }
                    }

                    // Right
                    if(xi+1 < 8){
                        if(boardstate[xi+1][yi].toString().equals("King") && boardstate[xi+1][yi].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi+1; checkCoordJ=yi;
                        }
                    }

                    // Down
                    if(yi+1 < 8){
                        if(boardstate[xi][yi+1].toString().equals("King") && boardstate[xi][yi+1].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi; checkCoordJ=yi+1;
                        }
                    }

                    // Down - left
                    if(yi+1 < 8 && xi-1 >= 0){
                        if(boardstate[xi-1][yi+1].toString().equals("King") && boardstate[xi-1][yi+1].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi-1; checkCoordJ=yi+1;
                        }
                    }

                    // Down - right
                    if(yi+1 < 8 && xi+1 < 8){
                        if(boardstate[xi+1][yi+1].toString().equals("King") && boardstate[xi+1][yi+1].type()==otherPlayer){
                            check=true;
                            checkCoordI=xi+1; checkCoordJ=yi+1;
                        }
                    }
                }
            }
        }
        // else return false ..
        check=false;
        return false;
    }

    public int checkCoordI(){
        return checkCoordI;
    }

    public int checkCoordJ(){
        return checkCoordJ;
    }

    public boolean checkstatus(){ return this.check; }

    public void flipcheck(){ if(check)check=false; }
}
