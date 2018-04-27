package traffic;

import java.util.Random;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;
import repast.simphony.util.collections.ListIndexedIterable;

public class Builder implements ContextBuilder<Object> {
	
	public static int clock = 0;
	public static int count = 0;
	public static final int NUM_LIGHTS = 25;
	public static final int NUM_CARS = 1;
	public static final int LIGHT_LENGTH = 5;
	
	public static int[] xpos = new int[NUM_LIGHTS]; 		  //Positions of light(s) *test*
	public static int[] ypos = new int[NUM_LIGHTS];
	
	Random rng = new Random();
	int x = 0;
	int y = 0;
	
	@Override
	public Context build(Context<Object> context) {
		
		context.setId("Traffic");
		ContinuousSpaceFactory spaceFactory = 
				ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = 
				spaceFactory.createContinuousSpace("space", context, 
						new RandomCartesianAdder<Object>(), 
						new repast.simphony.space.continuous.StrictBorders(),
						30, 30);
		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		//Correct import: import repast.simphony.space.grid.WrapAroundBorders;
		Grid<Object> grid = gridFactory.createGrid("grid", context,
				new GridBuilderParameters<Object>(new WrapAroundBorders(),
						new SimpleGridAdder<Object>(),
						true, 30, 30));
		
		//Parameters params = RunEnvironment.getInstance().getParameters();
		
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
		
		for (int i = 0; i < NUM_LIGHTS; i++) {
			context.add(new Light(space, grid, xpos[i], ypos[i], true));
		}
		
		//Test: car locations same as lights
		for (int i = 0; i < NUM_CARS; i++) {
			context.add(new Car(space, grid, xpos[i], ypos[i], true)); //Starting location of car
			
		}
			
		//TEST CARS
		//context.add(new Car(space, grid, 1, 10, true, 0)); //Starting location of car
		//context.add(new Car(space, grid, 3, 10, true, 0)); //Starting location of car
		//context.add(new Car(space, grid, 5, 10, true, 0)); //Starting location of car
		
		//ListIndexedIterable <Car> = context.getObjects(Class <Car>);
		
	
		//ListIndexedIterable <Car> iter = context.getObjects(<Car>);
		
		//Roads
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				if (i % 4 == 0 || i % 6 == 0) {
					if (j % LIGHT_LENGTH == 0) {
						context.add(new Road(space, grid, i, j));
					}	
				}
				if (j % 4 == 0 || j % 6 == 0) {
					if (i % LIGHT_LENGTH == 0) {
						context.add(new Road(space, grid, i, j));
					}	
				}
			}
		}	
		
		for (Object obj : context) {
			NdPoint pt = space.getLocation(obj);
			grid.moveTo(obj, (int)pt.getX(), (int)pt.getY());
			//grid.moveTo(obj, 25, 25);
		}
		
		return context;
	}//End context
	
	
	
}