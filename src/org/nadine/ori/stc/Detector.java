package org.nadine.ori.stc;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
 * 
 * @author dini
 *
 */
public class Detector {
	
	private SpanningTree treeTable;
	private static Detector _instance = null;
	protected Detector(){
		treeTable = SpanningTree.getInstance();
	}
	 public static Detector getInstance(){
		 if (_instance == null){
			 _instance = new Detector();
		 }
		 return _instance;
	 }
	public Direction getMoveDirection(Cell curr)
	{
		/*
		 * according to the Mat frame Detect witch  is the first new neighbor Counterclockwise
		 *   returns his direction. NONE if there is NO neighbor to move to.
		 *   ** in case one of the neighbor is border will update the TreeTable.       
		 */
		return Direction.NONE;
	}
	public Mat process(Mat inputImage) {
		Mat invcolor = new Mat (inputImage.rows(),inputImage.cols(),inputImage.type(),new Scalar(255,255,255));
		Core.subtract(invcolor, inputImage, inputImage);
		return inputImage;
	}

}
