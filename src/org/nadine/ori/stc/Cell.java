package org.nadine.ori.stc;

public class Cell {	
	public Coord pos;
	public Coord fatherPos;
	boolean isObstacle;
	
	public Cell(Coord coord,Coord fcoord, boolean isObst) {
		pos.row = coord.row;
		pos.col = coord.col;
		fatherPos.col = fcoord.col;
		fatherPos.row = fcoord.row;
		isObstacle = isObst;
	}
	public Cell(Coord coord,Coord fcoord) {
		pos.row = coord.row;
		pos.col = coord.col;
		fatherPos.col = fcoord.col;
		fatherPos.row = fcoord.row;
		isObstacle = false;
	}
	public Cell(Coord fcoord) {
		fatherPos.col = fcoord.col;
		fatherPos.row = fcoord.row;
		isObstacle = false;
	}
	/*
	 * This is Question to the tree.
	 * 
	public CellStatus GetStatus() {
		
		return status;
	}
	*/
	public Coord getPos() {
		return pos;
	}
	
	public Coord getFatherPos() {
		return fatherPos;
	}
	public void SetFatherPos(Coord fcoord) {
		fatherPos.col = fcoord.col;
		fatherPos.row = fcoord.row;
	}
	public void SetBorder() {
		isObstacle = true;
	}
	public boolean isBorder()
	{
		return isObstacle;
	}
	public void SetObstacle() {
		isObstacle = true;
	}
	public boolean isObstacle()
	{
		return isObstacle;
	}
	@Override
	public boolean equals(Object obj) {
			Cell c = (Cell)obj;
			return (c.isObstacle == isObstacle  && fatherPos.equals(c.fatherPos));	
	}
}
