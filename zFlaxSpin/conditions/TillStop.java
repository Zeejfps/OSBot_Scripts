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

		return !script.getBot().getClient().getMyPlayer().isMoving();
	}
	
	private void doAntiBan(){
		final int randNum = MethodProvider.random(0, 200);
		
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
