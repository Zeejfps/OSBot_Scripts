package zFlaxSpin.conditions;

import org.osbot.script.MethodProvider;
import org.osbot.script.Script;
import org.osbot.script.mouse.RectangleDestination;
import org.osbot.script.rs2.model.Item;

import zFlaxSpin.util.Condition;

public class WhileWorking implements Condition{
	
	private final int flaxId;
	private final int amountToMake;
	private final int amountHad;
	private final int interfaceParent;
	private final Script script;
	
	public WhileWorking(final Script script, final int flaxId, final int amountToMake, final int amountHad, final int interfaceParent){
		this.script = script;
		this.flaxId = flaxId;
		this.amountToMake = amountToMake;
		this.amountHad = amountHad;
		this.interfaceParent= interfaceParent;
	}
	
	@Override
	public boolean isValid() {
		
		final Item flax = script.getBot().getClient().getInventory().getItemForId(flaxId);
		final int amountHave = (int) script.getBot().getClient().getInventory().getAmount(flax);

		final int amountMade = amountHad - amountHave;
		
		if(script.getBot().getClient().getInterface(160).isValid()){
			return true;
		}

		if(script.getBot().getClient().getInterface(interfaceParent).isValid()){
			RectangleDestination xButton = new RectangleDestination(script.getBot().getClient().getInterface(interfaceParent).getChild(132).getRectangle());
			try {
				script.getBot().getClient().moveMouse(xButton, false);
				script.sleep(MethodProvider.random(100, 300));
				script.getBot().getClient().clickMouse(false);
				return true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		doAntiBan();
		
		return amountMade == amountToMake;
	}
	
	private void doAntiBan(){
		final int randNum = MethodProvider.random(0, 150);
		
		switch(randNum){
		
		case 1:
			try {
				script.getBot().getClient().rotateCameraToAngle(MethodProvider.random(-60, 60));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
			
		case 33:
			try {
				script.getBot().getClient().rotateCameraToAngle(MethodProvider.random(-80, 50));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
			
		case 12:
			try {
				script.getBot().getClient().rotateCameraToAngle(MethodProvider.random(-20, 60));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
			
		case 2:
			try {
				script.getBot().getClient().rotateCameraToAngle(MethodProvider.random(-70, 90));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		
	}

}
