package sample.GameChess.PlayGame.PieceClasses;
import javafx.scene.image.Image;


public class PiecePawn extends Piece {
	public static final int WHITE_PLAYER = 1,BLACK_PLAYER = 2,EMPTY_PLAYER = 0;
	boolean firstmove;
	public String name = "Pawn";
	public String imgname = "pawn.png";
	private Piece[][] boardstate;
	private int i;
	private int j;
	private int te;
	
	public PiecePawn(int type, int ii, int jj, boolean fm) {
		super(type);
		te=type;
		i = ii;
		j = jj;
		firstmove = fm;
	}
	
	@Override
	public Image image(){
	 if(te==1)
	 return new Image("sample/CursorPng/whitepawncursor.png");
	 else
	 return new Image("sample/CursorPng/blackpawncursor.png");
	}
	
	@Override
	public int icoord(){
		if(i > 8){
		return 7;}
		
		if(i < 0){
		return 0;}
		
		else{return i;}
	}
	
	@Override
	public int jcoord(){
		if(j > 8){
		return 7;}
		
		if(j < 0){
		return 0;}
		
		else{return j;}
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public String imagefilename(){
		return imgname;
	}
	
	@Override
	public boolean firstmove(){
		return firstmove;
	}
	
	public Piece[][] movepawn(Piece p, Piece t, Piece[][] bs){
		boardstate = bs;
		// Move pawn
		firstmove = false;
		boardstate[t.icoord()][t.jcoord()] = new PiecePawn(p.type(), t.icoord(), t.jcoord(), firstmove);
		boardstate[p.icoord()][p.jcoord()] = new Empty(0, p.icoord(), p.jcoord());
		// Return the new board
		return boardstate;
	}
}
