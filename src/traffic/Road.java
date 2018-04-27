package traffic;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Road {
	
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private int x, y;
	
	public Road(ContinuousSpace<Object> space, Grid<Object> grid, int x, int y) {
		this.space = space;
		this.grid = grid;
		this.x = x;
		this.y = y;
	}
	
}
