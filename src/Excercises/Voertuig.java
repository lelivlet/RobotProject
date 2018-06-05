package Excercises;

import lejos.hardware.motor.*;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class Voertuig {
	// Motor initialization
	EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
	EV3LargeRegulatedMotor mD = new EV3LargeRegulatedMotor(MotorPort.D);
    
    // Constructor    
    public Voertuig() {
		super();
	}

	// Methodes
    // Beweeg naar voren
    public void vooruit (float snelheid) {
    	// Geef snelheid in graden/sec
    	mA.setSpeed(snelheid);
    	mD.setSpeed(snelheid);
    	mA.forward();
    	mD.forward();
    	
    	/* Een 2 sec interval @TODO moet weg op een gegeven moment */
        Delay.msDelay(2000);
        
        // Stop de motoren
        volledigeStop();
    	    	
    	// Geef de motoren vrij
    	mA.close();
    	mD.close();
    }
    // Beweeg naar voren @overload
    public void vooruit () {
    	vooruit(mA.getMaxSpeed());
    }
    
    // Volledige stop methode
    public void volledigeStop() {
    	mA.stop();
    	mD.stop();
    }
    

}
