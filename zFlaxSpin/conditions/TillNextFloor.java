package zFlaxSpin.conditions;

import org.osbot.script.Script;

import zFlaxSpin.util.Condition;

public class TillNextFloor implements Condition{
	
	private final Script script;
	private final int floor;
	
	public TillNextFloor(Script script, int floor){
		this.script = script;
		this.floor = floor;
	}

	@Override
	public boolean isValid() {
		return script.getBot().getClient().getMyPlayer().getZ() != floor;
	}
}
