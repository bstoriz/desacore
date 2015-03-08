package pstoriz.desacore.level;

import pstoriz.desacore.util.Vector2i;

//Has an instance of another Node
public class Node {
	
	public Vector2i tile;
	public Node parent; //Each node needs to know where it came from to form a path
	public double fCost, gCost, hCost;
	
	/* gCost : Path length. Low cost path is easier vs high cost path is more difficult.
	*  hCost : Actual distance from point a to b
	*  fCost : The sum of g and h Cost  */
	
	public Node(Vector2i tile, Node parent, double gCost, double hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}

}
