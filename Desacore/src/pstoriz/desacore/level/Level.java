package pstoriz.desacore.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import pstoriz.desacore.Screen;
import pstoriz.desacore.entity.Entity;
import pstoriz.desacore.entity.mob.Player;
import pstoriz.desacore.entity.particle.Particle;
import pstoriz.desacore.entity.projectile.Projectile;
import pstoriz.desacore.graphics.ImageLoader;
import pstoriz.desacore.level.tile.Tile;
import pstoriz.desacore.util.Vector2i;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	// contains color values of every pixel in that level
	protected int[] tiles;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();

	//Takes in two node objects and returns and integer
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			//Move up or down in the array based off of comparing fcosts
			if (n1.fCost < n0.fCost) return +1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	
	private List<Player> players = new ArrayList<Player>();
	
	public ImageLoader glow = new ImageLoader("/textures/appearance/Glow.png",
			"/textures/appearance/Glow_alpha.png", 144, 144);

	// public static Level spawn = new Level("/location");

	// Constructor for the level
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	protected void generateLevel() {

	}

	protected void loadLevel(String path) {

	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}
		remove();
	}
	
	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) players.remove(i);
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	@SuppressWarnings("unused")
	private void time() {

	}

	// x and y is position of Entity. size is
	// the size of object
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		// Collision detection for the corners
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}

	// Corner "pin" almost. Defines which area of the map we want to render
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		// renders all the tiles until it hits the end of the screen
		int x0 = xScroll >> 4; // Same as divided by 16 (Size of our tiles).
								// Pixel level not tile level
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4; // Define render region of
														// the screen
		for (int y = y0; y < y1; y++) { // Cycles through every pixel
			for (int x = x0; x < x1; x++) { // Rendering from top part of screen
											// from bottom part of the screen
				getTile(x, y).render(x, y, screen); // Tile precision
			}
		}

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
	}
	
	public void renderParticles(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
	}

	// Seperate render method for the Above Tile
	public void renderAbove(int xScroll, int yScroll, Screen screen, Tile tile) {
		screen.setOffset(xScroll, yScroll);
		// renders all the tiles until it hits the end of the screen
		int x0 = xScroll >> 4; // Same as divided by 16 (Size of our tiles).
								// Pixel level not tile level
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4; // Define render region of
														// the screen
		for (int y = y0; y < y1; y++) { // Cycles through every pixel
			for (int x = x0; x < x1; x++) { // Rendering from top part of screen
											// from bottom part of the screen
				if (getTile(x, y).isAbove()) {
					getTile(x, y).render(x, y, screen); // Tile precision
				}
			}
		}
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((Player) e);
		} else {
			entities.add(e);				
		}
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayerAt(int index) {
		return players.get(index);
	}
	
	public Player getClientPlayer() {
		return players.get(0);
	}
	
	// A* Search algorithm
	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>(); //Tiles considering moving to
		List<Node> closedList = new ArrayList<Node>(); //Tiles not to move to
		Tile check = null;
		Node current = new Node(start, null, 0, getDistance(start, goal)); //Place to start for alrogithm
		openList.add(current);
		//While there's a path, keep calculating
		while (openList.size() > 0) { 
			//Sorts the list based on whether nodeSorter is +1 or -1
			Collections.sort(openList, nodeSorter); //Sorts in order from lowest fcost to highest fcost
			current = openList.get(0); //returns the very first node
			//if the place we're checking is the finish, then stop checking
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					//Retracing steps from finish to start
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current); //Not looking at tile you were just on (that would be pointless)
			//Check every single adjacency
			for (int i = 0; i < 9; i++) {
				if (i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				
				//selects the current neighbor
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if (at == null) continue;
				if (at.solid()) {
					if (i == 0 || i == 6) check = at;
					continue;
				}
				if (at.isHarmful()) {
					if (i == 0 || i == 6) check = at;
					continue;
				}
				if (at.isSlow()) {
					if (i == 0 || i == 6) check = at;
					continue;
				}
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95); //adds 1 or 0.95
				if (check != null) {
					if (i == 1 || i == 7) gCost = 3.2;
				}
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				//Has tile already been visited
				if (vecInList(closedList, a) && gCost >= node.gCost) continue;
				if (!vecInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}
	
	//Checks if a vector is in a list of nodes
	private boolean vecInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector)) return true;
		}
		return false;
	}
	
	//Calculates the distance between two vectors
	public double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	//Finds entities in range
	public ArrayList<Entity> getEntities(Entity e, int radius) {
		ArrayList<Entity> result = new ArrayList<Entity>();
		int ex = (int) e.getX();
		int ey = (int) e.getY();
		for (int i = 0; i < entities.size(); i++) {
			if (e.equals(entities.get(i))) continue;
			double x = entities.get(i).getX();
			double y = entities.get(i).getY();	
			double distance = Math.hypot(x - ex, y - ey);
			//Pathagorean theorem to find distance
			if (distance <= radius) result.add(entities.get(i));
		}
		return result;
	}
	
	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		double ex = e.getX();
		double ey = e.getY();
		for (Player p : players) {
			double x = p.getX();
			double y = p.getY();
			//Pathagorean theorem to find distance
			double distance = Math.hypot(x - ex, y - ey);
			if (distance <= radius) result.add(p);
		}
		return result;
	}
	
	// Renders specific tiles
	public Tile getTile(int x, int y) {
		// If you get outside the map boundaries it returns a voidTile
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.voidTile;
		if (tiles[x + y * width] == Tile.col_stone1)
			return Tile.stone1;
		if (tiles[x + y * width] == Tile.col_stone2)
			return Tile.stone2;
		if (tiles[x + y * width] == Tile.col_stone3)
			return Tile.stone3;
		if (tiles[x + y * width] == Tile.col_grass)
			return Tile.grass;
		if (tiles[x + y * width] == Tile.col_wood)
			return Tile.wood;
		if (tiles[x + y * width] == Tile.col_water)
			return Tile.water;
		if (tiles[x + y * width] == Tile.col_lava)
			return Tile.lava;
		if (tiles[x + y * width] == Tile.col_wall)
			return Tile.wall;
		if (tiles[x + y * width] == Tile.col_wallAbove)
			return Tile.wallAbove;
		if (tiles[x + y * width] == Tile.col_wallLeft)
			return Tile.wallLeft;
		if (tiles[x + y * width] == Tile.col_wallRight)
			return Tile.wallRight;
		if (tiles[x + y * width] == Tile.col_topLeftCorner)
			return Tile.topLeftCorner;
		if (tiles[x + y * width] == Tile.col_topRightCorner)
			return Tile.topRightCorner;
		if (tiles[x + y * width] == Tile.col_bottomLeftCorner)
			return Tile.bottomLeftCorner;
		if (tiles[x + y * width] == Tile.col_bottomRightCorner)
			return Tile.bottomRightCorner;
		if (tiles[x + y * width] == Tile.col_topRightInv)
			return Tile.topRightInv;
		if (tiles[x + y * width] == Tile.col_topLeftInv)
			return Tile.topLeftInv;
		return Tile.voidTile;
	}

	public boolean getIsAbove(Tile tile) {
		return tile.isAbove();
	}

	public Tile chooseTile(Tile tile0, Tile tile1, Tile tile2) {
		Random r = new Random();
		int num = r.nextInt(2);
		if (num == 0)
			return tile0;
		if (num == 1)
			return tile1;
		return tile2;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
