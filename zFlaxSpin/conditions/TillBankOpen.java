package zFlaxSpin.conditions;

import org.osbot.script.Script;

import zFlaxSpin.util.Condition;

public class TillBankOpen implements Condition{

	private Script script;
	
	public TillBankOpen(Script script){
		this.script = script;
	}
	
	@Override
	public boolean isValid() {
		return script.getBot().getClient().getBank().isOpen();
	}

}
