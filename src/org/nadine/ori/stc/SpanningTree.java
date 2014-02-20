package org.nadine.ori.stc;
import java.util.HashMap;
public class SpanningTree {
	
	 private HashMap<Integer, HashMap<Integer,Cell>> Table;
	 private static SpanningTree _instance=null;
	 
	 protected SpanningTree() {
		 Table = new HashMap<Integer,HashMap<Integer, Cell>>();
	 }
	 
	 public static SpanningTree getInstance(){
		 if (_instance == null){
			 _instance = new SpanningTree();
		 }
		 return _instance;
	 }
	 
	 public Cell putValue(Coord coord ,Cell p)
	 {
		 if(!Table.containsKey(coord.row))
			 Table.put(coord.row,new HashMap<Integer,Cell>());
		 assert(!(Table.get(coord.row).containsKey(coord.col))):
			 		"Tring to Add Cell to Coord we have already visited";
		 Table.get(coord.row).put(coord.col, p);
		 return p;
	 }
	 public Cell putValue(int x, int y,Cell p)
	 {
		 if(!Table.containsKey(x))
			 Table.put(x,new HashMap<Integer,Cell>());
		 assert(!(Table.get(x).containsKey(y))):
			 		"Tring to Add Cell to Coord we have already visited";
		 Table.get(x).put(y,p);
		 return p;
	 }
	 public Cell putValue(Coord coord ,Coord fcoord,boolean isObst)
	 {
		 Cell p = new Cell(coord,fcoord,isObst);
		 return putValue(coord,p);
	 }
	 public Cell putValue(int x,int y ,Coord fcoord,boolean isObst)
	 {
		 Cell p = new Cell(new Coord(x,y),fcoord,isObst);
		 return putValue(x,y,p);
	 }
	 public Cell putValue(int x,int y ,int f_x,int f_y,boolean isObst)
	 {
		 Cell p = new Cell(new Coord(x,y),new Coord(f_x,f_y),isObst);
		 return putValue(x,y,p);
	 }
	 
	 public Cell getValue(Coord coord)
	 {
		HashMap<Integer,Cell> row = Table.get(coord.row);
		if(row == null)
			return null;
		return row.get(coord.col);
	 }
	 public Cell getValue(int x,int y)
	 {
		 Coord coord = new Coord(x,y);
		 return getValue(coord);
	 }
	 
	 public boolean contains(Coord coord)
	 {
		 boolean b = Table.containsKey(coord.row) ?
				 			Table.get(coord.row).containsKey(coord.col): false;
		return b;
	 }
	 public boolean contains (int x,int y)
	 {
		 return contains(new Coord(x,y)); 
	 }
	 /*returns null in case there is no pos in this coord. 
	   notice that the father of (0,0)is always null*/
	 public Coord getFatherCoord(Coord coord)
	 {
		 Cell p = getValue(coord);
		 if(p == null)
			 return null;
		 return p.getFatherPos();
	 }
	 public CellStatus getStatus(int x,int y)
	 {
		 Cell p = getValue(x,y);
		 if(p == null)
			 return CellStatus.NEW;
		 if(p.isObstacle)
			 return CellStatus.BORDER;
		 return CellStatus.OLD;
	 }
	 
	 public CellStatus getStatus(Coord coord)
	 {
		 return getStatus(coord.row,coord.col);
	 }
}

