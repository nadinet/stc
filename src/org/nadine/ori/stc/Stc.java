package org.nadine.ori.stc;

public class Stc {
	
	public Direction currentDirection;
	 public Coord currCoord;
	public SpanningTree treeTable;
	public Detector detector;
	/***************** Methods ************************/
	
	public  void setCurrentDirection(Direction dir)
	{
			if (dir == Direction.NONE)
				return;
			currentDirection = dir;
	}	
	public  void setCurrentCoord(int r, int c) 
	{
			currCoord.row = r;
			currCoord.col = c;
	}	
	
	public  void setCurrentCoord(Coord coord) 
	{
		currCoord.row = coord.row;
		currCoord.col = coord.col;
	}
		
	public  Cell setNextCell(Coord currP, Direction dir) {
		Coord fcoord = new Coord(currCoord.row , currCoord.col);
			setCurrentDirection(dir);
			switch (dir) {
				case NORTH:
					setCurrentCoord(currP.row - 1, currP.col);
					break;
				case EAST:
					setCurrentCoord(currP.row, currP.col + 1);
					break;
				case SOUTH:
					setCurrentCoord(currP.row + 1, currP.col);
					break;
				case WEST:
					setCurrentCoord(currP.row, currP.col - 1);
					break;
				case NONE:
				default:
					//return HASH.GET(currP); //Nadine: what??
					return null;
			}
			return treeTable.putValue(new Coord(currCoord.row , currCoord.col), fcoord,false);
	}
		
		public void stcAlgorithm(Cell currCell) {
			Coord startCoord = new Coord(0,0);
			Direction nextDirection;
			Coord fcoord;
			boolean toContine = true;
			while (toContine) {
				fcoord = currCell.getFatherPos(); 
				nextDirection = detector.getMoveDirection(currCell); //the son\neighbor to move to
				if (nextDirection == Direction.NONE) {
						if(currCell.getPos().equals(startCoord)) {
							//END OF ALGO
							toContine = false;
						} else {
						setCurrentCoord(fcoord);
						currCell = treeTable.getValue(fcoord);
						// TODO: "HOPA" & Go to father
						}
				} else {
					setCurrentDirection(nextDirection);
				//	if (nextDirection != Direction.NONE) {
					// Go to NEXT
					currCell = setNextCell(currCell.getPos(), nextDirection); //update the cell and currCoord we go to, insert to the Tree & update mambers
				/*} else { //Direction is NONE - How We Get Here??? NONE is in the if block
					if (currCell.getPos().equals(startCoord)) //Starting cell, we are done
						break;
					
					// "HOPA" & Go to father
				}	*/	
			}
		}
}
		public void startStc()
		{
			treeTable = SpanningTree.getInstance();
			detector = Detector.getInstance();
			currentDirection = Direction.NORTH;
			currCoord = new Coord(0,0);
			Cell p = new Cell(new Coord(0,0),null,false);
			treeTable.putValue(0,0,p);
			stcAlgorithm(p);
		}
		
		public static void main(String[] args) {
			Stc stcProssesor = new Stc();
			stcProssesor.startStc();
		}
			
}
