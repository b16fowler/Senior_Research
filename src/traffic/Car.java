package traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.graph.Network;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;

/*
 * Github
 * Presentation material
 * Division problem
 */

public class Car extends Builder {
	
	//Variables
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private int initialX, initialY;
	private int direction;				//0 = horizontal, 1 = vertical
	//private static int clock = 0;
	//private static int c = 0;
	//private boolean color = true;
	private int turn;
	Random rng = new Random();
	
	public static final int NUM_LIGHTS = 25;
	public static final int LIGHT_LENGTH = 5;
	
	public static int[] xpos = new int[NUM_LIGHTS]; 		  //Positions of light(s) *test*
	public static int[] ypos = new int[NUM_LIGHTS];
	public static boolean[] color = new boolean[NUM_LIGHTS];
	public static boolean light_color = true;
	
	//Test - arrays to hold current locations of all cars
	public static int[] carx = new int[NUM_CARS];
	public static int[] cary = new int[NUM_CARS];
	public static int[] startx = new int[NUM_CARS];
	public static int[] starty = new int[NUM_CARS];
	public static int s;
	
	public static boolean[] go = new boolean[NUM_CARS];
	public static boolean pause = false;
	public static int q = 0; 						//iterater for arrays
	public static int c = 0;
	
	public static int[] finals = new int[1000];
	public static int[] dis = new int[NUM_CARS];
	public static int[] time = new int[NUM_CARS];
	public static int[] rate = new int[NUM_CARS];
	public static int t = 0;
	
	//Constructor
	public Car(ContinuousSpace<Object> space, Grid<Object> grid, int x, int y, boolean direction) {
		this.space = space;
		this.grid = grid;
		this.initialX = x;
		this.initialY = y;
	}
	
	//Methods
	@ScheduledMethod(start = 1000, interval = 1000)
	public void results() {
		System.out.println("\n");
		for (int i = 0; i < finals.length; i++) {
			System.out.print(finals[i] + " ");
		}
		System.out.println();
	}
	//Moves car to non-random location
	@ScheduledMethod(start = 1, interval = 10000)
	public void move() {
		
		xpos[0] = 5; ypos[0] = 5;
		xpos[1] = 5; ypos[1] = 10;
		xpos[2] = 5; ypos[2] = 15;
		xpos[3] = 5; ypos[3] = 20;
		xpos[4] = 5; ypos[4] = 25;
		xpos[5] = 10; ypos[5] = 5;
		xpos[6] = 10; ypos[6] = 10;
		xpos[7] = 10; ypos[7] = 15;
		xpos[8] = 10; ypos[8] = 20;
		xpos[9] = 10; ypos[9] = 25;
		xpos[10] = 15; ypos[10] = 5;
		xpos[11] = 15; ypos[11] = 10;
		xpos[12] = 15; ypos[12] = 15;
		xpos[13] = 15; ypos[13] = 20;
		xpos[14] = 15; ypos[14] = 25;
		xpos[15] = 20; ypos[15] = 5;
		xpos[16] = 20; ypos[16] = 10;
		xpos[17] = 20; ypos[17] = 15;
		xpos[18] = 20; ypos[18] = 20;
		xpos[19] = 20; ypos[19] = 25;
		xpos[20] = 25; ypos[20] = 5;
		xpos[21] = 25; ypos[21] = 10;
		xpos[22] = 25; ypos[22] = 15;
		xpos[23] = 25; ypos[23] = 20;
		xpos[24] = 25; ypos[24] = 25;
		
		for (int i = 0; i < NUM_CARS; i++) {
			go[i] = true; 							//true = car moving
		}
		for (int i = 0; i < NUM_CARS; i++) {
			time[i] = 1;
		}
		for (int i = 0; i < NUM_CARS; i++) {
			dis[i] = 1;
		}
		
		//direction = rng.nextInt(2);
		//direction = 1; //TEST
		startx[s] = initialX;
		starty[s] = initialY;
		s++;
		space.moveTo(this, initialX, initialY); 
		grid.moveTo(this, initialX, initialY);
			
	}//End move

	//Light method
	@ScheduledMethod(start = 1, interval = LIGHT_LENGTH)
	public void changeLight() {
		light_color ^=true;
		//light_color = true;
		//System.out.println("Light: " + light_color);
	}
	
	//TEST method
	@ScheduledMethod(start = 1, interval = 1)
	public void run() {
		//q = 0;
		//System.out.println("Top of run method");
		step();
		
		if (c == NUM_CARS) {
			//System.out.println("Car's x positions: ");
			for(int i = 0; i < NUM_CARS; i++) {
				//System.out.print(carx[i] + " ");
			}
			//System.out.println("\nCar's y positions: ");
			for(int i = 0; i < NUM_CARS; i++) {
				//System.out.print(cary[i] + " ");
			}
			//System.out.println("\nGo array: ");
			for (int i = 0; i < NUM_CARS; i++) {
				//System.out.println(go[i] + " ");
			}
			//System.out.println("\nq: " + q);
			//System.out.println("c: " + c);
			c = 0;
			q = 0;
		}
	}
		
	//@ScheduledMethod(start = 1, interval = 1)
	public void step() {
		//Get grid location of current zombie
		//GridPoint pt = grid.getLocation(this);
		
		//Use the GridCellNgh class to create GridCells for surrounding neighborhood
		//GridCellNgh<Human> nghCreator = new GridCellNgh<Human>(grid, pt, Human.class, 1, 1);
		
		//Import repast.simphony.query.space.grid.GridCell
		//List<GridCell<Human>> gridCells = nghCreator.getNeighborhood(true);
		//SimUtilities.shuffle(gridCells, RandomHelper.getUniform());
		
		GridPoint pt = grid.getLocation(this);
		GridCellNgh<Car> nghCreator = new GridCellNgh<Car>(grid, pt, Car.class, 1, 1);
		
		List<GridCell<Car>> gridCells = nghCreator.getNeighborhood(true);
		for(GridCell<Car> cell : gridCells) {
			if(cell.size() == 1) {
				//stop
			}
		}
		
		/*
		
		GridPoint pointWithMostHumans = null;
		int maxCount = -1;
		for (GridCell<Human> cell : gridCells) {
			if (cell.size() > maxCount) {
				pointWithMostHumans = cell.getPoint();
				maxCount = cell.size();
			}
		}
		
		for (GridCell<Human> cell : gridCells) {
			if (cell.size() > maxCount) {
				pointWithMostHumans = cell.getPoint();
				maxCount = cell.size();
			}
		}
		moveTowards(pointWithMostHumans);
		infect();
		*/

		
		//GridPoint gp = new GridPoint(25,40);
		
		//Grid gp = grid.getLocation(this);
		
		NdPoint  myPoint = space.getLocation(this);
		double x = myPoint.getX();
		double y = myPoint.getY();
		turn = 0;
		
		int diff = 0; //Distance away from closest light
		//checking if car is leaving model
		//if leaving, rng for new start spot and direction
		if (x == 29 || y == 29) {
			dis[q] = 0;
			time[q] = 1;
			direction = rng.nextInt(2);
			if (direction == 0) {
				x = 0;
				y = rng.nextInt((int) Math.sqrt(NUM_LIGHTS)) + 1;
				y = y * LIGHT_LENGTH;
			}
			else if (direction == 1) {
				x = rng.nextInt((int) Math.sqrt(NUM_LIGHTS)) + 1;
				x = x * LIGHT_LENGTH;
				y = 0;
			}
			
			startx[q] = (int) x;
			starty[q] = (int) y;
			space.moveTo(this, x, y);
			grid.moveTo(this, (int)x, (int)y);
		}
		//Finding closest light
		else if (direction == 0) {								//horizontal
			for (int i = 0; i < NUM_LIGHTS; i++) {
				if (y == ypos[i]) {								//looking for lights in same horizontal line
					diff = (int) (xpos[i] - x);
					if (diff == 1) {
						break;
					}
				}
			}
		}
		else if (direction == 1) {								//vertical
			for (int i = 0; i < NUM_LIGHTS; i++) {
				if (x == xpos[i]) {
					diff = (int)(ypos[i] - y);
					if (diff == 1) {
						break;
					}
				}
			}
		}
		
		//////////////////////////////////////////////////////////
		//Car stopped in front of this
		pause = false;
		for (int w = 0; w < NUM_CARS; w++) {
			if (direction == 0 && x == carx[w] - 1 && y == cary[w] && go[w] == false) {		//horizontal
				go[q] = false;
				pause = true;
				dis[q]++;
				//System.out.println("\n***BEHIND STOPPED CAR***\n");
			}
			else if (direction == 1 && y == cary[w] - 1 && x == carx[w] && go[w] == false) {
				go[q] = false;
				pause = true;
				dis[q]++;
				//System.out.println("\n***BEHIND STOPPED CAR***\n");
			}
		}
			
		//One space before closest light
		if (direction == 0 && pause == false) {								//Horizontal movement
			//System.out.println("x of car: " + x);
			//System.out.println("y of car: " + y);
			if (diff == 1) {										//at light
				//System.out.println("CAR AT LIGHT");
				if (light_color == true) {						//light is true
					space.moveTo(this, x + 1, y);				//go under light
					grid.moveTo(this, (int)x + 1, (int)y);
				}
				else {											//light is false
					//System.out.println("Car stopping...");
					go[q] = false;
					//wait
				}
			}
			else {												//not at light
				//System.out.println("x: " + x + ", y: " + y);
				space.moveTo(this, x + 1, y);					//standard car movement
				grid.moveTo(this, (int)x + 1, (int)y);
				go[q] = true;
			}	
		}
		else if (direction == 1 && pause == false) {								//Vertical movement
			for (int i = 0; i < NUM_LIGHTS; i++) {
				//System.out.println("x of car: " + x);
				//System.out.println("y of car: " + y);
				if (diff == 1) {									//at light
					//System.out.println("CAR AT LIGHT");
					if (light_color == false) {					//light is true
						space.moveTo(this, x, y + 1);			//go under light
						grid.moveTo(this, (int)x, (int)y + 1);
					}
					else {										//light is false
						//System.out.println("Car stopping...");
						go[q] = false;
						//wait
					}
				}
				else {											//not at light
					space.moveTo(this, x, y + 1);				//standard car movement
					grid.moveTo(this, (int)x, (int)y + 1);
					go[q] = true;
				}
			}	
		}

		//Under light, turning or not turning
		if (direction == 0) {
			for (int i = 0; i < NUM_LIGHTS; i++) {
				if (x == xpos[i] && y == ypos[i]) {
					turn = rng.nextInt(2);						//car turn percentage
					if (turn == 0) {								//go straight
						space.moveTo(this, x + 1, y);		
						grid.moveTo(this, (int)x + 1, (int)y);
					}
					else {										//turn
						space.moveTo(this, x, y + 1);			
						grid.moveTo(this, (int)x, (int)y + 1);
						direction = 1;
					}
				}
			}
		}
		else if (direction == 1) {
			for (int i = 0; i < NUM_LIGHTS; i++) {
				if (y == ypos[i] && x == xpos[i]) {
					turn = rng.nextInt(2);						//car turn percentage
					if (turn == 0) {								//go straight
						space.moveTo(this, x, y  + 1);		
						grid.moveTo(this, (int)x, (int)y + 1);
					}
					else {										//turn
						space.moveTo(this, x  + 1, y);			
						grid.moveTo(this, (int)x + 1, (int)y);
						direction = 0;
					}
				}
			}
		}

		//logging position of car
		//System.out.println("Logging car position...");
		//System.out.println("index: " + q);
		carx[q] = (int) x;
		cary[q] = (int) y;
		if (go[q] == true) {
			dis[q]++;
		}
		
		
		dis[q] = (int) (x - startx[q]) + (int) (y - starty[q]);
		if (dis[q] == 0) {
			dis[q]++;
		}
		if (time[q] != 0) {
			rate[q] = dis[q] / time[q];
		}
		System.out.println("Distance: " + dis[q]);
		System.out.println("Time: " + time[q]);
		System.out.println("Rate: " + rate[q]);
		
			
		if (x == 28 || y == 28) {
			dis[q] = (int) (x - startx[q]) + (int) (y - starty[q]);
			rate[q] = dis[q] / time[q];
			finals[t] = rate[q];
			t++;
			System.out.println("\n/////////////");
			System.out.println("Speed: " + rate[q]);
			System.out.println("/////////////\n");
			rate[q] = 0;
		}
		
		time[q]++;
		q++;
		c++;
		
		myPoint = space.getLocation(this);		
		grid.moveTo(this , (int)myPoint.getX(), (int)myPoint.getY ());
		
		//grid.moveTo(this,  (int) x+1, (int)y);
		
		//space.moveByVector(this , 1, angle , 0);
	
		//myPoint = space.getLocation(this);
		//grid.moveTo(this , (int)myPoint.getX(), (int)myPoint.getY ())
		
		
	    //moveTowards(gp);
		
	}//End step()
	
	public void moveTowards(GridPoint pt) {
		//Only move if we are not already in this grid location
		if (!pt.equals(grid.getLocation(this))) {
			NdPoint myPoint = space.getLocation(this);
			NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
			double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
			space.moveByVector(this, 1, angle, 0);
			myPoint = space.getLocation(this);
			grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());
			
			//moved = true;
		}	
	}
	
	/*
	public void infect() {
		GridPoint pt = grid.getLocation(this);
		List<Object> humans = new ArrayList<Object>();
		for (Object obj : grid.getObjectsAt(pt.getX(), pt.getY())) {
			if (obj instanceof Car) {
				humans.add(obj);
			}
		}
		if (humans.size() > 0) {
			int index = RandomHelper.nextIntFromTo(0, humans.size() - 1);
			Object obj = humans.get(index);
			NdPoint spacePt = space.getLocation(obj);
			Context<Object> context = ContextUtils.getContext(obj);
			context.remove(obj);
			Zombie zombie = new Zombie(space, grid);
			context.add(zombie);
			//MoveTo
			space.moveTo(zombie, spacePt.getX(), spacePt.getY());
			grid.moveTo(zombie, pt.getX(), pt.getY());
			
			Network<Object> net = (Network<Object>)context.getProjection("infection network");
			net.addEdge(this, zombie);
		}
	}
	*/
}
