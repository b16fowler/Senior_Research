package traffic;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Light extends Builder {
	
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private int posX, posY;
	public boolean light_color = true;
	
	public Light(ContinuousSpace<Object> space, Grid<Object> grid, int x, int y, boolean color) {
		this.space = space;
		this.grid = grid;
		this.posX = x;
		this.posY = y;
		this.light_color = color;
	}
	
	@ScheduledMethod(start = 1, interval = 100000)
	public void addlight() {
		
		//moving light to non-random location
		grid.moveTo(this, posX, posY);
		space.moveTo(this, posX, posY);
		
	}//End addlight()	
	
	
	public boolean getColor() {
		return light_color;
	}
	
	/*
	@ScheduledMethod(start = 1, interval = 1)
	public void changelight() {
		
		clock = getClock();
		System.out.println("Clock: " + clock);
		
		if (clock % LIGHT_LENGTH == 0) {
			color ^= true;
			System.out.println("Color: " + color);
		}
		
		if (color == true) {
			System.out.println("Light is green.");
		}
		else {
			System.out.println("Light is red.");
		}
	}//End changelight()
	*/
	
}
