package zFlaxSpin.conditions;

import org.osbot.script.Script;

import zFlaxSpin.util.Condition;

public class TillNotValid implements Condition{
	
	private final Script script;
	private final int interfaceId;
	
	public TillNotValid(final Script script, final int interfaceId){
		this.script = script;
		this.interfaceId = interfaceId;
	}
	
	@Override
	public boolean isValid() {
		return script.client.getInterface(interfaceId) == null;
	}

}