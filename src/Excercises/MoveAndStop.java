package Excercises;

import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

public class MoveAndStop {
	
	public void moveForward() {
		Motor.A.forward();
		Motor.A.setSpeed(100);
		Motor.C.forward();
		Motor.C.setSpeed(100);
		
		Delay.msDelay(2000);
		
		Motor.A.stop();
		Motor.C.stop();
	}
	
}
