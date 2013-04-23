package zFlaxSpin.main;

import java.awt.Graphics;
import java.awt.Point;

import org.osbot.script.Script;
import org.osbot.script.ScriptManifest;
import org.osbot.script.mouse.RectangleDestination;
import org.osbot.script.rs2.map.Position;
import org.osbot.script.rs2.model.Item;
import org.osbot.script.rs2.model.RS2Object;
import org.osbot.script.rs2.ui.RS2Interface;
import org.osbot.script.rs2.utility.Area;

import zFlaxSpin.conditions.TillBankOpen;
import zFlaxSpin.conditions.TillDoorOpen;
import zFlaxSpin.conditions.TillNextFloor;
import zFlaxSpin.conditions.TillNotValid;
import zFlaxSpin.conditions.TillStop;
import zFlaxSpin.conditions.TillValid;
import zFlaxSpin.conditions.WhileWorking;
import zFlaxSpin.util.Condition;
import zFlaxSpin.enums.Task;

@ScriptManifest(author = "Zeejfps", info = "Spinns and banks flax into \nbowstrings at lumbridge castle.", name = "zFlaxSpinner V1.3", version = 1.3D)
public class MainScript extends Script{

	private final int flaxId = 1779;//1737;
	private final int amountToMake = 28; //3;
	private final int interfaceParent = 459;
	private final int interfaceChild = 89;//98;
	
	//private int startXp;
	private int startTime;
	private int timeRunning;
	//private int xpGained;
	
	private final Area bankArea = new Area(new Position[] {
			new Position(3207, 3215, 2),
			new Position(3210, 3215, 2),
			new Position(3207, 3220, 2),
			new Position(3210, 3220, 2)
	});
	
	private final Area spinnerArea = new Area(new Position[] {
			new Position(3208, 3214, 1),
			new Position(3213, 3214, 1),
			new Position(3208, 3217, 1),
			new Position(3213, 3217, 1)
	});
	
	private final Area stairsArea = new Area(new Point[] {
			
			new Point(3205, 3208),
			new Point(3207, 3208),
			new Point(3205, 3211),
			new Point(3207, 3211)
			
	});
	
	private int counter = 0;
	
	@Override
	public void onPaint(Graphics g){
		updateInfo();
		g.drawString(formatTime(timeRunning), 8, 89);
		//g.drawString(Integer.toString(xpGained), 8, 105);
	}
	
	@Override
	public void onStart(){
		//startXp = client.getSkills().getExperience(Skill.CRAFTING);
		startTime = (int) System.currentTimeMillis();
	}
	
	@Override
	public int onLoop(){
		
		//log("in loop");
		final Area area;
		final Position destination;
		final Task task;
		final int correctFloor;
		
		try{
			if(client.getInventory().contains(flaxId)){
				//log("Has Flax");
				area = spinnerArea;
				destination = new Position(3209, 3214, 1);
				correctFloor = 1;
				task = Task.SPINN_FLAX;
			}else{
				//log("No Flax");
				area = bankArea;
				destination = new Position(3209, 3219, 2);
				correctFloor = 2;
				task = Task.BANK;
			}
			
			if(onCorrectFloor(correctFloor)){
				//log("Correct Floor");
				walkToDestination(area, destination);
				performTask(task);
			}else{
				//log("Not CorrectFloor");
				changeFloor(correctFloor);
			}
		}catch(Exception e){
			//log("Some Error occured, restarting loop");
		}
		
		return random(200, 600);
	}
	
	@Override
	public void onExit(){
		log("Thank you for using zSpinnerPro!");
	}
	
	private void changeFloor(int correctFloor) throws InterruptedException{
		
		final String action;
		final int stairsId;
		
		if(client.getMyPlayer().getZ() > correctFloor){
			action = "Climb-down";
			stairsId = 1740;
		}else{
			action = "Climb-up";
			stairsId = 1739;
		}
		
		walkToDestination(stairsArea, new Position(3206, 3209, client.getMyPlayer().getZ()));
		sleep(random(100, 300));			
		RS2Object stairs = closestObject(stairsId);
		//int x = random((int) (stairs.getMouseDestination().getBoundingBox().getMinX()), (int) stairs.getMouseDestination().getBoundingBox().getMaxX());
		//int y = (int) (stairs.getMouseDestination().getBoundingBox().getCenterY() + 15 + random(0,3));
		//new PreciseDestination(new Point(x, y), true)
		client.moveCameraToPosition(stairs.getPosition());
		sleep(random(100, 200));
		stairs.interact(action);
		sleep(new TillNextFloor(this, client.getMyPlayer().getZ()), 3000);
	
	}
	
	private boolean onCorrectFloor(final int floor){
		return client.getMyPlayer().getZ() == floor;
	}
	
	private void walkToDestination(final Area area, final Position destination) throws InterruptedException{
		//log("Walking to destination...");
		final Area doorArea = new Area(new Position[] {
				
				new Position(3207, 3215, 1),
				new Position(3207, 3213, 1),
				new Position(3208, 3215, 1),
				new Position(3208, 3213, 1)
				
		});
		
		final RS2Object door;

		if(area.contains(client.getMyPlayer().getPosition())){
			//log("In area.");
			return;
		}
		walk(destination, 0);
		sleep(new TillStop(this), 6000);
		sleep(random(200, 400));

		if(client.getMyPlayer().getZ() == 1){
			door = closestObject(doorArea, 1536);
			if(door != null){
				client.moveCameraToPosition(door.getPosition());
				sleep(random(100, 200));
				door.interact("Open");
				sleep(new TillDoorOpen(this, doorArea, 1536), 5000);
			}
		}
		
		walkToDestination(area, destination);
	}
	
	private void performTask(final Task task) throws InterruptedException{
		//log("Performing a task...");
		if(task.equals(Task.BANK)){
			final RS2Object bankBooth = closestObject(18491);//(4907);
	
			counter = 0;
			openBank(bankBooth);
			client.getBank().depositAll();
			sleep(random(100, 400));
			if(!client.getBank().contains(flaxId)){
				log("No more Flax in BANK!!! Stopping...");
				client.getBank().close();
				sleep(random(100, 500));
				logout();
				sleep(random(100, 200));
				this.stop();
			}
			client.getBank().withdrawX(flaxId, amountToMake);
			sleep(random(100, 400));
			//client.getBank().close();

		}else if(task.equals(Task.SPINN_FLAX)){
			//log("Trying to spinn.");
			final RS2Object spinningWheel = closestObject(3632);
			final Item flax = client.getInventory().getItemForId(flaxId);
			final int amountHad = (int) client.getInventory().getAmount(flax);

			//log("amountHad = " + amountHad);
				
			clickOnSpinningWheel(spinningWheel);
			sleep(random(50, 200));
			RectangleDestination rectDest = new RectangleDestination(client.getInterface(interfaceParent).getChild(interfaceChild).getRectangle());
			selectOption(null, rectDest, "Make X");
			sleep(new TillNotValid(this, interfaceParent), 3000);
			sleep(random(50, 200));
			client.typeString((Integer.toString(amountHad)));
			sleep(random(300, 600));
			
			sleep(new WhileWorking(this, flaxId, amountToMake, amountHad, interfaceParent), 120000);

		
		}else{
			
		}
	}
	
	private void clickOnSpinningWheel(final RS2Object spinningWheel) throws InterruptedException{
		if(client.getInterface(interfaceParent) != null){
			//log("Its up");
			return;
		}else{	
			selectOption(spinningWheel, spinningWheel.getMouseDestination(), "Spin");
			sleep(new TillValid(this, interfaceParent), 5000);
			clickOnSpinningWheel(spinningWheel);
		}
	}
	
	private void openBank(final RS2Object bankBooth) throws InterruptedException{
		if(client.getBank().isOpen()){
			return;
		}
		client.moveCameraToPosition(bankBooth.getPosition());
		sleep(random(100, 200));
		bankBooth.interact(false, "Bank");
		sleep(new TillBankOpen(this), 3000);
		counter++;
		if(counter > 2){
			return;
		}
		openBank(bankBooth);
	}
	
	/*
	 * This method updates the info that needs to be 
	 * displayed in the paint.
	 */
	private void updateInfo(){
		timeRunning = (int) (System.currentTimeMillis() - startTime);
		//xpGained = client.getSkills().getExperience(Skill.CRAFTING) - startXp;
	}
	
	/*
	 * This method returns a String that has
	 * formated time inside.
	 */
	private String formatTime(final int time){
		long s = (time / 1000);
		
		long sec = (s%60);
		long min = (s%3600)/60;
		long hour = s/3600;
		
		String timeFormat = String.format("%dh : %02dm : %02ds", hour, min, sec);
		return timeFormat;
	}
	
	private void logout() throws InterruptedException
	{
	        RS2Interface logoutInterface = new RS2Interface(client.getBot(),182);
	        selectInterfaceOption(548,33,"Logout");
	        sleep(random(100, 200));
	        if(logoutInterface != null)
	        {
	            selectInterfaceOption(182,6,"Ok");
	        }
	        log("Logged Out");
	}
	
	public boolean sleep(Condition c, int timeout) {
        for (int i = 0; i < timeout / 500 && !c.isValid(); i++) {
            try {
                sleep(random(400, 600));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return c.isValid();
    }
	
	
}
