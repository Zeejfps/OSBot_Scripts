package zFlaxSpin.conditions;

import org.osbot.script.MethodProvider;
import org.osbot.script.Script;

import zFlaxSpin.util.Condition;

public class TillStop implements Condition{
	
	private final Script script;
	
	public TillStop(Script script){
		this.script = script;
	}
	
	@Override
	public boolean isValid() {
		doAntiBan();

		return !script.client.getMyPlayer().isMoving();
	}
	
	private void doAntiBan(){
		final int randNum = MethodProvider.random(0, 100);
		
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
