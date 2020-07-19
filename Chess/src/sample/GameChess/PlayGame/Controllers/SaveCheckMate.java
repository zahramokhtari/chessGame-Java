package sample.GameChess.PlayGame.Controllers;

import sample.GameChess.PlayGame.PieceClasses.*;

public class SaveCheckMate {

    public static Piece[][] tryAll(Piece p,Piece[][] pieces){

        Piece[][] possiblemoves = new Piece[8][8];

        for(int x=0; x < 8; x++){
            for(int y=0; y < 8; y++){
                possiblemoves[x][y] = pieces[x][y];
            }
        }

        //_____________________________________WHITE_PAWNS_____________________________________//

        if(p.toString() == "Pawn" && p.type() == 1){
            // LOOK ONE SQUARE AHEAD IF CLEAR HIGHLIGHT
            if(p.jcoord()-1 >= 0){ // Guard for bounds
                if(pieces[p.icoord()][p.jcoord()-1].toString().equals("Empty")){
                    possiblemoves[p.icoord()][p.jcoord()-1]=new PiecePawn(1,p.icoord(),p.jcoord()-1,false);
                }}

            if(p.firstmove()){
                // LOOK TWO SQUARE AHEAD IF CLEAR HIGHLIGHT
                if(pieces[p.icoord()][p.jcoord()-2].toString().equals("Empty") && pieces[p.icoord()][p.jcoord()-1].toString().equals("Empty")){
                    possiblemoves[p.icoord()][p.jcoord()-2]=new PiecePawn(1,p.icoord(),p.jcoord()-2,false);
                }}

            // LOOK ONE SQUARE LEFT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
            if(p.icoord()-1 >= 0 && p.jcoord()-1 >= 0){
                if(pieces[p.icoord()-1][p.jcoord()-1].type() == 2){
                    possiblemoves[p.icoord()-1][p.jcoord()-1]=new PiecePawn(1,p.icoord()-1,p.jcoord()-1,false);
                }}

            // LOOK ONE SQUARE RIGHT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
            if(p.icoord()+1 < 8 && p.jcoord()-1 >= 0){
                if(pieces[p.icoord()+1][p.jcoord()-1].type() == 2){
                    possiblemoves[p.icoord()+1][p.jcoord()-1]=new PiecePawn(1,p.icoord()+1,p.jcoord()-1,false);
                }}
        }

        //_____________________________________BLACKPAWNS_____________________________________//
        if(p.toString() == "Pawn" && p.type() == 2){
            // LOOK ONE SQUARE AHEAD IF CLEAR HIGHLIGHT
            if(p.jcoord()+1 < 8){ // Guard for bounds
                if(pieces[p.icoord()][p.jcoord()+1].toString().equals("Empty")){
                    possiblemoves[p.icoord()][p.jcoord()+1] = new PiecePawn(2,p.icoord(),p.jcoord()+1,false);
                }}

            if(p.firstmove() == true){
                // LOOK TWO SQUARE AHEAD IF CLEAR HIGHLIGHT
                if(pieces[p.icoord()][p.jcoord()+2].toString().equals("Empty") && pieces[p.icoord()][p.jcoord()+1].toString().equals("Empty")){
                    possiblemoves[p.icoord()][p.jcoord()+2] = new PiecePawn(2,p.icoord(),p.jcoord()+2,false);
                }}

            // LOOK ONE SQUARE LEFT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
            if(p.icoord()-1 >= 0 && p.jcoord()+1 < 8){
                if(pieces[p.icoord()-1][p.jcoord()+1].type() == 1){
                    possiblemoves[p.icoord()-1][p.jcoord()+1] = new PiecePawn(2,p.icoord()-1,p.jcoord()+1,false);
                }}

            // LOOK ONE SQUARE RIGHT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
            if(p.icoord()+1 < 8 && p.jcoord()+1 < 8){
                if(pieces[p.icoord()+1][p.jcoord()+1].type() == 1){
                    possiblemoves[p.icoord()+1][p.jcoord()+1] = new PiecePawn(2,p.icoord()+1,p.jcoord()+1,false);
                }}
        }

        //__________________________________________WHITEROOK_____________________________________//
        if(p.toString() == "Rook" && p.type() == 1){
            // Look Up ..
            for(int y = p.jcoord()-1; y >= 0; y--){
                if(pieces[p.icoord()][y].toString().equals("Empty")){
                    possiblemoves[p.icoord()][y] = new PieceRook(1,p.icoord(),y);
                }
                if(pieces[p.icoord()][y].type()==2){
                    possiblemoves[p.icoord()][y] = new PieceRook(1,p.icoord(),y);
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
                    possiblemoves[x][p.jcoord()] = new PieceRook(1,x,p.jcoord());
                }
                if(pieces[x][p.jcoord()].type()==2){
                    possiblemoves[x][p.jcoord()] = new PieceRook(1,x,p.jcoord());
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
                    possiblemoves[x][p.jcoord()] = new PieceRook(1,x,p.jcoord());
                }
                if(pieces[x][p.jcoord()].type()==2){
                    possiblemoves[x][p.jcoord()] = new PieceRook(1,x,p.jcoord());
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
                    possiblemoves[p.icoord()][y] = new PieceRook(1,p.icoord(),y);
                }
                if(pieces[p.icoord()][y].type()==2){
                    possiblemoves[p.icoord()][y] = new PieceRook(1,p.icoord(),y);
                    // Stop looking
                    y=8;
                }
                if(y!=8 && pieces[p.icoord()][y].type()==1 && y!=8){
                    // Stop looking
                    y=8;
                }
            }
        }

        //__________________________________________BLACKROOK_____________________________________//

        if(p.toString() == "Rook" && p.type() == 2){
            // Look Up ..
            for(int y = p.jcoord()-1; y >= 0; y--){
                if(pieces[p.icoord()][y].toString().equals("Empty")){
                    possiblemoves[p.icoord()][y] = new PieceRook(2,p.icoord(),y);
                }
                if(pieces[p.icoord()][y].type()==1){
                    possiblemoves[p.icoord()][y] = new PieceRook(2,p.icoord(),y);
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
                    possiblemoves[x][p.jcoord()] = new PieceRook(2,x,p.jcoord());
                }
                if(pieces[x][p.jcoord()].type()==1){
                    possiblemoves[x][p.jcoord()] = new PieceRook(2,x,p.jcoord());
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
                    possiblemoves[x][p.jcoord()] = new PieceRook(2,x,p.jcoord());
                }
                if(pieces[x][p.jcoord()].type()==1){
                    possiblemoves[x][p.jcoord()] = new PieceRook(2,x,p.jcoord());
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
                    possiblemoves[p.icoord()][y] = new PieceRook(2,p.icoord(),y);
                }
                if(pieces[p.icoord()][y].type()==1){
                    possiblemoves[p.icoord()][y] = new PieceRook(2,p.icoord(),y);
                    // Stop looking
                    y=8;
                }
                if(y!=8 && pieces[p.icoord()][y].type()==2){
                    // Stop looking
                    y=8;
                }
            }
        }

        //__________________________________________WHITEBISHOP_____________________________________//
        if(p.toString() == "Bishop" && p.type() == 1){
            // Look up .. (left)
            for(int y=p.jcoord()-1, x=p.icoord()-1; y >= 0 && x >= 0; y--,x--){
                if(pieces[x][y].toString().equals("Empty")){
                    possiblemoves[x][y] = new PieceBishop(1,x,y);
                }
                if(pieces[x][y].type()==2){
                    possiblemoves[x][y] = new PieceBishop(1,x,y);
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
                    possiblemoves[x][y] = new PieceBishop(1,x,y);
                }
                if(pieces[x][y].type()==2){
                    possiblemoves[x][y] = new PieceBishop(1,x,y);
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
                    possiblemoves[x][y] = new PieceBishop(1,x,y);
                }
                if(pieces[x][y].type()==2){
                    possiblemoves[x][y] = new PieceBishop(1,x,y);
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
                    possiblemoves[x][y] = new PieceBishop(1,x,y);
                }
                if(pieces[x][y].type()==2){
                    possiblemoves[x][y] = new PieceBishop(1,x,y);
                    // Stop Looking
                    x=8;y=8;
                }
                if(x!=8 && y!=8 && pieces[x][y].type()==1){
                    // Stop Looking
                    x=8;y=8;
                }
            }
        }
        //__________________________________________BLACKBISHOP_____________________________________//
        if(p.toString() == "Bishop" && p.type() == 2){
            // Look up .. (left)
            for(int y=p.jcoord()-1, x=p.icoord()-1; y >= 0 && x >= 0; y--,x--){
                if(pieces[x][y].toString().equals("Empty")){
                    possiblemoves[x][y] = new PieceBishop(2,x,y);
                }
                if(pieces[x][y].type()==1){
                    possiblemoves[x][y] = new PieceBishop(2,x,y);
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
                    possiblemoves[x][y] = new PieceBishop(2,x,y);
                }
                if(pieces[x][y].type()==1){
                    possiblemoves[x][y] = new PieceBishop(2,x,y);
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
                    possiblemoves[x][y] = new PieceBishop(2,x,y);
                }
                if(pieces[x][y].type()==1){
                    possiblemoves[x][y] = new PieceBishop(2,x,y);
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
                    possiblemoves[x][y] = new PieceBishop(2,x,y);
                }
                if(pieces[x][y].type()==1){
                    possiblemoves[x][y] = new PieceBishop(2,x,y);
                    // Stop Looking
                    x=8;y=8;
                }
                if(x!=8 && y!=8 && pieces[x][y].type()==2){
                    // Stop Looking
                    x=8;y=8;
                }
            }
        }

        //_________________________________WHITEKNIGHT_____________________________________//

        // Assuming knights can jump regardless of what pieces are in the way

        if(p.toString() == "Knight" && p.type() == 1){
            // Up and left (first)
            if(p.icoord()-1 >= 0 && p.jcoord()-2 >= 0){				// Bound check
                if(pieces[p.icoord()-1][p.jcoord()-2].toString().equals("Empty")){
                    possiblemoves[p.icoord()-1][p.jcoord()-2] = new PieceKnight(1,p.icoord()-1,p.jcoord()-2);
                }
                if(pieces[p.icoord()-1][p.jcoord()-2].type()==2){
                    possiblemoves[p.icoord()-1][p.jcoord()-2] = new PieceKnight(1,p.icoord()-1,p.jcoord()-2);
                }
            }

            // Up and left (second)
            if(p.icoord()-2 >= 0 && p.jcoord()-1 >= 0){				// Bound check
                if(pieces[p.icoord()-2][p.jcoord()-1].toString().equals("Empty")){
                    possiblemoves[p.icoord()-2][p.jcoord()-1] = new PieceKnight(1,p.icoord()-2,p.jcoord()-1);
                }
                if(pieces[p.icoord()-2][p.jcoord()-1].type()==2){
                    possiblemoves[p.icoord()-2][p.jcoord()-1] = new PieceKnight(1,p.icoord()-2,p.jcoord()-1);
                }
            }

            // Up and right (first)
            if(p.icoord()+1 < 8 && p.jcoord()-2 >= 0){				// Bound check
                if(pieces[p.icoord()+1][p.jcoord()-2].toString().equals("Empty")){
                    possiblemoves[p.icoord()+1][p.jcoord()-2] = new PieceKnight(1,p.icoord()+1,p.jcoord()-2);
                }
                if(pieces[p.icoord()+1][p.jcoord()-2].type()==2){
                    possiblemoves[p.icoord()+1][p.jcoord()-2] = new PieceKnight(1,p.icoord()+1,p.jcoord()-2);
                }
            }

            // Up and right (second)
            if(p.icoord()+2 < 8 && p.jcoord()-1 >= 0){				// Bound check
                if(pieces[p.icoord()+2][p.jcoord()-1].toString().equals("Empty")){
                    possiblemoves[p.icoord()+2][p.jcoord()-1] = new PieceKnight(1,p.icoord()+2,p.jcoord()-1);
                }
                if(pieces[p.icoord()+2][p.jcoord()-1].type()==2){
                    possiblemoves[p.icoord()+2][p.jcoord()-1] = new PieceKnight(1,p.icoord()+2,p.jcoord()-1);
                }
            }

            // Bottom and right (first)
            if(p.icoord()+1 < 8 && p.jcoord()+2 < 8){				// Bound check
                if(pieces[p.icoord()+1][p.jcoord()+2].toString().equals("Empty")){
                    possiblemoves[p.icoord()+1][p.jcoord()+2] = new PieceKnight(1,p.icoord()+1,p.jcoord()+2);
                }
                if(pieces[p.icoord()+1][p.jcoord()+2].type()==2){
                    possiblemoves[p.icoord()+1][p.jcoord()+2] = new PieceKnight(1,p.icoord()+1,p.jcoord()+2);
                }
            }

            // Bottom and right (second)
            if(p.icoord()+2 < 8 && p.jcoord()+1 < 8){				// Bound check
                if(pieces[p.icoord()+2][p.jcoord()+1].toString().equals("Empty")){
                    possiblemoves[p.icoord()+2][p.jcoord()+1] = new PieceKnight(1,p.icoord()+2,p.jcoord()+1);
                }
                if(pieces[p.icoord()+2][p.jcoord()+1].type()==2){
                    possiblemoves[p.icoord()+2][p.jcoord()+1] = new PieceKnight(1,p.icoord()+2,p.jcoord()+1);
                }
            }

            // Bottom and left (first)
            if(p.icoord()-1 >= 0 && p.jcoord()+2 < 8){				// Bound check
                if(pieces[p.icoord()-1][p.jcoord()+2].toString().equals("Empty")){
                    possiblemoves[p.icoord()-1][p.jcoord()+2] = new PieceKnight(1,p.icoord()-1,p.jcoord()+2);
                }
                if(pieces[p.icoord()-1][p.jcoord()+2].type()==2){
                    possiblemoves[p.icoord()-1][p.jcoord()+2] = new PieceKnight(1,p.icoord()-1,p.jcoord()+2);
                }
            }

            // Bottom and left (second)
            if(p.icoord()-2 >= 0 && p.jcoord()+1 < 8){				// Bound check
                if(pieces[p.icoord()-2][p.jcoord()+1].toString().equals("Empty")){
                    possiblemoves[p.icoord()-2][p.jcoord()+1] = new PieceKnight(1,p.icoord()-2,p.jcoord()+1);
                }
                if(pieces[p.icoord()-2][p.jcoord()+1].type()==2){
                    possiblemoves[p.icoord()-2][p.jcoord()+1] = new PieceKnight(1,p.icoord()-2,p.jcoord()+1);
                }
            }
        }

        //_________________________________BLACKKNIGHT_____________________________________//
        if(p.toString() == "Knight" && p.type() == 2){
            // Up and left (first)
            if(p.icoord()-1 >= 0 && p.jcoord()-2 >= 0){				// Bound check
                if(pieces[p.icoord()-1][p.jcoord()-2].toString().equals("Empty")){
                    possiblemoves[p.icoord()-1][p.jcoord()-2] = new PieceKnight(2,p.icoord()-1,p.jcoord()-2);
                }
                if(pieces[p.icoord()-1][p.jcoord()-2].type()==1){
                    possiblemoves[p.icoord()-1][p.jcoord()-2] = new PieceKnight(2,p.icoord()-1,p.jcoord()-2);
                }
            }

            // Up and left (second)
            if(p.icoord()-2 >= 0 && p.jcoord()-1 >= 0){				// Bound check
                if(pieces[p.icoord()-2][p.jcoord()-1].toString().equals("Empty")){
                    possiblemoves[p.icoord()-2][p.jcoord()-1] = new PieceKnight(2,p.icoord()-2,p.jcoord()-1);
                }
                if(pieces[p.icoord()-2][p.jcoord()-1].type()==1){
                    possiblemoves[p.icoord()-2][p.jcoord()-1] = new PieceKnight(2,p.icoord()-2,p.jcoord()-1);
                }
            }

            // Up and right (first)
            if(p.icoord()+1 < 8 && p.jcoord()-2 >= 0){				// Bound check
                if(pieces[p.icoord()+1][p.jcoord()-2].toString().equals("Empty")){
                    possiblemoves[p.icoord()+1][p.jcoord()-2] = new PieceKnight(2,p.icoord()+1,p.jcoord()-2);
                }
                if(pieces[p.icoord()+1][p.jcoord()-2].type()==1){
                    possiblemoves[p.icoord()+1][p.jcoord()-2] = new PieceKnight(2,p.icoord()+1,p.jcoord()-2);
                }
            }

            // Up and right (second)
            if(p.icoord()+2 < 8 && p.jcoord()-1 >= 0){				// Bound check
                if(pieces[p.icoord()+2][p.jcoord()-1].toString().equals("Empty")){
                    possiblemoves[p.icoord()+2][p.jcoord()-1] = new PieceKnight(2,p.icoord()+2,p.jcoord()-1);
                }
                if(pieces[p.icoord()+2][p.jcoord()-1].type()==1){
                    possiblemoves[p.icoord()+2][p.jcoord()-1] = new PieceKnight(2,p.icoord()+2,p.jcoord()-1);
                }
            }

            // Bottom and right (first)
            if(p.icoord()+1 < 8 && p.jcoord()+2 < 8){				// Bound check
                if(pieces[p.icoord()+1][p.jcoord()+2].toString().equals("Empty")){
                    possiblemoves[p.icoord()+1][p.jcoord()+2] = new PieceKnight(2,p.icoord()+1,p.jcoord()+2);
                }
                if(pieces[p.icoord()+1][p.jcoord()+2].type()==1){
                    possiblemoves[p.icoord()+1][p.jcoord()+2] = new PieceKnight(2,p.icoord()+1,p.jcoord()+2);
                }
            }

            // Bottom and right (second)
            if(p.icoord()+2 < 8 && p.jcoord()+1 < 8){				// Bound check
                if(pieces[p.icoord()+2][p.jcoord()+1].toString().equals("Empty")){
                    possiblemoves[p.icoord()+2][p.jcoord()+1] = new PieceKnight(2,p.icoord()+2,p.jcoord()+1);
                }
                if(pieces[p.icoord()+2][p.jcoord()+1].type()==1){
                    possiblemoves[p.icoord()+2][p.jcoord()+1] = new PieceKnight(2,p.icoord()+2,p.jcoord()+1);
                }
            }

            // Bottom and left (first)
            if(p.icoord()-1 >= 0 && p.jcoord()+2 < 8){
                // Bound check
                if(pieces[p.icoord()-1][p.jcoord()+2].toString().equals("Empty")){
                    possiblemoves[p.icoord()-1][p.jcoord()+2] = new PieceKnight(2,p.icoord()-1,p.jcoord()+2);
                }
                if(pieces[p.icoord()-1][p.jcoord()+2].type()==1){
                    possiblemoves[p.icoord()-1][p.jcoord()+2] = new PieceKnight(2,p.icoord()-1,p.jcoord()+2);
                }
            }

            // Bottom and left (second)
            if(p.icoord()-2 >= 0 && p.jcoord()+1 < 8){
                // Bound check
                if(pieces[p.icoord()-2][p.jcoord()+1].toString().equals("Empty")){
                    possiblemoves[p.icoord()-2][p.jcoord()+1] = new PieceKnight(2,p.icoord()-2,p.jcoord()+1);
                }
                if(pieces[p.icoord()-2][p.jcoord()+1].type()==1){
                    possiblemoves[p.icoord()-2][p.jcoord()+1] = new PieceKnight(2,p.icoord()-2,p.jcoord()+1);
                }
            }

        }

        //______________________________WHITEQUEEN_____________________________________//
        if(p.toString() == "Queen" && p.type() == 1){
            // Look up .. (left)
            for(int y=p.jcoord()-1, x=p.icoord()-1; y >= 0 && x >= 0; y--,x--){
                if(pieces[x][y].toString().equals("Empty")){
                    possiblemoves[x][y] = new PieceQueen(1,x,y);
                }
                if(pieces[x][y].type()==2){
                    possiblemoves[x][y] = new PieceQueen(1,x,y);
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
                    possiblemoves[x][y] = new PieceQueen(1,x,y);
                }
                if(pieces[x][y].type()==2){
                    possiblemoves[x][y] = new PieceQueen(1,x,y);
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
                    possiblemoves[x][y] = new PieceQueen(1,x,y);
                }
                if(pieces[x][y].type()==2){
                    possiblemoves[x][y] = new PieceQueen(1,x,y);
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
                    possiblemoves[x][y] = new PieceQueen(1,x,y);
                }
                if(pieces[x][y].type()==2){
                    possiblemoves[x][y] = new PieceQueen(1,x,y);
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
                    possiblemoves[p.icoord()][y] = new PieceQueen(1,p.icoord(),y);
                }
                if(pieces[p.icoord()][y].type()==2){
                    possiblemoves[p.icoord()][y] = new PieceQueen(1,p.icoord(),y);
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
                    possiblemoves[x][p.jcoord()] = new PieceQueen(1,x,p.jcoord());
                }
                if(pieces[x][p.jcoord()].type()==2){
                    possiblemoves[x][p.jcoord()] = new PieceQueen(1,x,p.jcoord());
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
                    possiblemoves[x][p.jcoord()] = new PieceQueen(1,x,p.jcoord());
                }
                if(pieces[x][p.jcoord()].type()==2){
                    possiblemoves[x][p.jcoord()] = new PieceQueen(1,x,p.jcoord());
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
                    possiblemoves[p.icoord()][y] = new PieceQueen(1,p.icoord(),y);
                }
                if(pieces[p.icoord()][y].type()==2){
                    possiblemoves[p.icoord()][y] = new PieceQueen(1,p.icoord(),y);
                    // Stop looking
                    y=8;
                }
                if(y!=8 && pieces[p.icoord()][y].type()==1 && y!=8){
                    // Stop looking
                    y=8;
                }
            }
        }

        //______________________________BLACKQUEEN_____________________________________//
        if(p.toString() == "Queen" && p.type() == 2){
            // Look up .. (left)
            for(int y=p.jcoord()-1, x=p.icoord()-1; y >= 0 && x >= 0; y--,x--){
                if(pieces[x][y].toString().equals("Empty")){
                    possiblemoves[x][y] = new PieceQueen(2,x,y);
                }
                if(pieces[x][y].type()==1){
                    possiblemoves[x][y] = new PieceQueen(2,x,y);
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
                    possiblemoves[x][y] = new PieceQueen(2,x,y);
                }
                if(pieces[x][y].type()==1){
                    possiblemoves[x][y] = new PieceQueen(2,x,y);
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
                    possiblemoves[x][y] = new PieceQueen(2,x,y);
                }
                if(pieces[x][y].type()==1){
                    possiblemoves[x][y] = new PieceQueen(2,x,y);
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
                    possiblemoves[x][y] = new PieceQueen(2,x,y);
                }
                if(pieces[x][y].type()==1){
                    possiblemoves[x][y] = new PieceQueen(2,x,y);
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
                    possiblemoves[p.icoord()][y] = new PieceQueen(2,p.icoord(),y);
                }
                if(pieces[p.icoord()][y].type()==1){
                    possiblemoves[p.icoord()][y] = new PieceQueen(2,p.icoord(),y);
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
                    possiblemoves[x][p.jcoord()] = new PieceQueen(2,x,p.jcoord());
                }
                if(pieces[x][p.jcoord()].type()==1){
                    possiblemoves[x][p.jcoord()] = new PieceQueen(2,x,p.jcoord());
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
                    possiblemoves[x][p.jcoord()] = new PieceQueen(2,x,p.jcoord());
                }
                if(pieces[x][p.jcoord()].type()==1){
                    possiblemoves[x][p.jcoord()] = new PieceQueen(2,x,p.jcoord());
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
                    possiblemoves[p.icoord()][y] = new PieceQueen(2,p.icoord(),y);
                }
                if(pieces[p.icoord()][y].type()==1){
                    possiblemoves[p.icoord()][y] = new PieceQueen(2,p.icoord(),y);
                    // Stop looking
                    y=8;
                }
                if(y!=8 && pieces[p.icoord()][y].type()==2 && y!=8){
                    // Stop looking
                    y=8;
                }
            }
        }

        //_________________________________WHITEKING_____________________________________//
        if(p.toString() == "King" && p.type() == 1){
            // Up
            if(p.jcoord()-1 >= 0){
                if(pieces[p.icoord()][p.jcoord()-1].toString().equals("Empty")){
                    possiblemoves[p.icoord()][p.jcoord()-1] = new PieceKing(1,p.icoord(),p.jcoord()-1);
                }
                if(pieces[p.icoord()][p.jcoord()-1].type()==2){
                    possiblemoves[p.icoord()][p.jcoord()-1] = new PieceKing(1,p.icoord(),p.jcoord()-1);
                }
            }

            // Up - right
            if(p.jcoord()-1 >= 0 && p.icoord()+1 < 8){
                if(pieces[p.icoord()+1][p.jcoord()-1].toString().equals("Empty")){
                    possiblemoves[p.icoord()+1][p.jcoord()-1] = new PieceKing(1,p.icoord()+1,p.jcoord()-1);
                }
                if(pieces[p.icoord()+1][p.jcoord()-1].type()==2){
                    possiblemoves[p.icoord()+1][p.jcoord()-1] = new PieceKing(1,p.icoord()+1,p.jcoord()-1);
                }
            }

            // Up - left
            if(p.jcoord()-1 >= 0 && p.icoord()-1 >= 0){
                if(pieces[p.icoord()-1][p.jcoord()-1].toString().equals("Empty")){
                    possiblemoves[p.icoord()-1][p.jcoord()-1] = new PieceKing(1,p.icoord()-1,p.jcoord()-1);
                }
                if(pieces[p.icoord()-1][p.jcoord()-1].type()==2){
                    possiblemoves[p.icoord()-1][p.jcoord()-1] = new PieceKing(1,p.icoord()-1,p.jcoord()-1);
                }
            }

            // Left
            if(p.icoord()-1 >= 0){
                if(pieces[p.icoord()-1][p.jcoord()].toString().equals("Empty")){
                    possiblemoves[p.icoord()-1][p.jcoord()] = new PieceKing(1,p.icoord()-1,p.jcoord());
                }
                if(pieces[p.icoord()-1][p.jcoord()].type()==2){
                    possiblemoves[p.icoord()-1][p.jcoord()] = new PieceKing(1,p.icoord()-1,p.jcoord());
                }
            }

            // Right
            if(p.icoord()+1 < 8){
                if(pieces[p.icoord()+1][p.jcoord()].toString().equals("Empty")){
                    possiblemoves[p.icoord()+1][p.jcoord()] = new PieceKing(1,p.icoord()+1,p.jcoord());
                }
                if(pieces[p.icoord()+1][p.jcoord()].type()==2){
                    possiblemoves[p.icoord()+1][p.jcoord()] = new PieceKing(1,p.icoord()+1,p.jcoord());
                }
            }

            // Down
            if(p.jcoord()+1 < 8){
                if(pieces[p.icoord()][p.jcoord()+1].toString().equals("Empty")){
                    possiblemoves[p.icoord()][p.jcoord()+1] = new PieceKing(1,p.icoord(),p.jcoord()+1);
                }
                if(pieces[p.icoord()][p.jcoord()+1].type()==2){
                    possiblemoves[p.icoord()][p.jcoord()+1] = new PieceKing(1,p.icoord(),p.jcoord()+1);
                }
            }

            // Down - left
            if(p.jcoord()+1 < 8 && p.icoord()-1 >= 0){
                if(pieces[p.icoord()-1][p.jcoord()+1].toString().equals("Empty")){
                    possiblemoves[p.icoord()-1][p.jcoord()+1] = new PieceKing(1,p.icoord()-1,p.jcoord()+1);
                }
                if(pieces[p.icoord()-1][p.jcoord()+1].type()==2){
                    possiblemoves[p.icoord()-1][p.jcoord()+1] = new PieceKing(1,p.icoord()-1,p.jcoord()+1);
                }
            }

            // Down - right
            if(p.jcoord()+1 < 8 && p.icoord()+1 < 8){
                if(pieces[p.icoord()+1][p.jcoord()+1].toString().equals("Empty")){
                    possiblemoves[p.icoord()+1][p.jcoord()+1] = new PieceKing(1,p.icoord()+1,p.jcoord()+1);
                }
                if(pieces[p.icoord()+1][p.jcoord()+1].type()==2){
                    possiblemoves[p.icoord()+1][p.jcoord()+1] = new PieceKing(1,p.icoord()+1,p.jcoord()+1);
                }
            }
        }
        //same level ar kamla khato tailei hobe😈😈😈

        //_________________________________BLACK_KING_____________________________________//
        if(p.toString() == "King" && p.type() == 2){
            // Up
            if(p.jcoord()-1 >= 0){
                if(pieces[p.icoord()][p.jcoord()-1].toString().equals("Empty")){
                    possiblemoves[p.icoord()][p.jcoord()-1] = new PieceKing(2,p.icoord(),p.jcoord()-1);
                }
                if(pieces[p.icoord()][p.jcoord()-1].type()==1){
                    possiblemoves[p.icoord()][p.jcoord()-1] = new PieceKing(2,p.icoord(),p.jcoord()-1);
                }
            }

            // Up - right
            if(p.jcoord()-1 >= 0 && p.icoord()+1 < 8){
                if(pieces[p.icoord()+1][p.jcoord()-1].toString().equals("Empty")){
                    possiblemoves[p.icoord()+1][p.jcoord()-1] = new PieceKing(2,p.icoord()+1,p.jcoord()-1);
                }
                if(pieces[p.icoord()+1][p.jcoord()-1].type()==1){
                    possiblemoves[p.icoord()+1][p.jcoord()-1] = new PieceKing(2,p.icoord()+1,p.jcoord()-1);
                }
            }

            // Up - left
            if(p.jcoord()-1 >= 0 && p.icoord()-1 >= 0){
                if(pieces[p.icoord()-1][p.jcoord()-1].toString().equals("Empty")){
                    possiblemoves[p.icoord()-1][p.jcoord()-1] = new PieceKing(2,p.icoord()-1,p.jcoord()-1);
                }
                if(pieces[p.icoord()-1][p.jcoord()-1].type()==1){
                    possiblemoves[p.icoord()-1][p.jcoord()-1] = new PieceKing(2,p.icoord()-1,p.jcoord()-1);
                }
            }

            // Left
            if(p.icoord()-1 >= 0){
                if(pieces[p.icoord()-1][p.jcoord()].toString().equals("Empty")){
                    possiblemoves[p.icoord()-1][p.jcoord()] = new PieceKing(2,p.icoord()-1,p.jcoord());
                }
                if(pieces[p.icoord()-1][p.jcoord()].type()==1){
                    possiblemoves[p.icoord()-1][p.jcoord()] = new PieceKing(2,p.icoord()-1,p.jcoord());
                }
            }

            // Right
            if(p.icoord()+1 < 8){
                if(pieces[p.icoord()+1][p.jcoord()].toString().equals("Empty")){
                    possiblemoves[p.icoord()+1][p.jcoord()] = new PieceKing(2,p.icoord()+1,p.jcoord());
                }
                if(pieces[p.icoord()+1][p.jcoord()].type()==1){
                    possiblemoves[p.icoord()+1][p.jcoord()] = new PieceKing(2,p.icoord()+1,p.jcoord());
                }
            }

            // Down
            if(p.jcoord()+1 < 8){
                if(pieces[p.icoord()][p.jcoord()+1].toString().equals("Empty")){
                    possiblemoves[p.icoord()][p.jcoord()+1] = new PieceKing(2,p.icoord(),p.jcoord()+1);
                }
                if(pieces[p.icoord()][p.jcoord()+1].type()==1){
                    possiblemoves[p.icoord()][p.jcoord()+1] = new PieceKing(2,p.icoord(),p.jcoord()+1);
                }
            }

            // Down - left
            if(p.jcoord()+1 < 8 && p.icoord()-1 >= 0){
                if(pieces[p.icoord()-1][p.jcoord()+1].toString().equals("Empty")){
                    possiblemoves[p.icoord()-1][p.jcoord()+1] = new PieceKing(2,p.icoord()-1,p.jcoord()+1);
                }
                if(pieces[p.icoord()-1][p.jcoord()+1].type()==1){
                    possiblemoves[p.icoord()-1][p.jcoord()+1] = new PieceKing(2,p.icoord()-1,p.jcoord()+1);
                }
            }

            // Down - right
            if(p.jcoord()+1 < 8 && p.icoord()+1 < 8){
                if(pieces[p.icoord()+1][p.jcoord()+1].toString().equals("Empty")){
                    possiblemoves[p.icoord()+1][p.jcoord()+1] = new PieceKing(2,p.icoord()+1,p.jcoord()+1);
                }
                if(pieces[p.icoord()+1][p.jcoord()+1].type()==1){
                    possiblemoves[p.icoord()+1][p.jcoord()+1] = new PieceKing(2,p.icoord()+1,p.jcoord()+1);
                }
            }
        }

        // Return array of possible moves for the piece ..
        return possiblemoves;
    }
}
