package zFlaxSpin.conditions;

import org.osbot.script.Script;
import org.osbot.script.rs2.model.RS2Object;
import org.osbot.script.rs2.utility.Area;

import zFlaxSpin.util.Condition;

public class TillDoorOpen implements Condition{

	private final Script script;
	private final Area doorArea;
	private final int doorId;
	
	public TillDoorOpen(final Script script, final Area doorArea, final int doorId){
		this.doorArea = doorArea;
		this.doorId = doorId;
		this.script = script;
	}
	
	@Override
	public boolean isValid() {
		
		RS2Object door = script.closestObject(doorArea, doorId);
		
		return door == null;
	}

}
