package models;

import lejos.hardware.motor.*;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class Bewegingsapparaat {
	//Constanten
	private static final int INTERVAL = 200;
	private final int Gradenstapje = 360; // Dit is ��n rotatie van een wiel, dat staat gelijk aan 30 graden
	
	// Motor initialization
	EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
	EV3LargeRegulatedMotor mD = new EV3LargeRegulatedMotor(MotorPort.D);
    
    // Constructor    
    public Bewegingsapparaat() {
		super();
	}

    
	// Methodes:
    /* Beweeg naar voren of naar achteren */
    public void vooruitOfAchteruit (float snelheid, char voorOfAchter) {
    	mA.setSpeed(snelheid);
    	mD.setSpeed(snelheid);
    	// Geef snelheid in graden/sec
    	if (voorOfAchter == 'V') {
    		mA.forward();
        	mD.forward();
    	} else if (voorOfAchter == 'A') {
    		mA.backward();
        	mD.backward();
    	}
    	    	
    	// Een 2 sec interval @TODO moet weg op een gegeven moment
        Delay.msDelay(INTERVAL);
        
        // Stop de motoren
        volledigeStop();
    	    	
    	// Geef de motoren vrij
    	mA.close();
    	mD.close();
    }
    
    public void setEngineSpeed(double engineLeft, double engineRight) {
    	this.mA.setSpeed((float)engineLeft);
    	this.mD.setSpeed((float)engineRight);
    }
    
    
    //@overload Beweeg naar voren 
    public void vooruitOfAchteruit (char voorOfAchter) {
    	vooruitOfAchteruit(mA.getMaxSpeed(), voorOfAchter);
    }
    
    /* Roteer methode */
    public void roteer(char richting) {
    	if(richting == 'L') {
    		mA.rotate(Gradenstapje); // 1 rotaties (360 graden) is ongeveer 30 graden in real life
    	} else if(richting == 'R') {
    		mD.rotate(Gradenstapje);
    	} else {
    		System.out.println("Deze richting bestaat niet!");
    	}
    }
       
    /* Volledige stop methode */
    public void volledigeStop() {
    	mA.stop();
    	mD.stop();
    }
    
}
