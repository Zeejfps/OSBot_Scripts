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
		
		final Item flax = script.client.getInventory().getItemForId(flaxId);
		final int amountHave = (int) script.client.getInventory().getAmount(flax);

		final int amountMade = amountHad - amountHave;
		
		if(script.client.getInterface(160) != null){
			return true;
		}

		if(script.client.getInterface(interfaceParent) != null){
			RectangleDestination xButton = new RectangleDestination(script.client.getInterface(interfaceParent).getChild(132).getRectangle());
			try {
				script.client.moveMouse(xButton, false);
				script.sleep(MethodProvider.random(100, 300));
				script.client.clickMouse(false);
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
			script.client.rotateCameraToAngle(MethodProvider.random(-60, 60));
			break;
			
		case 33:
			script.client.rotateCameraToAngle(MethodProvider.random(-80, 50));
			break;
			
		case 12:
			script.client.rotateCameraToAngle(MethodProvider.random(-20, 60));
			break;
			
		case 2:
			script.client.rotateCameraToAngle(MethodProvider.random(-70, 90));
			break;
		default:
			break;
		}
		
	}

}
