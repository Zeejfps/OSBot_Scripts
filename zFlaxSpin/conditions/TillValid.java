package zFlaxSpin.conditions;

import org.osbot.script.Script;

import zFlaxSpin.util.Condition;

public class TillValid implements Condition{
	
	private final Script script;
	private final int interfaceId;
	
	public TillValid(final Script script, final int interfaceId){
		this.script = script;
		this.interfaceId = interfaceId;
	}
	
	@Override
	public boolean isValid() {
		return script.getBot().getClient().getInterface(interfaceId).isValid();
	}

}
