package zFlaxSpin.conditions;

import org.osbot.script.rs2.model.RS2Object;

import zFlaxSpin.util.Condition;

public class TillDoorOpen implements Condition{

	private final RS2Object door;
	
	public TillDoorOpen(RS2Object door){
		this.door = door;
	}
	
	@Override
	public boolean isValid() {

		return !door.exists();
	}

}
